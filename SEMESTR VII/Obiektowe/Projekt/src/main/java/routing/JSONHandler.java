package routing;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.NodeConfigJson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import routing.operation.Operation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by RedHat on 20.01.2018.
 */
public class JSONHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (this.checkClassShouldHandle(httpExchange)) {
            if (this.checkRequestExists(
                    httpExchange.getRequestMethod(),
                    httpExchange.getRequestURI().toString())) {
                try {
                    execute();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    System.out.println("LATAJACA DUPA W KODZIE");
                    delegate();
                }
            } else
                delegate();
        } else
            delegate();
    }

    @Override
    public void execute() throws NoSuchMethodException, IOException {

        DataFromURLCSV data = OperatorCSV.handleURL.get(
                this.httpExchange.getRequestMethod().toString()).stream()
                .filter(dataURL -> dataURL.URL.equals(httpExchange.getRequestURI().toString()))
                .findFirst().orElse(null);

        if (data == null)
            delegate();

        String className = data.className;
        Operation operation = null;
        try {
            Class c = Class.forName(className);
            Object instance = c.newInstance();

            // create params
            Class[] classes = new Class[] { Class.forName(Map.class.getName().toString()) };
            Map<String, String> params = getValueNode(readJSONFromHttp());

            Method method = instance.getClass().getMethod(data.methodName, classes);
            Object result = method.invoke(instance, params);
            display((String) result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ErrorHandler.errorCSV);
            delegate();
        }
    }

    @Override
    public void delegate() {

        ErrorHandler errorHandler = new ErrorHandler.Builder()
                .httpExchange(httpExchange)
                .methodToHandle(new ArrayList<>())
                .build();

        try {
            errorHandler.handle(httpExchange);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sth wrong in class " + errorHandler.getClass().getSimpleName());
        }
    }

    static class Builder {

        private HttpExchange    httpExchange    = null;
        private List<String>    methodToHandle  = new ArrayList<String>();

        public JSONHandler.Builder httpExchange(HttpExchange httpExchange) {
            this.httpExchange = httpExchange;
            return this;
        }

        public JSONHandler.Builder methodToHandle(List<String> methodToHandle) {
            this.methodToHandle = methodToHandle;
            return this;
        }

        public JSONHandler build() {
            return new JSONHandler(this);
        }
    }

    public JSONHandler(JSONHandler.Builder builder) {
        this.httpExchange       = builder.httpExchange;
        this.methodsToHandle    = builder.methodToHandle;
    }

    public static List<String> acceptedNode = new ArrayList<String>() {{
        add("search");
        add("idMovie");
        add("link");
        add("title");
        add("poster");
        add("premier");
        add("id");
        add("description");
        add("genre");
        add("mark");
    }};

    private Map<String, String> getValueNode(String JSON) {

        JSONParser parser = new JSONParser();
        Map<String, String> result = new HashMap<>();
        try {
            JSONObject obj = (JSONObject) parser.parse(JSON);
            for (String var : acceptedNode)
                result.put(var, (String) obj.get(var));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String readJSONFromHttp() {

        StringBuffer result = new StringBuffer();
        int i = 0;
        InputStream inputStream = httpExchange.getRequestBody();

        do {
            try {
                i = inputStream.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i > -1)
                result.append((char)i);
        } while (i > -1);

        return result.toString();
    }
}
