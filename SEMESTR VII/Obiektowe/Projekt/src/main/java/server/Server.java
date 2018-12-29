package server;

import com.sun.net.httpserver.HttpServer;
import routing.MainHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by RedHat on 20.01.2018.
 */
public class Server {

    HttpServer server = null;
    int port = 8080;

    public void launchServer() throws IOException {

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MainHandler());
        server.createContext("/movies", new MainHandler());
        server.createContext("/movie", new MainHandler());
        server.createContext("/contact", new MainHandler());
        server.createContext("/support", new MainHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("SERVER START U CAN FIND IT ON: http:\\\\localhost:" + port);
    }
}
