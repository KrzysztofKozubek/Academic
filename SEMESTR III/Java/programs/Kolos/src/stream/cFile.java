package stream;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class cFile {

    public void rewriteFile(String from, String to) throws IOException {
        FileInputStream fileIn = null;
        FileOutputStream fileOut = null;
        try {
            fileIn = new FileInputStream(from);
            fileOut = new FileOutputStream(to);
            int helpChar;

            while ((helpChar = fileIn.read()) != -1) {
                fileOut.write(helpChar);
            }
        } finally {
            if (fileIn != null)
                fileIn.close();
            if (fileOut != null)
                fileOut.close();
        }
    }

    public void rewriteBuffor2(String from, String to) {
        InputStream in = null;
        OutputStream out = null;

        try {
            //in = new BufferedInputStream(new FileInputStream(from));
            //out = new BufferedOutputStream(new FileOutputStream(to));

            in = new BufferedInputStream(new FileInputStream(from), 1 * 8);
            out = new BufferedOutputStream(new FileOutputStream(to), 1 * 8);
            while (in.available() > 0) {
                out.write((char) in.read());
            }

        } catch (IOException e) {

        }
    }

    public void rewriteZnakowe(String from, String to) throws IOException {

        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader(from);
            out = new FileWriter(to);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

    public void rewriteBuffor(String from, String to) {

        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new FileReader(from));
            out = new PrintWriter(new FileWriter(to));
            String c;
            while ((c = in.readLine()) != null) {
                //out.write(c);
                out.println(c);
            }
        } catch (IOException e) {
            System.out.println("BLAD PRZY ODCZYCIE");
        }finally {
            if(out != null)
                out.close();
        }
    }

    public void rewriteScan(String from, String to) {

        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(from)));
            FileWriter out = new FileWriter(to);

            ///Scanner scan = new Scanner("Will Smith;Male;30");
            //scan.useDelimiter(Pattern.compile(";"));
            //s.useDelimiter(",\\s*");
            while (s.hasNext()) {
                out.write(s.next());
            }
        } catch (IOException e) {

        } finally {
            if (s != null)
                s.close();
        }
    }

    public void rewriteFormat(String from, String to) {

        double d = 2.0;
        double s = Math.sqrt(2.0);
        //System.out.println("Pierwiastek z  " + d + " to " + s + ".");

        //System.out.format("Pierwiastek z  %f to %.4f\n", d, s);

        //System.out.format(Locale.US, "Pierwiastek z  %.1f to %.4f\n", d, s);
        //System.out.printf(Locale.US, "Pierwiastek z  %.1f to %.4f\n", d, s);
    }

    public void multiInt(int... ints){
        int suma = 0;
        for(int iEle : ints){
            suma += iEle;
        }
    }

    public static void main(String[] args) {

        cFile file = new cFile();
        long start = 0;
        long stop = 0;
        try {
            start = System.currentTimeMillis();
            file.rewriteFile("text.txt", "copytext.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.rewriteBuffor2("text.txt", "copytext4.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.rewriteZnakowe("text.txt", "copytext2.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.rewriteBuffor("text.txt", "copytext3.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.rewriteScan("text.txt", "copytext5.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.rewriteScan("text.txt", "copytext6.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));














            start = System.currentTimeMillis();
            file.rewriteFormat("text.txt", "copytext5.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.multiInt(1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4);
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));


        } catch (IOException e) {
            System.out.println("Błąd z plikiem");
        }
    }
}



