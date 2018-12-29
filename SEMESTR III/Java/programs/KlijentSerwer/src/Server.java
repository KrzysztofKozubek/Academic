import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 50007;
    static boolean flaga = true;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args) throws IOException {

        ServerSocket polaczenie = null;
        Socket gniazdo = null;
        InputStream we = null;
        OutputStream wy = null;
        BufferedReader odczyt = null;
        BufferedReader odczytWe = null;
        DataOutputStream zapis = null;
        String slowoPL = "", slowoEN = "";

        try {
            polaczenie = new ServerSocket(8866);
        } catch (IOException e) {
            System.out.println("Nie można utworzyć gniazda serwera.");
        }

        try {
            gniazdo = polaczenie.accept();
        } catch (IOException e) {
            System.out.println("Nie można nawiązać połączenia z klientem.");
        }

        try {
            we = gniazdo.getInputStream();
        } catch (IOException e) {
            System.out.println("Nie można pobrać strumienia wejściowego.");
        }

        try {
            wy = gniazdo.getOutputStream();
        } catch (IOException e) {
            System.out.println("Nie można pobrać strumienia wyjściowego.");
        }

        Client client = new Client();
        client.main(args);

        while (true) {
            try {
                odczyt = new BufferedReader(new InputStreamReader(we));
                slowoPL = odczyt.readLine();

                if ((slowoPL == null) || slowoPL.equals("koniec")) {
                    gniazdo.close();
                    System.exit(0);
                } else if (slowoPL != null) {
                    System.out.println("Odczytano slowo : " + slowoPL);
                    if (slowoPL.equals("okno")) {
                        slowoEN = "window";
                    } else if (slowoPL.equals("ekran")) {
                        slowoEN = "screen";
                    } else if (slowoPL.equals("oko")) {
                        slowoEN = "eye";
                    } else if (slowoPL.equals("ucho")) {
                        slowoEN = "ear";
                    } else if (slowoPL.equals("dom")) {
                        slowoEN = "house";
                    } else if (slowoPL.equals("noga")) {
                        slowoEN = "leg";
                    } else if (slowoPL.equals("cukier")) {
                        slowoEN = "sugar";
                    } else {
                        slowoEN = "";
                    }

                    zapis = new DataOutputStream(wy);
                    zapis.writeBytes(slowoEN + " ");
                    zapis.flush();
                    System.out.println("Wyslano slowo : " + slowoEN);

                }

            } catch (IOException e) {
                System.out.println("Problem wejścia - wyjścia.");
            }

        }
    }
}
