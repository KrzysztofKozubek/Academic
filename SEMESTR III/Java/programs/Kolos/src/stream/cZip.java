package stream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class cZip {
    public static void main(String[] args) {

        try {
            FileOutputStream fos = new FileOutputStream("atest.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            String file1Name = "text.txt";

            addToZipFile(file1Name, zos);

            zos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

        System.out.println("Writing '" + fileName + "' to zip file");

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    public static void zippowanie(String... strings) throws IOException{

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("tes.zip"));
        for(String var : strings)
            addZip(zos, var);
        zos.close();
    }

    public static void addZip(ZipOutputStream zos, String fileName) throws IOException{

        FileInputStream zis = new FileInputStream(new File(fileName));
        zos.putNextEntry(new ZipEntry(fileName));
        int l;
        while((l = zis.read()) >=0)
            zos.write(l);
        zis.close();
    }




/*
    public static void addToZip(ZipOutputStream zos, String fileName) throws IOException{

        FileInputStream fis = new FileInputStream(new File(fileName));
        zos.putNextEntry(new ZipEntry(fileName));
        int l;
        while((l = fis.read()) >= 0)
            zos.write(l);
    }

    public static void zippowanie(String... strings) throws IOException{

        FileOutputStream fos = new FileOutputStream("test.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);

        for(String var : strings)
            addToZip(zos, var);
        zos.close();
        fos.close();
    }
*/










}
