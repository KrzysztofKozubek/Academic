import org.hamcrest.Matchers;
import org.junit.Before;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	private AbstractWard ward;
	private Map<Integer, Integer> state;
	private Map<Integer, Integer> limit;
	private Set<Integer> rooms; // identyfikatory pomieszczen
	private List<Patient> history;
	private Set<Patient> patients;
	private Map<Integer, Set<Patient>> patientsInRooms;
	private static boolean returnsNumberOfFreeBeds;
	private static boolean returnsNumberOfFreeBedsResultMakesSense;
	private static boolean returnsNumberOfFreeBedsTested;
	

	private void executeSetLimits(Map<Integer, Integer> max) {
		rooms = max.keySet();
		limit = new HashMap<>(max); // sporzadzamy kopie oryginalnych danych
		try {
			ward.setLimits(new HashMap<Integer, Integer>(max));
		} catch (Exception e) {

			PMO_SystemOutRedirect.println("BLAD: W trakcie setLimit pojawil sie wyjatek");
			TestHelper.showException(e);
		}
	}

	private void patientAdmission(Patient patient, int room, boolean expected) {
		TestHelper.tryExecute(() -> {
			Boolean result = null;
			try {
				result = ward.patientAdmission(patient, room);
			} catch (Exception e) {
				PMO_SystemOutRedirect.println("BLAD: W trakcie patientAdmission pojawil sie wyjatek Remote");
				TestHelper.showException(e);
			}
			return result;
		}, "patientAdmission", expected);
	}

	private void patientDischarge(Patient patient, boolean expected) {
		TestHelper.tryExecute(() -> {
			Boolean result = null;
			try {
				result = ward.patientDischarge(patient);
			} catch (Exception e) {
				PMO_SystemOutRedirect.println("BLAD: W trakcie patientAdmission pojawil sie wyjatek Remote");
				TestHelper.showException(e);
			}
			return result;
		}, "patientDischarge", expected);
	}

	private void getResults() {
		try {
			PMO_SystemOutRedirect.startRedirectionToNull();
			state = ward.getRoomsState();
			history = ward.getAdmissionHistory();
			patients = ward.getPatients();

			patientsInRooms.clear();
			Set<Patient> tmp;
			for (Integer roomID : rooms) {
				tmp = ward.getPatients(roomID);
				assertNotNull("Wynikiem getPatients dla istniejacego pomieszczenia nie moze byc null", tmp);
				patientsInRooms.put(roomID, tmp);
			}
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			TestHelper.showException(e);
			fail("W trakcie pracy metody getResult doszlo do wyjatku");
		} finally {
			PMO_SystemOutRedirect.returnToStandardStream();
		}

		assertNotNull("Wynikiem pracy getRoomsState nie moze byc null", state);
		assertNotNull("Wynikiem pracy getAdmissionHistory nie moze byc null", history);
		assertNotNull("Wynikiem pracy getPatients nie moze byc null", patients);
	}

	@Before
	public void createArrayParserObject() {
		ward = Narrow.find("WARD");
		patientsInRooms = new HashMap<>();

		if (!returnsNumberOfFreeBedsTested) {
			returnsNumberOfFreeBedsTested = true;
			testGetRooms();
		}
	}

	private void testGetRooms() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 2);
			}
		}); // pomieszczenia o id 11 -> 2 lozka

		try {
			// jesli pojawi sie "2" to getRooms odwraca logike pracy
			returnsNumberOfFreeBeds = ward.getRoomsState().get(11) == 2;
			returnsNumberOfFreeBedsResultMakesSense = true;
		} catch (Exception e) {
			returnsNumberOfFreeBedsResultMakesSense = false;
		}
	}

	private void testHistory(Patient... expected) {
		assertThat("Liczba pozycji historii powinna byc inna.", history, Matchers.hasSize(expected.length));

		for (int i = 0; i < expected.length; i++) {
			assertEquals("Pozycja " + i + " historii przyjec jest bledna", history.get(i).getID(), expected[i].getID());
		}
	}

	/**
	 * Konwertuje tablice pacjentow na tablice ich ID
	 * 
	 * @param patients
	 * @return
	 */
	private Long[] patient2ID(Patient[] patients) {
		List<Long> result = new ArrayList<>();
		TestHelper.convert2list(patients).forEach(p -> result.add(p.getID()));
		return result.toArray(new Long[patients.length]);
	}

	private Collection<Long> patientsCollection2ID(Collection<Patient> patients) {
		List<Long> result = new ArrayList<>();
		patients.forEach(p -> result.add(p.getID()));
		return result;
	}

	private void testState(Map<Integer, Patient[]> expected) {
		Set<Patient> all = new HashSet<>();
		for (Integer key : expected.keySet()) {
			all.addAll(Arrays.asList(expected.get(key))); // zbieramy wszystkich
															// pacjentow
			assertTrue("Oddzial zawiera pomieszczenie " + key + " powinien byc dla niego raport o pacjentach",
					patientsInRooms.containsKey(key));
			assertTrue("Oddzial zawiera pomieszczenie " + key + " powinien byc dla niego raport o liczbie lozek",
					state.containsKey(key));
			assertThat("Stan osobowy pomieszczenia " + key + " wg. ID pacjentow",
					patientsCollection2ID(patientsInRooms.get(key)),
					Matchers.containsInAnyOrder(patient2ID(expected.get(key))));

			if (returnsNumberOfFreeBeds) {
				assertThat("Stan lozek powinien uwzgledniach osoby przyjete " + key, state,
						Matchers.hasEntry(key, limit.get(key).intValue() - expected.get(key).length));
			} else {
				// dwa powyzsze testy daja to co ponizszy
				assertThat("Stan lozek powinien uwzgledniach osoby przyjete " + key, state,
						Matchers.hasEntry(key, expected.get(key).length));
			}
		}

		assertThat("Stan osobowy oddzialu", patientsCollection2ID(patients),
				Matchers.containsInAnyOrder(patientsCollection2ID(all).toArray(new Long[all.size()])));
	}

	Set<Patient> createSet(Patient... p) {
		return new HashSet<Patient>(Arrays.asList(p));
	}

	Patient[] createArray(Patient... p) {
		return p;
	}

	@org.junit.Test
	public void stateReturnCorrectResult() {
	        assertTrue("Nie udalo sie przeprowadzic testu sposobu dzialania getRoomsState", 
	                   returnsNumberOfFreeBedsResultMakesSense );
		assertFalse("getRoomsState powinna zwracac liczbe lozek zajetych", returnsNumberOfFreeBeds);
	}

	@org.junit.Test
	public void singleRoomTest() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 2);
			}
		}); // pomieszczenia o id 11 -> 2 lozka

		Patient p1 = new Patient();
		patientAdmission(p1, 11, true);
		getResults();

		testHistory(p1);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest2() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 2);
			}
		}); // pomieszczenia o id 11 -> 2 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, false);

		getResults();

		testHistory(p1, p2);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest3() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 3);
			}
		}); // pomieszczenia o id 11 -> 3 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, true);

		getResults();

		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2, p3));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest3B() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 3);
			}
		}); // pomieszczenia o id 11 -> 3 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		Patient p4 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, true);
		patientAdmission(p4, 11, false);
		getResults();

		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2, p3));
			}
		});

		patientDischarge(p2, true);
		getResults();

		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3));
			}
		});

		patientAdmission(p4, 11, true);
		getResults();

		testHistory(p1, p2, p3, p4);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p4));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest3C() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 3);
			}
		}); // pomieszczenia o id 11 -> 3 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		Patient p4 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, true);
		patientAdmission(p4, 11, false);

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2, p3));
			}
		});

		patientDischarge(p2, true);

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3));
			}
		});

		patientAdmission(p4, 11, true);

		getResults();
		testHistory(p1, p2, p3, p4);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p4));
			}
		});

		patientDischarge(p4, true);
		patientAdmission(p2, 11, true);

		getResults();
		testHistory(p1, p2, p3, p4, p2);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p2));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest3D() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 3);
			}
		}); // pomieszczenia o id 11 -> 3 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		Patient p4 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, true);
		patientAdmission(p4, 11, false);

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2, p3));
			}
		});

		patientDischarge(p2, true);

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3));
			}
		});

		patientAdmission(p4, 11, true);

		getResults();
		testHistory(p1, p2, p3, p4);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p4));
			}
		});
		// bledne dane do zwolnienia pacjenta
		patientDischarge(new Patient(), false);
		patientAdmission(p2, 11, false); // czyli p2 nie jest ponownie
											// przyjmowany

		getResults();
		testHistory(p1, p2, p3, p4);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p4));
			}
		});
	}

	@org.junit.Test
	public void singleRoomTest3E() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(11, 3);
			}
		}); // pomieszczenia o id 11 -> 3 lozka

		Patient p1 = new Patient();
		Patient p2 = new Patient();
		Patient p3 = new Patient();
		Patient p4 = new Patient();
		patientAdmission(p1, 11, true);
		patientAdmission(p2, 11, true);
		patientAdmission(p3, 11, true);
		patientAdmission(p4, 11, false);

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p2, p3));
			}
		});

		patientAdmission(p4, 11, false); // brak miejsca
		patientAdmission(p2, 11, false); // p2 juz jest
		patientAdmission(p4, 111, false); // brak takiego pomieszczenia

		getResults();
		testHistory(p1, p2, p3);
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(11, createArray(p1, p3, p2));
			}
		});

		// test danych dla zlego pomieszczenia
		try {
			assertNull("Brak pomieszczenia -> getPatients=null", ward.getPatients(123));
			assertNull("Brak pomieszczenia -> getPatients=null", ward.getPatients(111));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void multipleRoomsTest() {
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(102, 2);
				put(203, 3);
				put(304, 4);
			}
		});

		Patient[] ps = new Patient[10];

		for (int i = 0; i < 10; i++)
			ps[i] = new Patient();

		patientAdmission(ps[0], 102, true);
		patientAdmission(ps[1], 203, true);
		patientAdmission(ps[2], 304, true);
		patientAdmission(ps[3], 102, true);
		patientAdmission(ps[4], 203, true);
		patientAdmission(ps[5], 304, true);
		patientAdmission(ps[6], 102, false);
		patientAdmission(ps[6], 203, true);
		patientAdmission(ps[7], 203, false);
		patientAdmission(ps[7], 304, true);
		patientAdmission(ps[8], 304, true);
		patientAdmission(ps[9], 304, false);

		patientDischarge(ps[4], true);
		patientAdmission(ps[9], 203, true);

		patientDischarge(ps[0], true);
		patientDischarge(ps[3], true);

		try {
			Set<Patient> result = ward.getPatients(102);
			assertNotNull("Zgodnie z umowa pomieszczenie bez pacjentow zwraca pusty zbior", result);
			System.out.println("1");
			assertEquals("Zgodnie z umowa pomieszczenie bez pacjentow zwraca pusty zbior", 0, result.size());
			System.out.println("1");
		} catch (Exception e) {
			TestHelper.showException(e);
			System.out.println("1");
			fail("W trakcie getPatients dla pomieszczenia, ktore istnieje, ale nie ma w nim pacjentow wystapil wyjatek");
			System.out.println("1");
		}

		System.out.println("1");
		patientAdmission(ps[0], 102, true);
		patientAdmission(ps[3], 102, true);

		System.out.println("1");
		getResults();

		System.out.println("1");
		testHistory(ps[0], ps[1], ps[2], ps[3], ps[4], ps[5], ps[6], ps[7], ps[8], ps[9], ps[0], ps[3]);

		System.out.println("1");
		testState(new HashMap<Integer, Patient[]>() {
			{
				put(102, createArray(ps[0], ps[3]));
				put(203, createArray(ps[1], ps[6], ps[9])); // brak ps[4]
				put(304, createArray(ps[2], ps[5], ps[7], ps[8]));
			}
		});

		System.out.println("1");
		// test danych dla zlego pomieszczenia
		try {
			System.out.println("1");
			assertNull("Brak pomieszczenia -> getPatients=null", ward.getPatients(123));
			System.out.println("1");
			assertNull("Brak pomieszczenia -> getPatients=null", ward.getPatients(111));
			System.out.println("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@org.junit.Test ( timeout = 7500 )
	public void parallelAdmission() {
		final int limit = 11;
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(102, limit);
			}
		});

		AtomicInteger counter = new AtomicInteger();
		AtomicBoolean exceptionDetected = new AtomicBoolean(false);
		int THS = 50;
		CyclicBarrier cb = new CyclicBarrier(THS);
		Thread[] th = new Thread[THS];

		System.out.println("Zaczynamy attakowac");
		for (int i = 0; i < THS; i++) {
			th[i] = new Thread(new Runnable() {
				public void run() {
					Patient p = new Patient();
					try {
						cb.await();
						boolean result = ward.patientAdmission(p, 102);
						if (result)
							counter.incrementAndGet();
					} catch (Exception e) {
						TestHelper.showException(e);
						exceptionDetected.set(true);
					}
				};
			});
			th[i].setDaemon(true);
			th[i].start();
		}

		for (int i = 0; i < THS; i++) {
			try {
				th[i].join();
			} catch (InterruptedException e) {
				exceptionDetected.set(true);
			}
		}

		assertFalse("W trakcie testu nie powinno byc wyjatku", exceptionDetected.get());
		assertEquals("W trakcie testu powinno dac sie zarejestrowac tylko ograniczona liczbe pacjentow", limit,
				counter.get());
	}

	@org.junit.Test ( timeout = 7500 )
	public void parallelDischarge() {
		final int limit = 13;
		executeSetLimits(new HashMap<Integer, Integer>() {
			{
				put(102, limit);
			}
		});

		AtomicInteger counter = new AtomicInteger();
		AtomicBoolean exceptionDetected = new AtomicBoolean(false);
		int THS = 50;
		CyclicBarrier cb = new CyclicBarrier(THS);
		Thread[] th = new Thread[THS];

		for (int i = 0; i < THS; i++) {
			th[i] = new Thread(new Runnable() {
				public void run() {
					Patient p = new Patient();
					try {
						cb.await();
						boolean result = ward.patientAdmission(p, 102);
						if (result)
							counter.incrementAndGet();
						cb.await();
						boolean result2 = ward.patientDischarge(p);
						// jesli tego pacjenta przyjeto na oddzial, to powinno
						// dac sie go
						// zwolnic do domu
						if (result && result2) {
							counter.decrementAndGet();
						}
					} catch (Exception e) {
						TestHelper.showException(e);
						exceptionDetected.set(true);
					}
				};
			});
			th[i].setDaemon(true);
			th[i].start();
		}

		for (int i = 0; i < THS; i++) {
			try {
				th[i].join();
			} catch (InterruptedException e) {
				exceptionDetected.set(true);
			}
		}

		assertFalse("W trakcie testu nie powinno byc wyjatku", exceptionDetected.get());
		assertEquals("Koncowa liczba pacjentow po rejestracji/zwolnieniu do domu powinna wyniesc 0", 0, counter.get());
	}

}
