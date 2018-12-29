package Server_Client.Client;

import Server_Client.Connections;
import Server_Client.MP3Player;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client extends Connections {

    public GuiClient guiClient;
    public String fileMp3 = "sms.mp3";

    public Client() {
        new Client("400");
    }

    public Client(String port) {
        this.port = Integer.parseInt(port);
        guiClient = new GuiClient(this);
    }

    public void run() {
        try {
            requestSocket = new Socket("localhost", port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            do {
                try {
                    message = (String) in.readObject();
                    if(message.equals("FILE")) {
                        message = (String) in.readObject();
                        downloadFile(message);
                    }
                    addTextToLabel("Ktoś", message);

                    //sendMessage();
                } catch (ClassNotFoundException classNot) {
                }
            } while (true);
        } catch (UnknownHostException unknownHost) {
        } catch (IOException ioException) {
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
            }
        }
    }

    public void addTextToLabel(String who, String text) {

        if(who == "I")
            guiClient.createCloudLeft(text);
        else{

            guiClient.createCloudRight(text);
            MP3Player.play(fileMp3);

        }
    }

    public void sendMessage() {
        String value;
        Scanner odczyt = new Scanner(System.in);
        value = odczyt.nextLine();
        sendMessage(value);
    }

    public void sendMessage(String msg) {
        try {
            addTextToLabel("I", msg);
            out.writeObject(msg);
            out.flush();

        } catch (IOException ioException) {
        }
    }



    public void sendFile(String file) {
        try {
            System.out.println("c send file start");
            ServerSocket serverSocket = new ServerSocket(100);
            Socket socket = serverSocket.accept();
            File transferFile = new File(file);
            byte[] bytearray = new byte[(int) transferFile.length()];
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            bin.read(bytearray, 0, bytearray.length);
            OutputStream os = socket.getOutputStream();
            os.write(bytearray, 0, bytearray.length);
            os.flush();
            socket.close();
            System.out.println("File transfer complete");

        } catch (IOException e) {
        }
    }

    public void downloadFile(String file) {
        try {
            System.out.println("c download file start");
            int filesize = 1022386;
            int bytesRead;
            int currentTot = 0;
            Socket socket = new Socket("127.0.0.1", 100);
            byte[] bytearray = new byte[filesize];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(bytearray, 0, bytearray.length);
            currentTot = bytesRead;
            do {
                bytesRead = is.read(bytearray, currentTot, (bytearray.length - currentTot));
                if (bytesRead >= 0) currentTot += bytesRead;
            } while (bytesRead > -1);
            bos.write(bytearray, 0, currentTot);
            bos.flush();
            bos.close();
            socket.close();
        } catch (IOException e) {
        }
    }



    public static void start(String port){
        Client client = new Client(port);
        client.run();
    }

    public static void main(String[] args) {

        Client.start("1");
    }
}
