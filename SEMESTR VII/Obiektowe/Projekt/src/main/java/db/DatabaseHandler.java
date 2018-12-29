package db;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public abstract class DatabaseHandler {

    Connection  connection  = null;
    Statement   statement   = null;

    protected final String nameTable = "movie";

    // "jdbc:postgresql://localhost:5432/postgres"
    // "jdbc:sqlite:/home/redhat/IdeaProjects/Project1/dbsqlite.sqlite"
    // "jdbc:mysql://localhost:3306/students"

    public static DatabaseHandler getDatabase() throws SQLException, ParseException, IOException {

        Map<String, DatabaseHandler> dbs = new HashMap<String, DatabaseHandler>() {{
            put("sqlite", DataBaseSQLite.getDataBaseSQLite());
            put("mysql", DataBaseMysql.getDataBaseMysql());
            put("postgresql", DataBasePostgresSQL.getDataBasePostgresSQL());
            put("mongodb", DataBaseMongo.getDataBaseMongo());
        }};

        DBConfig dbConfig = new DBConfig();
        dbConfig.readConfig();
        String type = dbConfig.getValueJSON(NodeConfigJson.TYPE);

        if (type == null) {
            throw new NullPointerException("Config file has got mistake");
        } else
            return dbs.get(type);
    }

    public boolean connect() throws SQLException, ParseException, IOException {

        Map<String, String> nodes = DBConfig.readConfig();
        if(connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:" + nodes.get(NodeConfigJson.TYPE.getNode()) +
                            ":" +
                            nodes.get(NodeConfigJson.URL.getNode()) +
                            ":" +
                            nodes.get(NodeConfigJson.PORT.getNode()) +
                            "/" +
                            nodes.get(NodeConfigJson.DATABASE.getNode()),
                    nodes.get(NodeConfigJson.USER.getNode()),
                    nodes.get(NodeConfigJson.PASSWORD.getNode()));

            statement = connection.createStatement();
            statement.setQueryTimeout(30);
        }
        System.out.println("Correct connection to " + nodes.get(NodeConfigJson.TYPE));
        return true;
    }

    public List<Movie> getAll() {

        return executeQuery("SELECT * FROM " + nameTable);
    }

    public List<Movie> getOne() {

        return executeQuery("SELECT * FROM " + nameTable + " LIMIT 1");
    }

    public List<Movie> getLastTen() {

        return executeQuery("SELECT * FROM " + nameTable + " LIMIT 10");
    }

    public List<Movie> getOne(String id) {

        return executeQuery("SELECT * FROM " + nameTable + " WHERE id = " + id);
    }

    public List<Movie> getByTitle(String title) {

        return executeQuery("SELECT * FROM " + nameTable + " WHERE title LIKE '%" + title + "%'");
    }

    public void insert(Movie movie) {

        execute(generateInsertQuery(movie));
    }

    public void delete(Integer id) {

        execute(generateDeleteQuery(id));
    }

    public void update(Integer IDMovieToChange, Movie movie) {

        execute(generateUpdateQuery(IDMovieToChange, movie));
    }

    public void execute(String query) {

        if(connection != null) {
            try {
                System.out.println(query);
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("First u must connect to DB");
    }

    public List<Movie> executeQuery(String query) {

        List<Movie> result = new LinkedList<>();
        if(connection != null) {
            try {
                System.out.println(query);
                ResultSet results = statement.executeQuery(query);
                while(results.next())
                    result.add(new Movie(
                            results.getInt("id"),
                            results.getString("title"),
                            results.getString("poster"),
                            results.getString("genre"),
                            results.getString("premier"),
                            results.getString("mark"),
                            results.getString("description")
                    ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("First u must connect to DB");
        return result;
    }

    public String generateInsertQuery(Movie movie) {

        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO `" + nameTable + "` VALUES (");
        query.append("'" + movie.id + "', ");
        query.append("'" + movie.title + "', ");
        query.append("'" + movie.poster + "', ");
        query.append("'" + movie.genre + "', ");
        query.append("'" + movie.premier + "', ");
        query.append("'" + movie.mark + "', ");
        query.append("'" + movie.description + "'");
        query.append(")");
        return query.toString();
    }

    public String generateDeleteQuery(Integer id) {

        StringBuffer query = new StringBuffer();
        query.append("DELETE FROM `" + nameTable + "` WHERE ");
        query.append("id = " + id + "");
        return query.toString();
    }

    public String generateUpdateQuery(Integer id, Movie movie) {

        StringBuffer query = new StringBuffer();
        query.append("UPDATE " + nameTable + " SET ");
        query.append("title = '" + movie.title + "', ");
        query.append("poster = '" + movie.poster + "', ");
        query.append("genre = '" + movie.genre + "', ");
        query.append("premier = '" + movie.premier + "', ");
        query.append("mark = '" + movie.mark + "', ");
        query.append("description = '" + movie.description + "'");
        query.append("WHERE id = " + id);
        return query.toString();

    }
}
