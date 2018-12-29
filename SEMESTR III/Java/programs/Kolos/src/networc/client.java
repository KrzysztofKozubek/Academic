package networc;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class client {

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    client() {
    }

    void run() {
        try {
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");


            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("server>" + message);

                    sendMessage();
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            //4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(){
        String value;
        Scanner odczyt = new Scanner(System.in);
        value = odczyt.nextLine();
        sendMessage(value);
    }

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String args[]) {
        client client = new client();
        client.run();


    }
}
