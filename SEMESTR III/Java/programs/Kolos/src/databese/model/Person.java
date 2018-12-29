package databese.model;
public class Person {
	
    private int id;
    private String imie;
    private String nazwisko;
    private String nr;
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public String getPhone() {
        return nr;
    }
    public void setPhone(String nr) {
        this.nr = nr;
    }
 
    public Person() { }
    public Person(int id, String imie, String nazwisko, String nr) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr = nr;
    }
 
    @Override
    public String toString() {
        return "["+id+"] - "+imie+" "+nazwisko+" - "+nr;
    }
}