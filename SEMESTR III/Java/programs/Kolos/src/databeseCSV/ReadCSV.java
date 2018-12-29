package databeseCSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2015-05-06.
 */
public class ReadCSV {

    static List<String[]> readCSV(String csvFile){
        List<String[]> lvalue = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int size = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine(); line = br.readLine();
            String[] variables = line.split(cvsSplitBy);
            size = variables.length;

            while ((line = br.readLine()) != null) {
                String[] v = line.split(cvsSplitBy);
                String[] var = new String[v.length];
                System.out.println(line);
                for(int i=0; i < v.length; i++)
                    var[i] = v[i];
                lvalue.add(var);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lvalue;
    }


}
