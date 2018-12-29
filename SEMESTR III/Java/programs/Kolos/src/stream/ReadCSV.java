package stream;

/**
 * Created by Kris on 2015-05-04.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    public static void main(String[] args) {

        ReadCSV obj = new ReadCSV();
        obj.run();

    }

    public void run() {

        List<Double[]> lvalue = new ArrayList<>();
        String csvFile = "src/stream/csv.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int size = 0;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine(); line = br.readLine();
            //System.out.println(line);
            String[] variables = line.split(cvsSplitBy);
            size = variables.length;

            while ((line = br.readLine()) != null) {

                String[] v = line.split(cvsSplitBy);
                Double[] tvalue = new Double[size];

                for(String var : v){
                    System.out.println(var);
                }

                for(int i = 0; i < size ;i++)
                    tvalue[i] = Double.parseDouble(v[i]);

                lvalue.add(tvalue);
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





        System.out.println("Done");
    }

}
