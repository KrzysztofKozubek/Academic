package db;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by RedHat on 31.01.2018.
 */

/*
cd to C:\Program Files\MongoDB\Server\3.2\bin>
enter command mongod
 */
public class DataBaseMongo extends DatabaseHandler {

    MongoClient client = null;
    DB database = null;
    DBCollection collection = null;


    static DataBaseMongo dataBaseMongo = null;

    private DataBaseMongo() {}

    public static DataBaseMongo getDataBaseMongo() {

        if (dataBaseMongo == null)
            dataBaseMongo = new DataBaseMongo();

        return dataBaseMongo;
    }

    @Override
    public boolean connect() throws SQLException, ParseException, IOException {

        Map<String, String> nodes = DBConfig.readConfig();

//        "mongodb://localhost:27017";
        if (client == null) {

            client = new MongoClient(
                    new MongoClientURI(
                            nodes.get(NodeConfigJson.TYPE.getNode()) +
                                    ":" +
                                    nodes.get(NodeConfigJson.URL.getNode()) +
                                    ":" +
                                    nodes.get(NodeConfigJson.PORT.getNode())
//                            nodes.get(NodeConfigJson.TYPE.getNode()) +
//                                    "://" +
//                                    nodes.get(NodeConfigJson.USER.getNode()) +
//                                    ":" +
//                                    nodes.get(NodeConfigJson.PASSWORD.getNode()) +
//                                    "@" +
//                                    nodes.get(NodeConfigJson.URL.getNode()) +
//                                    ":" +
//                                    nodes.get(NodeConfigJson.PORT.getNode()) +
//                                    "/?authSource=" +
//                                    nodes.get(NodeConfigJson.DATABASE.getNode())
                    )
            );

            database = client.getDB(nodes.get(NodeConfigJson.DATABASE.getNode()));
            collection = database.getCollection(nodes.get(NodeConfigJson.DATABASE.getNode()));
        }
        System.out.println("Correct connection to " + nodes.get(NodeConfigJson.TYPE.getNode()));
        return true;
    }

    @Override
    public List<Movie> getAll() {

        List<Movie> result = new LinkedList<>();
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                result.add(mappingDBObjectToMovie(cursor.next()));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public List<Movie> getOne() {
        List<Movie> result = new LinkedList<>();
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                result.add(mappingDBObjectToMovie(cursor.next()));
                return result;
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public List<Movie> getLastTen() {
        List<Movie> result = new LinkedList<>();
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                result.add(mappingDBObjectToMovie(cursor.next()));
                if (result.size() >= 10)
                    return result;
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public List<Movie> getOne(String id) {

        BasicDBObject query = new BasicDBObject("id", id);
        DBObject object = collection.findOne(query);
        List<Movie> result = Collections.singletonList(mappingDBObjectToMovie(object));

        return result;
    }

    @Override
    public List<Movie> getByTitle(String title) {

        BasicDBObject query = new BasicDBObject("title", title);
        DBCursor cursor = collection.find(query);
        List<Movie> result = new LinkedList<>();

        try {
            while(cursor.hasNext()) {
                result.add(mappingDBObjectToMovie(cursor.next()));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public void insert(Movie movie) {
        DBObject toInsert = mappingMovieToObject(movie);
        collection.insert(toInsert);
    }

    @Override
    public void delete(Integer id) {

        DBObject query = new BasicDBObject("id", String.valueOf(id));
        collection.remove(query);
    }

    @Override
    public void update(Integer IDMovieToChange, Movie movie) {

        BasicDBObject query = new BasicDBObject("id", String.valueOf(IDMovieToChange));

        DBObject newDocument = mappingMovieToObject(movie);

        DBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        collection.update(query, updateObj);

        super.update(IDMovieToChange, movie);
    }

    @Override
    public List<Movie> executeQuery(String query) {
        return super.executeQuery(query);
    }

    @Override
    public String generateInsertQuery(Movie movie) {
        return super.generateInsertQuery(movie);
    }

    @Override
    public String generateDeleteQuery(Integer id) {
        return super.generateDeleteQuery(id);
    }

    @Override
    public String generateUpdateQuery(Integer id, Movie movie) {
        return super.generateUpdateQuery(id, movie);
    }

    private BasicDBObject mappingMovieToObject(Movie movie) {

        return new BasicDBObject("id", movie.getId())
                .append("title", movie.getTitle())
                .append("poster", movie.getPoster())
                .append("genre", movie.getGenre())
                .append("premier", movie.getPremier())
                .append("mark", movie.getMark())
                .append("description", movie.getDescription());
    }

    private Movie mappingDBObjectToMovie(DBObject dbObject) {

        System.out.println(Integer.valueOf(dbObject.get("id").toString()));


        Movie result = new Movie(
                Integer.valueOf(dbObject.get("id").toString()),
                dbObject.get("title").toString(),
                dbObject.get("poster").toString(),
                dbObject.get("genre").toString(),
                dbObject.get("premier").toString(),
                dbObject.get("mark").toString(),
                dbObject.get("description").toString()
        );

        return result;
    }

    public void closeConnection() {
        client.close();
    }
}
