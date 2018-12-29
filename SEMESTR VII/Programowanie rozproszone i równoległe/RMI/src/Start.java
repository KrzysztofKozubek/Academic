import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by kris on 26.12.16.
 */

public class Start {

	private static void register() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AbstractWard s = (AbstractWard) UnicastRemoteObject.exportObject(new Ward(), 0);
            registry.rebind("WARD", s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {
        register();
    }
}

class Ward implements AbstractWard {

    /* Nr room, nr beds in room */
    Map<Integer, Integer> ward = new HashMap<>();
    /* zbior idPacjent, nrPokoju */
    Map<Patient, Integer> wherePatientLie = new HashMap<>();

    /* Aktualne zaludnienie osrodka */
    Map<Integer, Integer> actualStanOfWard = new HashMap<>();
    /* Historia przyjmowania pacjentow */
    List<Patient> historyWard = new ArrayList<>();

    public Ward(){super();}
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
    @Override
    public void setLimits(Map<Integer, Integer> max) throws RemoteException {
        synchronized (this){

            ward.clear();
            wherePatientLie.clear();
            actualStanOfWard.clear();
            historyWard.clear();

            for(Map.Entry<Integer, Integer> var : max.entrySet()){
                ward.put(var.getKey(), var.getValue());
                actualStanOfWard.put(var.getKey(), 0);
            }
        }
    }
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
    @Override
    public boolean patientAdmission(Patient patient, int room) throws RemoteException {
        synchronized (this){

            /* Pacjent juz lezy u nas */
            for(Map.Entry<Patient, Integer> var : wherePatientLie.entrySet())
                if(var.getKey().getID() == patient.getID()) return false;

            /* Nie ma takiego pokoju */
            if(ward.get(room) == null || actualStanOfWard.get(room) == null) return false;
            /* Brak miejsca w tym pokoju */
            if(ward.get(room) <= actualStanOfWard.get(room)) return false;

            wherePatientLie.put(patient, room);
            actualStanOfWard.put(room, actualStanOfWard.get(room) + 1);
            historyWard.add(patient);

            return true;
        }
    }
    /**
     * Wypis pacjenta z oddzialu
     *
     * @param patient
     *            Obiekt reprezentujacy zwalnianego do domu pacjenta
     * @return true - operacja zrealizowana poprawnie, false - bledne dane nie
     *         pozwalaja na wykonanie wypisu.
     */
    @Override
    public boolean patientDischarge(Patient patient) throws RemoteException {
        synchronized (this){

            int room = -1;
            /* Pacjent nie lezy u nas */
            for(Map.Entry<Patient, Integer> var : wherePatientLie.entrySet())
                if(var.getKey().getID() == patient.getID()) room = var.getValue();
            if(room == -1) return false;

            actualStanOfWard.put(room, actualStanOfWard.get(room) - 1);
            Iterator it = wherePatientLie.entrySet().iterator();

            while(it.hasNext()){
                Map.Entry var = (Map.Entry) it.next();
                if(((Patient) var.getKey()).getID() == patient.getID()){ it.remove(); return true; }
            }
            System.out.println("NIE UDALO ZNALEXC SIE PACJENTA NA SALACH KASIK SIE SCHOWAL");
            /* Mimo ze jest nie udalo sie go odnalexc gdzie lezy dziad */
            return false;
        }
    }
    /**
     * Metoda zwraca zbior wszystkich pacjentow, ktorzy znajduja sie na oddzial
     *
     * @return zbior pacjentow znajdujacych sie na oddziale
     */
    @Override
    public Set<Patient> getPatients() throws RemoteException {
        synchronized (this){
            Set<Patient> patients = new HashSet<>();

            for(Map.Entry<Patient, Integer> var : wherePatientLie.entrySet())
                patients.add(var.getKey());

            return patients;
        }
    }
    /**
     * Metoda zwraca zbior wszystkich pacjentow, ktorzy znajduja sie na oddziale
     * we wskazanym przez identyfikator pomieszczeniu
     *
     * @param room
     *            identyfikator pomieszczenia, ktorego dotyczy zapytanie
     * @return zbior pacjentow znajdujacych sie na oddziale
     */
    @Override
    public Set<Patient> getPatients(int room) throws RemoteException {
        synchronized (this){
            if(ward.get(room) == null) return null;
            Set<Patient> patients = new HashSet<>();

            for(Map.Entry<Patient, Integer> var : wherePatientLie.entrySet())
                if(var.getValue() == room) patients.add(var.getKey());

            return patients;
        }
    }
    /**
     * Metoda zwraca mape opisujaca aktualny stan zapelnienia wszystkich
     * pomieszczen oddzialu
     *
     * @return mapa, ktorej kluczem jest identyfikator pomieszczenia, wartoscia
     *         liczba zajetych w danym pomieszczeniu lozek
     */
    @Override
    public Map<Integer, Integer> getRoomsState() throws RemoteException {
        synchronized (this){
            return actualStanOfWard;
        }
    }
    /**
     * Metoda zwraca liste reprezentujaca historie przyjec na oddzial. Uwga: ten
     * sam pacjent moze na nia trafic kilkakrotnie (dlatego to jest lista, a nie
     * zbior).
     *
     * @return lista kolejnych przyjec na oddzial
     */
    @Override
    public List<Patient> getAdmissionHistory() throws RemoteException {
        synchronized (this){
            return historyWard;
        }
    }
}