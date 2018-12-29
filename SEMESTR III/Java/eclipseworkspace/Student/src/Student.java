class Student implements Comparable<Student> {
 public String imie;
 public String nazwisko;
 public int nrAlbumu;
 public int zmienna=0;
 public Student(String imie, String nazwisko, int nrAlbumu) {
  this.imie = imie;
  this.nazwisko = nazwisko;
  this.nrAlbumu = nrAlbumu;
 }
 public int compareTo(Student obiekt) {
	 if(zmienna==0)
	 {
		 return nazwisko.compareTo(obiekt.nazwisko);
 
	 }
	 else if(zmienna==1)
	 {
		 return imie.compareTo(obiekt.imie);
	 }
	 else
	 {
		 return nrAlbumu - obiekt.nrAlbumu;
	 }
 }

 public String toString() {
  return (nazwisko + " " + imie + " " + nrAlbumu);
 }
}