package databeseCSV;

import databeseCSV.model.Sensor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kris on 2015-05-06.
 */
public class Datebese {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:sensors.db";

    public static String tableName = "sensors";

    private Connection conn;
    private Statement stat;

    public Datebese(){
        try {
            Class.forName(Datebese.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Missing driver JDBC");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Error with open connection");
            e.printStackTrace();
        }
        //insertSensors(ReadCSV.readCSV("C:\\a.csv"));
        //show("SELECT name FROM sqlite_master");
        //show();
        //show(find("light", "5", "id", false));
        createTables();
    }

    public boolean questionMysql(String question){
        try {
            stat.execute(question);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createTables(){
        String createQuestion = "CREATE TABLE IF NOT EXISTS `" + tableName + "`(`id` INTEGER PRIMARY KEY AUTOINCREMENT, `accelermmeterX` double NULL, `accelermmeterY` double NULL, `accelermmeterZ` double NULL, `gravityX` double NULL, `gravityY` double NULL, `gravityZ` double NULL, `linearAccelerationX` double NULL, `linearAccelerationY` double NULL, `linearAccelerationZ` double NULL, `gyroscopeX` double NULL, `gyroscopeY` double NULL, `gyroscopeZ` double NULL, `light` double NULL, `magneticFieldX` double NULL, `magneticFieldY` double NULL, `magneticFieldZ` double NULL, `orientationX` double NULL, `orientationY` double NULL, `orientationZ` double NULL, `proximity` double NULL, `armosphericPressure` double NULL, `temperature` double NULL, `soundLevel` double NULL, `locationLatitude` double NULL, `locationLongitude` double NULL, `locationAltitude` double NULL, `locationAltitudeGoogle` double NULL, `locationAltitudeAtmosphericPressure` double NULL, `locationSpeed` double NULL, `locationAccuracy` double NULL, `locationOrientation` double NULL, `satellitesInRange` varchar(255) NULL, `timeSinceStartInMs` double NULL, `dateAndTime` datetime NULL)";
        return this.questionMysql(createQuestion);
    }

    public boolean deleteSensor(int id) {
        String createQuestion = "DELETE FROM `" + tableName + "` WHERE `" + tableName + "`.`id` = " + id;
        return this.questionMysql(createQuestion);
    }

    public boolean updateSensor(int id, String what, String var) {
        String createQuestion = "UPDATE `" + tableName + "` SET "+ what + "='" + var +"' WHERE `id` = " + id;
        return this.questionMysql(createQuestion);
    }

    public List<String[]> find(String whereName, String whereValue, String[] nameKolumn, boolean contain){
        String query = "SELECT " ;
        for(String v : nameKolumn)
            query += v + ",";
        query = query.substring(0, query.length() - 1);
        if(contain)
            query += " FROM " + tableName + " WHERE " + whereName + " LIKE '%" + whereValue + "%'";
        else
            query += " FROM " + tableName + " WHERE " + whereName + " = '" + whereValue + "'";

        System.out.println(query);

        List<String[]> sensors = new LinkedList<String[]>();
        try {
            ResultSet result = stat.executeQuery(query);
            String[] help;
            while(result.next()) {
                help = new String[nameKolumn.length];
                for(int i=0;i<nameKolumn.length;i++)
                    help[i] = result.getString(nameKolumn[i]);
                    sensors.add(help);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return sensors;
    }

    public List<String[]> find(String whereName, String whereValue, String nameKolumn, boolean contain){
        String[] v = new String[1];
        v[0] = nameKolumn;
        return find( whereName, whereValue, v, contain);
    }

    public Point[] getPointFromMap(int retiresNumber){
        String query = "SELECT locationLongitude, locationAltitude FROM " + tableName + " ";
        /*
 +
                "WHERE locationLongitude IN " +
                "(SELECT locationLongitude FROM " + tableName + " GROUP BY locationLongitude HAVING count (*) > " + retiresNumber + ") " +
                "AND " +
                "locationAltitude IN " +
                "(SELECT locationAltitude FROM " + tableName + " GROUP BY locationAltitude HAVING COUNT (*) > " + retiresNumber + ")";
         */
        Point[] points;
        try {
            ResultSet result = stat.executeQuery(query);
            points = new Point[result.getRow() + 1];
            int i = 0;
            while(result.next()) {
                System.out.println(result.getDouble("locationLongitude") + " " + result.getDouble("locationAltitude"));
                //points[i] = new Point(result.getDouble("locationLongitude"), result.getDouble("locationAltitude"));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return points;
    }


    public boolean insertSensor(String[] date) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into " + tableName + " values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            for(int i=0; i < date.length; i++){
                    prepStmt.setString(i + 1, date[i]);
            }
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error during insert sensor");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertSensors(List<String[]> date) {
        try {
            PreparedStatement prepStmt;

            for(String[] var : date){
                prepStmt = conn.prepareStatement(
                        "insert into " + tableName + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                for(int i=0; i < var.length; i++){
                    prepStmt.setString(i + 1, var[i]);
                }
                prepStmt.execute();
            }
        } catch (SQLException e) {
            System.err.println("Error during insert Sensors");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Mistake with close connect");
            e.printStackTrace();
        }
    }


    public void show(List<String[]> value){

        for(String[] var : value){
            for(String v : var){
                System.out.print(v);
            }
            System.out.println();
        }
    }

    public void show(String query){
        try {
            ResultSet result = stat.executeQuery(query);
            while(result.next()) {
                System.out.println(result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        String query = "SELECT id FROM " + tableName;
        try {
            ResultSet result = stat.executeQuery(query);
            while(result.next()) {
                System.out.println(result.getDouble("accelermmeterX"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}