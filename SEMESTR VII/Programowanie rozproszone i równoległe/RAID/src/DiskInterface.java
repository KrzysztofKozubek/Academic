/**
 * Interfejs obslugi dysku "fizycznego"
 * 
 * @author oramus
 *
 */
public interface DiskInterface {

	/**
	 * Klasa wyjatek - informacja o trwalym uszkodzeniu dysku
	 * 
	 * @author oramus
	 *
	 */
	public class DiskError extends Exception {
	};

	/**
	 * Zlecenie zapisu danej value do sektora sector
	 * 
	 * @param sector
	 *            numer sektora, do ktorego nalezy wykonac zapis
	 * @param value
	 *            wartosc do zapisu
	 * @throws DiskError
	 *             uszkodzenie dysku
	 */
	public void write(int sector, int value) throws DiskError;

	/**
	 * Zlecenie odczytu danej z dysku z sektora o numerze sector
	 * 
	 * @param sector
	 *            numer sektora, ktory ma zostac odczytany
	 * @return wartosc zapisana w sektorze
	 * @throws DiskError
	 *             informacja o uszkodzeniu dysku
	 */
	public int read(int sector) throws DiskError;

	/**
	 * Zwraca rozmiar dysku w sektorach
	 * 
	 * @return liczba sektorow na dysku
	 */
	public int size();
}
