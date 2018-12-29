package routing.operation;

import db.DBConfig;
import db.DatabaseHandler;
import db.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import routing.JSONHandler;

import java.util.*;

/**
 * Created by RedHat on 20.01.2018.
 */
public class POST implements Operation {

    @Override
    public void operation() {}

    public String getJSONSearch(Map<String, String> search) {

        List<Movie> movies = new LinkedList<>();
        DatabaseHandler db = null;
        JSONObject config = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            db = DatabaseHandler.getDatabase();
            db.connect();
            movies = db.getByTitle(search.get("search"));
            for (Movie var : movies) {
                array.add(var);
            }
            config.put("Movies", array);
            return config.toJSONString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getJSONMovie(Map<String, String> idMovie) {

        List<Movie> movies = new LinkedList<>();
        DatabaseHandler db = null;
        JSONObject config = new JSONObject();

        try {
            db = DatabaseHandler.getDatabase();
            db.connect();
            movies = db.getOne(idMovie.get("idMovie"));
            for (Movie movie : movies) {
                for (Map.Entry var : movie.variable.entrySet()) {
                    config.put(var.getKey(), var.getValue());
                }
            }
            return config.toJSONString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getJSONEmpty(Map empty) {

        JSONObject config = new JSONObject();

        try {
            return config.toJSONString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
