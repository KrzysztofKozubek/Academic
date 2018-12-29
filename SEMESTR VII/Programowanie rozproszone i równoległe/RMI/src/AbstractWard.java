import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AbstractWard extends Remote {
	/**
	 * Metoda ustala maksymalna liczbe lozek w kazdym z pomieszczen.
	 *
	 * @param max
	 *            Mapa, ktorej kluczem jest identyfikator pomieszczenia, a
	 *            wskazywana przez klucz wartoscia jest maksymalna liczba lozek
	 *            w danym pomieszczeniu. Numery pomieszczen moga byc dowolne
	 *            (np. -100, 0, 123, 512) - oczywiscie wszystkie beda zgodne z
	 *            typem Integer.
	 */
	void setLimits(Map<Integer, Integer> max) throws RemoteException;

	/**
	 * Przyjecie pacjenta na oddzial do wskazanego pomieszczenia
	 *
	 * @param patient
	 *            Obiekt reprezentujacy pacjenta
	 * @param room
	 *            identyfikator pomieszczenia, w ktorym pacjent ma zostac
	 *            umieszczony
	 * @return true - przyjecie pacjenta zostalo zrealizowane, false - dane
	 *         dotyczace przyjecia pacjenta sa bledne
	 *
	 */
	boolean patientAdmission(Patient patient, int room) throws RemoteException;

	/**
	 * Wypis pacjenta z oddzialu
	 *
	 * @param patient
	 *            Obiekt reprezentujacy zwalnianego do domu pacjenta
	 * @return true - operacja zrealizowana poprawnie, false - bledne dane nie
	 *         pozwalaja na wykonanie wypisu.
	 */
	boolean patientDischarge(Patient patient) throws RemoteException;

	/**
	 * Metoda zwraca zbior wszystkich pacjentow, ktorzy znajduja sie na oddzial
	 *
	 * @return zbior pacjentow znajdujacych sie na oddziale
	 */
	Set<Patient> getPatients() throws RemoteException;

	/**
	 * Metoda zwraca zbior wszystkich pacjentow, ktorzy znajduja sie na oddziale
	 * we wskazanym przez identyfikator pomieszczeniu
	 *
	 * @param room
	 *            identyfikator pomieszczenia, ktorego dotyczy zapytanie
	 * @return zbior pacjentow znajdujacych sie na oddziale
	 */
	Set<Patient> getPatients(int room) throws RemoteException;

	/**
	 * Metoda zwraca mape opisujaca aktualny stan zapelnienia wszystkich
	 * pomieszczen oddzialu
	 *
	 * @return mapa, ktorej kluczem jest identyfikator pomieszczenia, wartoscia
	 *         liczba zajetych w danym pomieszczeniu lozek
	 */
	Map<Integer, Integer> getRoomsState() throws RemoteException;

	/**
	 * Metoda zwraca liste reprezentujaca historie przyjec na oddzial. Uwga: ten
	 * sam pacjent moze na nia trafic kilkakrotnie (dlatego to jest lista, a nie
	 * zbior).
	 *
	 * @return lista kolejnych przyjec na oddzial
	 */
	List<Patient> getAdmissionHistory() throws RemoteException;
}
