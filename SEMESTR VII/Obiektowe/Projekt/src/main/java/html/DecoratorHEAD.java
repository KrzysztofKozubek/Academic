package html;

import com.google.common.collect.Multimap;
import db.Movie;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by RedHat on 27.01.2018.
 */
interface Decorator {

    String get(String nameFileHTML, Multimap<String, Movie> nodes) throws IOException;

    public String getTemplate();
}

public class DecoratorHEAD implements Decorator {
    Decorator decorator;
    String PATH_TO_HEAD_TEMPLATE = "./src/main/java/html/templates/headTemplate.html";

    public DecoratorHEAD(Decorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public String get(String nameFileHTML, Multimap<String, Movie> nodes) throws IOException {
        return HTMLTemplate.readFile(PATH_TO_HEAD_TEMPLATE) + decorator.get(nameFileHTML, nodes);
    }

    @Override
    public String getTemplate() {
        try {
            return HTMLTemplate.readFile(PATH_TO_HEAD_TEMPLATE) + decorator.getTemplate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return decorator.getTemplate();
    }
}