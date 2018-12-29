package routing;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import routing.operation.Operation;
import sun.applet.Main;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by RedHat on 20.01.2018.
 */
public abstract class Handler {

    HttpExchange    httpExchange    = null;
    List<String>    methodsToHandle = null;

    public abstract void delegate();

    public void execute() throws NoSuchMethodException, IOException {

        DataFromURLCSV data = takeDataURLFromCSV(httpExchange);

        if (data == null)
            delegate();

        String className = data.className;
        Operation operation = null;
        try {
            Class c = Class.forName(className);
            Object instance = c.newInstance();

            // create params
            Class[] classes = new Class[data.params.length];
            for (int i = 0; i < data.params.length; i++)
                classes[i] = Class.forName(data.params[i].getClass().getName());

            Method method = instance.getClass().getMethod(data.methodName, classes);
            Object result = method.invoke(instance, data.params);
            display((String) result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ErrorHandler.errorCSV);
            delegate();
        }
    }

    public boolean checkClassShouldHandle(HttpExchange h) {

        return methodsToHandle.stream()
                .anyMatch(t -> t.equals(h.getRequestMethod()));
    }

    public boolean checkRequestExists(String method, String URL) {

        return OperatorCSV.handleURL.get(method).stream()
                .filter(data -> data.URL.equals(URL))
                .findFirst().orElse(null) == null ? false : true;
    }

    public void display(String response) throws IOException{

// https://bugs.openjdk.java.net/browse/JDK-8182168
        try {
            byte[] bs = response.getBytes("UTF-8");
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(bs);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataFromURLCSV takeDataURLFromCSV(HttpExchange httpExchange) {

        return OperatorCSV.handleURL.get(
                this.httpExchange.getRequestMethod().toString()).stream()
                .filter(dataURL -> dataURL.URL.equals(httpExchange.getRequestURI().toString()))
                .findFirst().orElse(null);
    }
}
