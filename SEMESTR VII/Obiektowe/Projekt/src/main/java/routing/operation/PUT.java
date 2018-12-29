package routing.operation;

import db.DatabaseHandler;
import db.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RedHat on 20.01.2018.
 */
public class PUT implements Operation {

    @Override
    public void operation() {}


    public static Movie downloadMovieFromFilmWeb(String URL) {

        String htmlWebsite = getWebsite(URL);

        Pattern pattern = null;
        Matcher matcher = null;

        Map<String, String> tmpPattern = new HashMap<String, String>() {{
            put("id", "id:(.*?),");
            put("title", "FILM\",title:\"(.*?)\",");
            put("poster", "setPoster\\(\"(.*?)\"\\)");
            put("genre", "sasKeywordAdd\\(\"g\",\"(.*?)\"\\)");
            put("premier", "premiereCountry:\"(.*?)\",");
            put("mark", "rate:(.*?),");
            put("description", "<p class=\"text\">(.*?)</p>");
        }};
        Map<String, String> result = new HashMap<>();
        for (Map.Entry var : tmpPattern.entrySet()) {
            pattern = Pattern.compile((String) var.getValue());
            matcher = pattern.matcher(htmlWebsite);
            matcher.find();
            result.put((String) var.getKey(), matcher.group(1));
        }

        return new Movie(
                Integer.valueOf(result.get("id")),
                result.get("title"),
                result.get("poster"),
                result.get("genre"),
                result.get("premier"),
                result.get("mark"),
                result.get("description")
        );
    }

    public String addMovie(Map<String, String> link) {

        DatabaseHandler db = null;
        try {
            db = DatabaseHandler.getDatabase();
            db.connect();
            Movie movie = downloadMovieFromFilmWeb(link.get("link"));
            db.insert(movie);
            return movie.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String getWebsite(String link) {
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL(link).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return content;
    }
}
