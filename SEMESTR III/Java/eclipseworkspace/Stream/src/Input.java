import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPOutputStream;

public class Input {

		public static void main(String[] args) throws IOException{
			
			byte[] buffer = new byte[5];
	        File fi = new File("cokolwiek.txt");
	        FileInputStream fis = new FileInputStream(fi);
	        int c;
	        while ((c = fis.read(buffer)) != -1) {
	            System.out.print(new String(buffer, 0, c));
	        }
	        fis.close();

	        File f = new File("jakistekst.txt");
	        f.createNewFile();
	        FileOutputStream fos = new FileOutputStream(f);
	        fos.write("ala ma kota".getBytes());
	        fos.flush();
	        fos.close();
	        
	/*        PrintStream ps = new PrintStream(f);
	        ps.println("co kolwiek");
	        ps.close();
	        fos = new FileOutputStream(f, true);
	        fos.write("ala ma kota".getBytes());
	        fos.flush();
	        fos.close(); */
	        
	        byte[] b = new byte[1]; 
	        URL url = new URL("http://www.google.pl");
	        URLConnection con = url.openConnection();
	        InputStream is = con.getInputStream();
	        while(is.available()>0){
	            is.read(b);
	            System.out.print(new String(b));
		}
	        
	        FileOutputStream fer = new FileOutputStream("plik.txt.gz");
	        GZIPOutputStream gos = new GZIPOutputStream(fer);
	        gos.write("ala ma kota".getBytes());
	        gos.close();
}
}
