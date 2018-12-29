package routing;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RedHat on 20.01.2018.
 */
public class ErrorHandler extends Handler implements HttpHandler {

    public static final String errorCSV = "Incorrect file CSV(routing)";
    public static final String error404 = "This side no exists";
    public static final String error    = "Cannot show u this error, check console";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (!this.checkRequestExists(
                httpExchange.getRequestMethod(),
                httpExchange.getRequestURI().toString())) {
            display(error404);
        } else {
            display(error);
        }
    }

    @Override
    public void delegate() {}

    @Override
    public void execute() throws NoSuchMethodException, IOException {}


    static class Builder {

        private HttpExchange    httpExchange    = null;
        private List<String>    methodToHandle  = new ArrayList<String>();

        public Builder httpExchange(HttpExchange httpExchange) {
            this.httpExchange = httpExchange;
            return this;
        }

        public Builder methodToHandle(List<String> methodToHandle) {
            this.methodToHandle = methodToHandle;
            return this;
        }

        public ErrorHandler build() {
            return new ErrorHandler(this);
        }
    }

    public ErrorHandler(Builder builder) {
        this.httpExchange       = builder.httpExchange;
        this.methodsToHandle    = builder.methodToHandle;
    }
}
