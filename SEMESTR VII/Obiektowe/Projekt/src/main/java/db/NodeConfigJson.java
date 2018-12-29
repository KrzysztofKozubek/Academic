package db;

//important nodes in file config json
public enum NodeConfigJson {
    TYPE("type"), URL("url"), PORT("port"), USER("user"), PASSWORD("pass"), DATABASE("db");

    private final String node;

    NodeConfigJson(String i) {
        node = i;
    }

    public String getNode() {
        return node;
    }
}