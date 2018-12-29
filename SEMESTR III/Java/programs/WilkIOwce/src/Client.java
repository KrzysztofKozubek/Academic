import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client implements Runnable {

    public int size = 8;
    public int pxSize = 50;

    public Color colorField1 = Color.BLACK;
    public Color colorField2 = Color.WHITE;

    public static Board board = new Board();


    BufferedReader br1, br2;
    PrintWriter pr1;
    Socket socket;
    Thread t1, t2;
    String in = "", out = "";

    public Client() {
        try {
            t1 = new Thread(this);
            t2 = new Thread(this);
            socket = new Socket("localhost", 5000);
            t1.start();

            t2.start();

        } catch (Exception e) {
        }
    }

    public void run() {

        try {
            while (true) {
                if (Thread.currentThread() == t2) {
                    do {
                        br1 = new BufferedReader(new InputStreamReader(System.in));
                        pr1 = new PrintWriter(socket.getOutputStream(), true);

                        in = br1.readLine();
                        pr1.println(in);
                        board.upDateBoard(in);
                    } while (!in.equals("END"));
                } else {
                    do {
                        br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = br2.readLine();
                        System.out.println("Server says : : : " + out);
                        board.upDateBoard(out);

                    } while (!out.equals("END"));
                }
            }
        } catch (Exception e) {
        }

    }

    public static void main(String[] args) {
        new Client();
    }
}