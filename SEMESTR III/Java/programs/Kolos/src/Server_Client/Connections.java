package Server_Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connections {

    public int port;


    public ServerSocket providerSocket;
    public Socket requestSocket;
    public Socket connection = null;
    public ObjectOutputStream out;
    public ObjectInputStream in;


    public String message;

}
