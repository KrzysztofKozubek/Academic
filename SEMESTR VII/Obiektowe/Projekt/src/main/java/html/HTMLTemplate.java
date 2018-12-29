package html;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import db.Movie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by RedHat on 20.01.2018.
 */
public class HTMLTemplate implements Decorator {

    private static final String PATH_FILES_TEMPLATES_HTML   = "./src/main/java/html/templates/";

    String fileNameHTML = null;

    public String getTemplate() {

        String result = null;
        try {
            result = readFile(PATH_FILES_TEMPLATES_HTML + fileNameHTML + ".html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Cannot open template: " + PATH_FILES_TEMPLATES_HTML + fileNameHTML + ".html");
        }
        return result;
    }

    @Override
    public String get(String nameFileHTML, Multimap<String, Movie> nodes) throws IOException {
        return this.overrideHTML(readFile(PATH_FILES_TEMPLATES_HTML + nameFileHTML + ".html"), nodes);
    }

    /*
    Example node to override:
    <html>
    {{ExampleNode}} ...
     */
    private String overrideHTML(String stringToOverride, Multimap<String, Movie> p_nodes) {

        Matcher matcher = null;

        String nodeWithSpecialCharacter = "\\{\\{(.*)\\}\\}";
        String HTMLtmpFor               = null;
        String regularExpressionNode    = "\\{\\{(.*)\\}\\}";
        String regularExpressionFor     = "\\{\\{(.*)\\}\\}";

        StringBuffer HTMLToChangeLoopFor= new StringBuffer();

        Map<String, List<Movie>> nodes  = (Map<String, List<Movie>>) (Map)Multimaps.asMap(p_nodes);
        Field[] fields = Movie.class.getFields();


        // find all for: example: for(node){...}
        for (Map.Entry node : nodes.entrySet()) {
            matcher = findLoopFor(node.getKey().toString(), stringToOverride);

            while (matcher.find()) {
                // change node in for to correct text
                for (Movie var : nodes.get(node.getKey())) { // List<Movie>
                    HTMLtmpFor = matcher.group(1);
                    for (int i = 0; i < fields.length; i++)
                        HTMLtmpFor = HTMLtmpFor.replaceAll("\\{\\{"+ fields[i].getName() +"\\}\\}", var.get(fields[i].getName()));
                    HTMLToChangeLoopFor.append(HTMLtmpFor);
                }

            }
            stringToOverride = stringToOverride.replaceAll(matcher.pattern().toString(), HTMLToChangeLoopFor.toString());
        }
        // find simple node to change
        for (Map.Entry node : nodes.entrySet()) {
            nodeWithSpecialCharacter = "\\{\\{" + node.getKey() + "\\}\\}";
            stringToOverride = stringToOverride
                    .replaceAll(nodeWithSpecialCharacter, node.getValue().toString());
        }
        // remove don't handled nodes
        return stringToOverride.replaceAll(nodeWithSpecialCharacter, "");
    }

    private Matcher findLoopFor(String nodeFor, String text) {

        Pattern patternLoopFor = null;
        Matcher matcher = null;

        //for(nodeFor){[...]}
        String pattern = "for\\(" + nodeFor + "\\)\\{(.*)\\}";
        patternLoopFor = Pattern.compile(pattern);
        return patternLoopFor.matcher(text);
    }



    public static String readFile(String fileNameHTML) throws FileNotFoundException {

        StringBuffer result = new StringBuffer();
        try (Stream<String> stream = Files.lines(Paths.get(fileNameHTML))) {
            stream.forEach(result::append);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot open file");
            throw new FileNotFoundException();
        }
        return result.toString();
    }


    public static class BuilderTemplateHTML {

        String fileNameHTML = null;

        public BuilderTemplateHTML fileNameHTML(String fileNameHTML) {
            this.fileNameHTML = fileNameHTML;
            return this;
        }

        public HTMLTemplate build() {
            return new HTMLTemplate(this);
        }
    }

    HTMLTemplate(BuilderTemplateHTML builder) {
        this.fileNameHTML = builder.fileNameHTML;
    }
}
