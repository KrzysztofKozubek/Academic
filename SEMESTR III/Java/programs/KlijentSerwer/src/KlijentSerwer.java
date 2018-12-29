import java.net.*;
import java.io.*;

public class KlijentSerwer {

    public static void main(String[] args) throws IOException {


        Server server = new Server();
        Client client = new Client();

        server.main(args);
        client.main(args);
    }
}
