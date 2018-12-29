package db;

import java.sql.SQLException;
import java.util.Scanner;

public class DataBasePostgresSQL extends DatabaseHandler {

    static DataBasePostgresSQL dataBasePostgresSQL = null;

    private DataBasePostgresSQL() {}

    public static DataBasePostgresSQL getDataBasePostgresSQL() {

        if(dataBasePostgresSQL == null)
            dataBasePostgresSQL = new DataBasePostgresSQL();

        return dataBasePostgresSQL;
    }

    @Override
    public String generateInsertQuery(Movie movie) {

        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO " + nameTable +
                " (title, poster, genre, premier, mark, time) VALUES (");
        query.append("'" + movie.title + "', ");
        query.append("'" + movie.poster + "', ");
        query.append("'" + movie.genre + "', ");
        query.append("'" + movie.premier + "', ");
        query.append("'" + movie.mark + "', ");
        query.append("'" + movie.description + "'");
        query.append(")");
        return query.toString();
    }
}
