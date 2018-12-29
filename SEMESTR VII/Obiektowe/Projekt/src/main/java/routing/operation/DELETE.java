package routing.operation;

import db.DatabaseHandler;
import db.Movie;

import java.util.Map;

/**
 * Created by RedHat on 20.01.2018.
 */
public class DELETE implements Operation {

    @Override
    public void operation() {}

    public String removeMovie(Map<String, String> idMovie) {

        DatabaseHandler db = null;

        try {
            db = DatabaseHandler.getDatabase();
            db.connect();

            db.delete(Integer.valueOf(idMovie.get("idMovie")));
            return "Movie has been removed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
