package db;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RedHat on 20.01.2018.
 */
public class Movie {

    public Integer id;
    public String title;
    public String poster;
    public String genre;
    public String premier;
    public String mark;
    public String description;

    public Map<String, String> variable = new HashMap<>();

    public Movie(Integer id, String title, String poster, String genre, String premier, String mark, String description) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.genre = genre;
        this.premier = premier;
        this.mark = mark;
        this.description = description;

        variable.put("id", String.valueOf(id));
        variable.put("title", title);
        variable.put("poster", poster);
        variable.put("genre", genre);
        variable.put("premier", premier);
        variable.put("mark", mark);
        variable.put("description", description);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getGenre() {
        return genre;
    }

    public String getPremier() {
        return premier;
    }

    public String getMark() {
        return mark;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPremier(String premier) {
        this.premier = premier;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get(String field) {
        return variable.get(field);
    }

    public String getJSON() {

        StringBuffer result = new StringBuffer();
        for (Map.Entry<String, String> var : variable.entrySet()) {
            result.append("\"" + var.getKey() + "\":\"" + var.getValue() + "\",");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "\"id\":\"" + id + '\"' +
                ", \"title\":\"" + title + '\"' +
                ", \"poster\":\"" + poster + '\"' +
                ", \"genre\":\"" + genre + '\"' +
                ", \"premier\":\"" + premier + '\"' +
                ", \"mark\":\"" + mark + '\"' +
                ", \"description\":\"" + description + '\"' +
                '}';
    }
}
