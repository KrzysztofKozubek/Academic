package db;

public class DataBaseMysql extends DatabaseHandler {

    static DataBaseMysql dataBaseMysql = null;

    private DataBaseMysql() {}

    public static DataBaseMysql getDataBaseMysql() {

        if(dataBaseMysql == null)
            dataBaseMysql = new DataBaseMysql();

        return dataBaseMysql;
    }
}
