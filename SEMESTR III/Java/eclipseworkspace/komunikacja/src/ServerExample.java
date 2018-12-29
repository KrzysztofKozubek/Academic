import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
    private static ServerSocket ss;

    public static void main(String[] args) throws IOException {
        ss = new ServerSocket(12345);
        while (true) {
            Socket s = ss.accept();
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            int b;
            while ((b = is.read()) != -1) {
                System.out.print((char) b);
                os.write(b);
            }
            s.close();
        }
    }
}
