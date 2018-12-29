import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadingAllFiles {
        public static void main(String args[]) throws IOException {
        	
        		Scanner input = new Scanner(System.in);
        		String text = input.nextLine();
        		File myFile = new File(text); 
                File [] fileList; 
                fileList = myFile.listFiles();
                for(int i=0; i<fileList.length; i++) 
                    System.out.println(fileList[i].getName());

        }
}