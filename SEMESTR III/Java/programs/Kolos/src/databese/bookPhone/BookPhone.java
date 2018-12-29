package databese.bookPhone;

import databese.model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class BookPhone {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";
 
    private Connection conn;
    private Statement stat;
 
    public BookPhone() {
        try {
            Class.forName(BookPhone.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
 
        createTables();
    }
 
    public boolean questionMysql(String question, String error){
    	try {
            stat.execute(question);
        } catch (SQLException e) {
            System.err.println("Blad przy " + error);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean createTables()  {
        String createQuestion = "CREATE TABLE IF NOT EXISTS person (id_person INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255), surname varchar(255), phone int)";
        return this.questionMysql(createQuestion, " tworzeniu tabeli");
    }
 
    public boolean deletePerson(int id) {
    	String createQuestion = "DELETE FROM `person` WHERE `person`.`id_person` = " + id;
    	return this.questionMysql(createQuestion, " usuwaniu urzytkownika");
    }
 
    public boolean updatePerson(int id, String what, String var) {
    	
    	String createQuestion = "UPDATE `person` SET "+ what + "='" + var +"' WHERE `id_person` = " + id;
    	return this.questionMysql(createQuestion, createQuestion);
    }
 
    public boolean insertPerson(String imie, String nazwisko, String nr) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into person values (NULL, ?, ?, ?);");
            prepStmt.setString(1, imie);
            prepStmt.setString(2, nazwisko);
            prepStmt.setString(3, nr);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public List<Person> findPerson(String surname){
    	String query = "SELECT * FROM person WHERE surname LIKE '%" + surname + "%'";	
    	List<Person> persons = new LinkedList<Person>();
        try {
            ResultSet result = stat.executeQuery(query);
            int id;
            String imie, nazwisko, nr;
            while(result.next()) {
                id = result.getInt("id_person");
                imie = result.getString("name");
                nazwisko = result.getString("surname");
                nr = result.getString("phone");
                persons.add(new Person(id, imie, nazwisko, nr));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return persons;
    }
    
    public List<Person> selectPerson() {
    	return this.findPerson("");
    }
 
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}