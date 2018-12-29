package db;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
Example Config.JSON
{
    "type":"sqlite",    <- type DB (sqlite, mysql, postgresql)
    "pass":"",          <- password
    "user":"root",      <- user name
    "url":"/home/redhat/IdeaProjects/Project1/",
    "db":"dbsqlite.sqlite"
}
Where:
type    <- type DB (sqlite, mysql, postgresql)
pass    <- password
user    <- user name
url     <- url to file DB or to connect with db
db      <- name database
 */

public class DBConfig {

    private static final String CONF_JSON = "conf.json";
    private static HashMap<String, String> config = new HashMap();


    public void writeConfig(Map<String, String> nodes) throws IOException {
        JSONObject config = new JSONObject();
        for (NodeConfigJson node : NodeConfigJson.values())
            if(nodes.containsKey(node.getNode()))
                config.put(node.getNode(), nodes.get(node.getNode()));

        FileWriter configFile = null;
        try {
            configFile = new FileWriter(CONF_JSON);
            configFile.write(config.toJSONString());
            configFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            configFile.close();
        }

    }

    public static Map<String, String> readConfig() throws IOException, ParseException {

        return readConfig(CONF_JSON);
    }

    public static Map<String, String> readConfig(String pathConfigFile) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        FileReader configFile = null;
        try {
            configFile = new FileReader(pathConfigFile);
            JSONObject obj = (JSONObject) parser.parse(configFile);

            for (NodeConfigJson var : NodeConfigJson.values())
                config.put(var.getNode(), (String)obj.get(var.getNode()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            configFile.close();
        }
        return config;
    }

    public String getValueJSON(NodeConfigJson enumNode) {

        return config.get(enumNode.getNode());
    }
}
