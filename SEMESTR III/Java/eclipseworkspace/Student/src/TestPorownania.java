import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class TestPorownania {
	public static void main(String[] args) throws SecurityException,
			FileNotFoundException, IOException {
		Student[] studenci = new Student[6];
		studenci[0] = new Student("Arek", "Zielony", 4325);
		studenci[1] = new Student("Beata", "Mruczek", 7453);
		studenci[2] = new Student("Celina", "Lonkis", 2644);
		studenci[3] = new Student("Daria", "Annas", 1632);
		studenci[4] = new Student("Franek", "Mruczek", 3856);
		studenci[5] = new Student("Grazyna", "Zielony", 4287);
		/*
		 * for(Student st: studenci) st.zmienna=0; Arrays.sort(studenci); for
		 * (Student st: studenci){ System.out.println(st); }
		 */
		System.out.println("Po imieniu");
		for (Student st : studenci)
			st.zmienna = 1;
		Arrays.sort(studenci);
		for (Student st : studenci) {
			System.out.println(st);
		}/*
		 * System.out.println("Po albumie"); for(Student st: studenci)
		 * st.zmienna=2; Arrays.sort(studenci); for (Student st: studenci){
		 * System.out.println(st); }
		 */
		for (int n = 0; n < studenci.length; n++) {

			Properties p = new Properties();

			p.setProperty("Imie", studenci[n].imie);

			p.store(new FileOutputStream("jakiesimie_" + String.valueOf(n) + ".txt"), "");
		}
	}
}