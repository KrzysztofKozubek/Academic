package stream;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class test {
    public String l;

    public void wczytywaniepliku(String from, String to) throws IOException{

        Scanner in1 = null;
        BufferedInputStream in2 = null;

        FileWriter out1 = null;
        PrintWriter out11 = null;
        BufferedOutputStream out2 = null;

        try{
            in1 = new Scanner(new BufferedReader(new FileReader(from)));
            in2 = new BufferedInputStream(new FileInputStream(from));

            out1 = new FileWriter(to);
            out11 = new PrintWriter(to);
            out2 = new BufferedOutputStream(new FileOutputStream(to));


            /*
            scanner:
            while (s.hasNext()) {
                out.write(s.next());
            }

            bufforreder
            while ((c = in.readLine()) != null) {
                //out.write(c);
                out.println(c);
            }

            bufforinputstream
            while (in.available() > 0) {
                out.write((char) in.read());
            }


             */
        }catch (IOException e){}
    }

    public void downloadSite(String address)throws IOException{

        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(new URL("http://zamulacztv.cba.pl").openStream())));
        while(scanner.hasNext() && (l = scanner.nextLine()) != null)
            System.out.println(l);
    }


    public static void main(String[] args) throws IOException{
        test test1 = new test();
        String url = "http://google.com";
        test1.downloadSite(url);
    }

}
