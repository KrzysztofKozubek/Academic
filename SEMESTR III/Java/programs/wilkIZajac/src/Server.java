import java.net.*;
import java.io.*;

public class Server
{
    public Server() throws IOException, ClassNotFoundException
    {
        ServerSocket ss = new ServerSocket(4000);
        Socket s = ss.accept();

        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

        // the 'echo' functionality of the server
        Object to = new Main();
        try
        {
            to = ois.readObject();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("broke");
            e.printStackTrace();
        }

        oos.writeObject(to);
        oos.flush();

        // close the connections
        ois.close();
        oos.close();

        s.close();
        ss.close();
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        new Server();
    }
}