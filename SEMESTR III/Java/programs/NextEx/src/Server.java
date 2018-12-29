import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public ServerSocket socketServer;
    public Player player;

    public Server(int numberPlayer, String address, int port) throws IOException {

        System.out.print("Server\n");
        player = new Player(numberPlayer, address, port);
        socketServer = new ServerSocket(port);
        socketServer.setSoTimeout(10000);
    }

    public void run() {

        try {

            Socket socket = socketServer.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.print("\nHello i waiting for you...");
            while (true) {

                String input = in.readLine();
                System.out.println(input);
                if (input == null || input.equals(".")) {

                    break;
                }
                out.println(input.toUpperCase());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int numberPlayer = 0;
        String address = "localhost";
        int port = 50005;
        Server.main(numberPlayer, address, port);
    }

    public static void main(int numberPlayer, String address, int port) {


        Thread t = null;
        try {
            t = new Server(numberPlayer, address, port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
