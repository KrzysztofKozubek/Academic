import java.io.*;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;

public class EditFile {

    public static void compression(File[] files) throws IOException {

        EditFile.zipSave("defoult.zip", files);
    }

    public static void decompression(File file) throws IOException {

        EditFile.unZip(file.getName(), file.getName());
    }

    public static void code(File file) throws IOException {

        EditFile.code(file.getName(), "code" + file.getName());
    }

    public static void decode(File file) throws IOException {

        EditFile.decode(file.getName(), "decode" + file.getName());
    }


    public static int hash(int sign) throws IOException {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("hash.txt"));
            String l;
            while ((l = in.readLine()) != null) {

                if ((int) l.toCharArray()[0] == sign) {

                    //System.out.println((int)l.toCharArray()[0] + " " + (int)l.toCharArray()[1] + " " + sign);
                    return (int) l.toCharArray()[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sign;
    }

    public static int dehash(int sign) throws IOException {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("hash.txt"));
            String l;
            while ((l = in.readLine()) != null) {

                if ((int) l.toCharArray()[1] == sign) {

                    return (int) l.toCharArray()[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sign;
    }

    public static void code(String file, String fileOut) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(fileOut);
            int c;
            while ((c = in.read()) != -1) {

                out.write(EditFile.hash(c));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static void decode(String file, String fileOut) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(fileOut);
            int c;
            while ((c = in.read()) != -1) {

                out.write(EditFile.dehash(c));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static void unZip(String zipFile, String outputFolder) throws IOException {
        int BUFFER = 1024;
        try {
            BufferedOutputStream dest = null;

            FileInputStream fis = new
                    FileInputStream(zipFile);

            ZipInputStream zis = new
                    ZipInputStream(new BufferedInputStream(fis));

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {

                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];


                FileOutputStream fos = new
                        FileOutputStream(entry.getName());

                dest = new
                        BufferedOutputStream(fos, BUFFER);

                while ((count = zis.read(data, 0, BUFFER))
                        != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void zipSave(String nameFile, File[] files) throws IOException {

        try {
            FileOutputStream fos = new FileOutputStream(nameFile);
            ZipOutputStream zos = new ZipOutputStream(fos);


            for (File var : files) {
                EditFile.addToZipFile(var.getAbsolutePath(), zos, var.getName());
            }

            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos, String nameFile) throws IOException {

        try {

            ZipEntry zipEntry = new ZipEntry(nameFile);
            FileInputStream fis = new FileInputStream(fileName);

            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }catch (IOException ex){

        }
    }

    public static void CopyFile(String path1, String path2) throws IOException {

        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File afile = new File(path1);
            File bfile = new File(path2);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length = buffer.length;
            //copy the file content in bytes while ((length = inStream.read(buffer)) > 0){

            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);
            }


            inStream.close();
            outStream.close();


        } catch (IOException ex) {
            // ex.printStackTrace();
        }
    }

    public static void deleteFile(String path) {

        try {

            File file = new File(path);

            while (!file.delete()) {

            }

        } catch (Exception e) {

            // e.printStackTrace();

        }
    }

}
