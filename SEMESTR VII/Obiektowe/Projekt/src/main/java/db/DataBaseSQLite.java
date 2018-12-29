package db;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DataBaseSQLite extends DatabaseHandler {

    static DataBaseSQLite dataBaseSQLite = null;

    private DataBaseSQLite() {}

    public static DataBaseSQLite getDataBaseSQLite() {

        if(dataBaseSQLite == null)
            dataBaseSQLite = new DataBaseSQLite();

        return dataBaseSQLite;
    }

    public boolean connect() throws SQLException, ParseException, IOException {

        Map<String, String> nodes = DBConfig.readConfig();
        if(connection == null) {
            System.out.println("jdbc:" + nodes.get(NodeConfigJson.TYPE.getNode()) +
                    ":" +
                    nodes.get(NodeConfigJson.URL.getNode()));
            connection = DriverManager.getConnection(
                    "jdbc:" + nodes.get(NodeConfigJson.TYPE.getNode()) +
                            ":" +
                            nodes.get(NodeConfigJson.URL.getNode()));

            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            System.out.println("Correct connection to sqlite");
            return true;
        }
        System.out.println("Currently u r connecting with this db");
        return false;
    }
}
