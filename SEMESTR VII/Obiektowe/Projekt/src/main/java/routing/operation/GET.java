package routing.operation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import db.DatabaseHandler;
import db.Movie;
import html.DecoratorHEAD;
import html.HTMLTemplate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by RedHat on 20.01.2018.
 */
public class GET implements Operation {

    DecoratorHEAD template = null;

    @Override
    public void operation() {}

    public String getOne(String idMovie) {

        try {
            DatabaseHandler db = DatabaseHandler.getDatabase();
            db.connect();
            List<Movie> movies = db.getOne(idMovie);

            StringBuffer result = new StringBuffer();
            for (Movie movie : movies)
                result.append(movie.toString());

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAll(String htmlFileNameTemplate) {

        try {
            DatabaseHandler db = DatabaseHandler.getDatabase();
            db.connect();
            List<Movie> movies = db.getAll();

            template = getTemplate(htmlFileNameTemplate);

            Multimap<String, Movie> nodes = ArrayListMultimap.create();
            Field[] fields = Movie.class.getDeclaredFields();
            for (Movie movie : movies)
                nodes.put("movies", movie);

            return template.get(htmlFileNameTemplate, nodes);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String contact(String htmlFileNameTemplate) {

        return getTemplate(htmlFileNameTemplate).getTemplate();
    }

    public String support(String htmlFileNameTemplate) {

        return getTemplate(htmlFileNameTemplate).getTemplate();
    }

    public String home(String htmlFileNameTemplate) {

        try {
            DatabaseHandler db = DatabaseHandler.getDatabase();
            db.connect();
            List<Movie> movies = db.getLastTen();

            template = getTemplate(htmlFileNameTemplate);

            Multimap<String, Movie> nodes = ArrayListMultimap.create();
            Field[] fields = Movie.class.getDeclaredFields();
            for (Movie movie : movies)
                nodes.put("movies", movie);

            return template.get(htmlFileNameTemplate, nodes);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String movie(String htmlFileNameTemplate, String id) {

        try {
            DatabaseHandler db = DatabaseHandler.getDatabase();
            db.connect();
            List<Movie> movies = db.getOne(id);

            template = getTemplate(htmlFileNameTemplate);

            Multimap<String, Movie> nodes = ArrayListMultimap.create();
            Field[] fields = Movie.class.getDeclaredFields();
            for (Movie movie : movies)
                nodes.put("movies", movie);

            return template.get(htmlFileNameTemplate, nodes);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private DecoratorHEAD getTemplate(String htmlFileNameTemplate) {
        return new DecoratorHEAD(new HTMLTemplate.BuilderTemplateHTML()
                .fileNameHTML(htmlFileNameTemplate)
                .build());
    }

    public String getJSONMovies() {

        List<Movie> movies = new LinkedList<>();
        DatabaseHandler db = null;
        JSONObject config = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            db = DatabaseHandler.getDatabase();
            db.connect();
            movies = db.getAll();
            for (Movie var : movies) {
                array.add(var);
            }
            config.put("Movies", array);
            return config.toJSONString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
