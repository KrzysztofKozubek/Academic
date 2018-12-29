import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    Player player;

    public Client(int numberPlayer, String address, int port) {

        System.out.print("Client\n");
        player = new Player(numberPlayer, address, port);
    }

    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;

        Socket socket = null;

        try {

            socket = new Socket(player.address, player.port);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {

            e.printStackTrace();
        }

        out.print("\nHello i waiting for you too...");


        while (true) {

            String input = null;

            try {

                input = in.readLine();
                if (input == null || input.equals(".")) {

                    break;
                }
                out.println(input.toUpperCase());

            } catch (IOException e) {

                e.printStackTrace();
            }


        }
    }


    public static void main(int numberPlayer, String address, int port) {


        Thread t = new Client(numberPlayer, address, port);
        t.start();

    }
}
