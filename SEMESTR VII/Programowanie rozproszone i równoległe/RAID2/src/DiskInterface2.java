/**
 * Interfejs obslugi dysku "fizycznego"
 * 
 * @author oramus
 *
 */
public interface DiskInterface2 {

	/**
	 * Zlecenie zapisu danej value do sektora sector
	 * 
	 * @param sector
	 *            numer sektora, do ktorego nalezy wykonac zapis
	 * @param value
	 *            wartosc do zapisu
	 */
	public void write(int sector, int value);

	/**
	 * Zlecenie odczytu danej z dysku z sektora o numerze sector
	 * 
	 * @param sector
	 *            numer sektora, ktory ma zostac odczytany
	 * @return wartosc zapisana w sektorze
	 */
	public int read(int sector);

	/**
	 * Zwraca rozmiar dysku w sektorach
	 * 
	 * @return liczba sektorow na dysku
	 */
	public int size();
}
