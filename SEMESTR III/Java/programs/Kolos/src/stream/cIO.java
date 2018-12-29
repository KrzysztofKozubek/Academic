package stream;


import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import static java.nio.file.StandardOpenOption.READ;

public class cIO {



    public void reWrite(String from, String to) throws IOException{

        Path file1 = new File(from).toPath();
        //Path file2 = FileSystems.getDefault().getPath(to);
        Path file2 = Paths.get(to);

        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        String encoding1 = System.getProperty("file.encoding");//UTF-8
        CharBuffer value;

        ByteBuffer buf2;
        SeekableByteChannel sbc1 = Files.newByteChannel(file1, StandardOpenOption.READ);
        FileChannel sbc2 = FileChannel.open(file2, StandardOpenOption.WRITE);

        try{
            while(sbc1.read(buf1) > 0){
                buf1.rewind();

                //buf2 = buf1;
                value = Charset.forName(encoding1).decode(buf1);
                buf2 = ByteBuffer.wrap(value.toString().getBytes());

                sbc2.write(buf2);
                buf1.flip();
            }
        }catch (IOException e){}


    }

    public void writeChanel(String from, String to) throws IOException {

        Path file = new File(to).toPath();
        String text = "BLA BLA BLA ";
        ByteBuffer buf = ByteBuffer.wrap(text.getBytes());

        try{
            FileChannel fc = FileChannel.open(file, StandardOpenOption.WRITE);
            fc.write(buf);
        }catch (IOException e){}
    }

    public void readChannel(String from, String to) throws IOException {

        Path file = (new File(to)).toPath();
        try {
            SeekableByteChannel sbc = Files.newByteChannel(file, StandardOpenOption.READ);
            ByteBuffer buf = ByteBuffer.allocate(10);

            String encoding = System.getProperty("file.encoding");

            while (sbc.read(buf) > 0) {
                buf.rewind();
                System.out.println(Charset.forName(encoding).decode(buf));
                buf.flip();
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
    }

    public void rewriteAccesToFile(String from, String to) throws IOException {
        Path fin    = Paths.get(from);
        Path fout   = FileSystems.getDefault().getPath(to);
        byte[] date;
        String l    = null;
        try {
            BufferedReader reader   = new BufferedReader(new InputStreamReader(Files.newInputStream(fin)));
            OutputStream out        = new BufferedOutputStream(Files.newOutputStream(fout));
            while ((l = reader.readLine()) != null) {

                out.write(l.getBytes(), 0, l.getBytes().length);
            }
        } catch (IOException e) {

        }
    }

    public void rewriteSmallFile(String from, String to) throws IOException {

        Path file = Paths.get(from);
        byte[] in;
        in = Files.readAllBytes(file);

        Path fileTo = FileSystems.getDefault().getPath(to);
        byte[] out = in;
        Files.write(fileTo, out);
    }

    public void accessToFile(String path) throws IOException {

        Path file = Paths.get(path);
        Charset charset = Charset.forName("US-ASCII");
        String s = "bla bla bla bla";
        BufferedWriter out = null;
        try {
            out = Files.newBufferedWriter(file, charset);
            out.write(s, 0, s.length());
        } catch (IOException e) {

        } finally {
            if (out != null)
                out.close();
        }
    }

    public void listPath(String path) throws IOException {
        Path p1 = Paths.get(path);
        Path p2 = FileSystems.getDefault().getPath(path);
        Path p3 = Paths.get(path);
        //Paths.get(URI.create("file:///tmp/file"));
        Path p4 = new File(path).toPath();

        BasicFileAttributes attr = Files.readAttributes(p1, BasicFileAttributes.class);
        System.out.println(attr.isOther() + " " + attr.creationTime());
    }

    public void listDirectory(String path) {

        File files = new File(path);
        File[] dir = files.listFiles();
        if (dir == null) return;
        for (File var : dir) {
            if (!var.isDirectory()) {
                System.out.println(var.getName());
            }
            if (var.getName().endsWith("txt")) {
                System.out.println(var.getName());
            }
            if (var.getName().startsWith("l")) {
                System.out.println(var.getName());
            }
        }

    }

    public static void main(String[] args) {

        try {
            cIO file = new cIO();
            long start = 0;
            long stop = 0;
            start = System.currentTimeMillis();
            //file.listDirectory("C://wamp/");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            //file.listPath("C://wamp/images_on.bmp");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            //file.accessToFile("C://wamp/images_on.bmp");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            //file.rewriteSmallFile("text.txt", "copytext7.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            //file.rewriteAccesToFile("text.txt", "copytext8.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));

            start = System.currentTimeMillis();
            file.reWrite("text.txt", "copytext9.txt");
            stop = System.currentTimeMillis();
            System.out.println("Czas wykonania (w milisekundach): " + (stop - start));


        } catch (IOException e) {

        }
    }
}
