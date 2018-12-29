import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.Movie;
import db.NodeConfigJson;
import org.bson.Document;
import routing.operation.PUT;
import server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RedHat on 20.01.2018.
 */

/*
.\C:\Program Files\MongoDB\Server\3.6\bin\mongod.exe
 */
public class APK {

    public static void main(String[] args) {

        Server server = new Server();
        try {
            server.launchServer();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SERVER DOWN!");
        }
    }
}
