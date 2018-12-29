package routing;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import html.HTMLTemplate;
import routing.operation.Operation;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RedHat on 20.01.2018.
 * Handle only method GET
 */
public class MainHandler extends Handler implements HttpHandler {

    public MainHandler() {}

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        this.httpExchange = httpExchange;
        this.methodsToHandle = new ArrayList<String>() {{add("GET");}};

        if (this.checkClassShouldHandle(httpExchange)) {

            if (this.checkRequestExists(
                    httpExchange.getRequestMethod() ,
                    httpExchange.getRequestURI().toString())) {
                try {
                    execute();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    System.out.println(ErrorHandler.errorCSV);
                    delegate();
                }
            } else
                delegate();
        } else
            delegate();
    }

    @Override
    public void delegate() {
        JSONHandler jsonHandler = new JSONHandler.Builder()
                .httpExchange(httpExchange)
                .methodToHandle(new ArrayList<String>() {{add("POST");add("PUT");add("DELETE");}})
                .build();

        try {
            jsonHandler.handle(httpExchange);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sth wrong in class " + jsonHandler.getClass().getSimpleName());
        }
    }

    @Override
    public boolean checkRequestExists(String method, String URL) {

        Map<String, List<DataFromURLCSV>> listMap = (Map<String, List<DataFromURLCSV>>)(Map) OperatorCSV.handleURL.asMap();

        for (Map.Entry var : listMap.entrySet()) {

            if (var.getKey().equals("GET")) {
                List<DataFromURLCSV> tmp = (List<DataFromURLCSV>) var.getValue();
                for (DataFromURLCSV data : tmp) {
                    if (URL.equals(data.URL) || (URL.startsWith(data.URL) && URL.charAt(data.URL.length()) == '/')) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public DataFromURLCSV takeDataURLFromCSV(HttpExchange httpExchange) {

        Map<String, List<DataFromURLCSV>> listMap = (Map<String, List<DataFromURLCSV>>)(Map) OperatorCSV.handleURL.asMap();
        String URL = httpExchange.getRequestURI().toString();

        for (Map.Entry var : listMap.entrySet()) {

            if (var.getKey().equals("GET")) {
                List<DataFromURLCSV> tmp = (List<DataFromURLCSV>) var.getValue();
                for (DataFromURLCSV data : tmp) {
                    if (URL.equals(data.URL) ||
                            (URL.toString().startsWith(data.URL) && URL.charAt(data.URL.length()) == '/')) {
                        if (URL.toString().length() > data.URL.toString().length())
                            data.params = new String[]{URL.substring(data.URL.length() + 1)};
                        return data;
                    }
                }
            }
        }

        return null;
    }

    //    @Override
//    public void execute() throws NoSuchMethodException, IOException {
//
//        DataFromURLCSV data = OperatorCSV.handleURL.get(
//                this.httpExchange.getRequestMethod().toString()).stream()
//                .filter(dataURL -> dataURL.URL.equals(httpExchange.getRequestURI().toString()))
//                .findFirst().orElse(null);
//
//        if (data == null)
//            delegate();
//
//        HTMLTemplate template = new HTMLTemplate.BuilderTemplateHTML()
//                .fileNameHTML(data.URL).build();
//
//        String result = null;
//        try {
//            result = template.get(this.httpExchange.getRequestURI().toString(), ArrayListMultimap.create());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Problem in template");
//        }
//
//        display(result);
//    }

    static class Builder {

        private HttpExchange    httpExchange    = null;
        private List<String>    methodToHandle  = new ArrayList<String>();

        public Builder HhttpExchange(HttpExchange httpExchange) {
            this.httpExchange = httpExchange;
            return this;
        }

        public Builder methodToHandle(List<String> methodToHandle) {
            this.methodToHandle = methodToHandle;
            return this;
        }

        public MainHandler build() {
            return new MainHandler(this);
        }
    }

    public MainHandler(Builder builder) {
        this.httpExchange       = builder.httpExchange;
        this.methodsToHandle    = builder.methodToHandle;
    }

}
