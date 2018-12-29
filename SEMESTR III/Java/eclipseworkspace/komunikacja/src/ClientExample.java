import java.io.*;
import java.net.Socket;

public class ClientExample {

    public static Socket sock;

    public static void main(String[] args) throws IOException {
        sock = new Socket(args[0], Integer.valueOf(args[1]));
        OutputStream os = sock.getOutputStream();
        InputStream is = sock.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sLine;
        byte[] bRes = new byte[100];
        while ((sLine = br.readLine()) != null) {
            os.write(sLine.getBytes());
//            os.write("\n");
            System.out.println("wyslalem: " + sLine);
            int l = is.read(bRes);
            System.out.println("odebralem:" + new String(bRes, 0, l));
        }
        br.close();
        sock.close();
    }
}
