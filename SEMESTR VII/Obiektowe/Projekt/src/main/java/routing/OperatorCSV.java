package routing;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by RedHat on 20.01.2018.
 */
public class OperatorCSV {

    private final static String PATH_CSV_URLS = "URL.csv";
    public static Multimap<String, DataFromURLCSV> handleURL = ArrayListMultimap.create();

    static {
        System.out.println("LOADING FILE CSV");
        try (Stream<String> stream = Files.lines(Paths.get(PATH_CSV_URLS))) {
            stream.forEach(OperatorCSV::addURLToHandleURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot open this file");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Incorrect file CSV(routing)");
        }
        System.out.println("LOAD COMPLEATE");
    }

    /*
    Example request in file CSV(routing)
    METHOD, URL, CLASS, METHOD_TO_CALL, PARAM1, PARAM2, ....
     */
    private static void addURLToHandleURL(String lineCSV) throws NullPointerException {

        String[] line = lineCSV.split(", ");
        String[] params = new String[line.length - 4];
        for (int i = 4; i < line.length; i++)
            params[i - 4] = line[i];

        handleURL.put(line[0], new DataFromURLCSV(line[1], line[2], line[3], params));
    }
}

class DataFromURLCSV {

    public String URL = null;
    public String className = null;
    public String methodName = null;
    public String[] params = null;

    public DataFromURLCSV(String URL, String className, String methodName, String[] params) {
        this.URL = URL;
        this.className = className;
        this.methodName = methodName;
        this.params = params;
    }
}