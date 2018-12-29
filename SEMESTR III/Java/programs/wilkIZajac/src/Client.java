import java.net.*;
import java.io.*;

public class Client
{
    public Client() throws IOException, ClassNotFoundException
    {
        Socket s = new Socket( "localhost", 4000 );

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream  ois = new ObjectInputStream( s.getInputStream());

        Main to = new Main( );

        // print object contents
        //System.out.println( to );

        oos.writeObject(to);
        oos.flush();
        Object received = ois.readObject();

        // should match original object contents
        System.out.println(received);

        oos.close();
        ois.close();
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        new Client();
    }
}


