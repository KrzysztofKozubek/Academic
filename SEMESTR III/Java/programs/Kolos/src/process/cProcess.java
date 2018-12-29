package process;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cProcess {

    public static void main(String[] args){
        try{
            String s;
            Process ps = Runtime.getRuntime().exec("cmd");
            BufferedReader bri = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            while((s = bri.readLine()) != null){
                System.out.println(s);
            }
            bri.close();

            while((s = bre.readLine()) != null){
                System.out.println(s);
            }
            bre.close();

            ps.waitFor();

        }catch (IOException e){e.printStackTrace();}
        catch (InterruptedException p){p.printStackTrace();}

        System.out.print("GOTOWE");
    }
}
