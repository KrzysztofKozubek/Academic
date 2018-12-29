package databese;

import java.util.List;
 
import java.util.Scanner;

import databese.bookPhone.*;
import databese.model.Person;

public class JdbcTest {
 
    public static void main(String[] args) {
    	
    	BookPhone b = new BookPhone();
    	//System.out.println("Co chcesz zrobić: \n1.Wyświetl rekordy\n2.Znajdz osobę\n3.Dodaj osobe\nq. Wyj�cie z programu: ");
    	Scanner scan = new Scanner(System.in);
    	String val = "";
    	
    	while(!val.equalsIgnoreCase("q")){
    		System.out.println("Co chcesz zrobić: \n1.Wyświetl rekordy\n2.Znajdz osobę\n3.Dodaj osobę\nq. Wyjście z programu: ");
    		val = scan.next().trim();
    		
    		if(val.equalsIgnoreCase("1")){
    			List<Person> persons = b.selectPerson();
    			System.out.println("Książka telefoniczna: ");
    	        for(Person c: persons)
    	            System.out.println(c);
    		}else
    		if(val.equalsIgnoreCase("2")){
    			System.out.println("Podaj nazwisko: ");
    			List<Person> persons2 = b.findPerson(scan.next().trim());
    			System.out.println("Znalezione kontakty: ");
    	        for(Person c: persons2)
    	            System.out.println(c);    			
    		}else
    		if(val.equalsIgnoreCase("3")){
    			System.out.println("name: ");
    			String name = scan.next().trim();
    			System.out.println("surname: ");
    			String surname = scan.next().trim();
    			System.out.println("phone: ");
    			String phone = scan.next().trim();
    			b.insertPerson(name, surname, phone);
    		}
    		
    		if(!val.equalsIgnoreCase("q"))
    		val = "";    		
    	}
    	
        System.out.println("Koniec programu");
        //b.insertPerson("Krzysztof", "Kozubek", "+4853653261");
        //b.insertPerson("Niki", "Skórka", "+48534537322");
        //b.insertPerson("Tata", "", "+48664715467");
        //b.deletePerson(1);
        
       // b.updatePerson(2, "name", "Kamil");
        
        
        
        //List<Person> persons = b.selectPerson();
        //List<Person> persons2 = b.findPerson("Kozubek");
        
        //System.out.println("Książka telefoniczna: ");
        //for(Person c: persons)
        //    System.out.println(c);
        
        //System.out.println("Znalezione kontakty: ");
        //for(Person c: persons2)
        //    System.out.println(c);
 
        //b.deletePerson(10);
        //b.deletePerson(11);
        //b.deletePerson(12);
        
        b.closeConnection();
    }
}