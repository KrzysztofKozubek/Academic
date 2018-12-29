import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

import org.omg.CORBA.IntHolder;

public class PMO_CorbaClient implements PMO_Testable, Runnable {

	private final long TIME_MEASURE_RESOLUTION = 10; // msec

	private final Generator gen;
	private final PMO_RMIGeneratorService rmiGen;
	private final CyclicBarrier barrier;
	private CyclicBarrier ordersBarrier;
	private int id; // id klienta
	private int orders; // liczba zlecen
	private Integer[] averagesOver; // liczba danych do wygenerowania
	private List<Thread> threads;
	private List<PMO_Testable> ordersTests;
	private int min, max; // minimalna - maksymalna liczba liczb do liczenia
							// sredniej
	private Set<Integer> globalIDs; // globalny zbior przyznanych uzytkownikom
									// ID, potem zadaniom
	private Set<Integer> localOrderIDs;
	private AtomicBoolean errorDetected = new AtomicBoolean(false);

	public PMO_CorbaClient(Generator gen, PMO_RMIGeneratorService rmiGen, CyclicBarrier barrier, int orders, int min,
			int max, Set<Integer> globalIDs) {
		this.rmiGen = rmiGen;
		this.barrier = barrier;
		this.gen = gen;
		this.max = max;
		this.min = min;
		this.orders = orders;
		this.globalIDs = globalIDs;
		localOrderIDs = Collections.synchronizedSet(new HashSet<>());
		PMO_SystemOutRedirect.println(
				"Utworzono klienta serwisu RMI " + rmiGen.getName() + ", ktory ma zlecic policzenie sredniej " + orders
						+ " razy. " + "\n Srednie maja byc liczone na podstawie od " + min + " do " + max + " liczb");
	}

	private void prepareOrders() {
		averagesOver = new Integer[orders];

		Random rnd = new Random();
		for (int i = 0; i < orders; i++) {
			if (max == min) {
				averagesOver[i] = min;
			} else {
				averagesOver[i] = rnd.nextInt(max - min) + min;
			}
			rmiGen.addExpectedNumberOfReads(averagesOver[i]);
		}
	}

	private class Order implements Runnable, PMO_Testable {
		int orderID;
		int avrOver;
		long orderExecTime; // czas wykonywania metody zlecenia
		float result;
		long orderWaitingTime; // czas oczekiwnia na zlecenie

		Order(int avrOver) {
			this.avrOver = avrOver;
		}

		@Override
		public void run() {
			IntHolder ih = new IntHolder();
			try {
				ordersBarrier.await(); // czekamy na inne watki

				// zlecenie z pomiarem czasu trwania
				orderExecTime = PMO_TimeHelper.executionTime(() -> gen.order(id, avrOver, ih));
				orderID = ih.value;

				// PMO_SystemOutRedirect
				// .println("Klient Corba o ID = " + id + " zglosil zlecenie,
				// ktore otrzymalo ID: " + orderID);

				// Test zablokowany z uwagi na mozliwosc ponownego uzycia ID dla
				// zakonczonego zlecenia
				// a w tescie brak globalnej synchronizacji zlecania/konczenia
				// prac
				// // test unikalnosci ID
				// if (!globalIDs.add(orderID)) {
				// fail("BLAD: Wykryto powtorzenie ID zlecenia - dalszy test nie
				// ma sensu");
				// if (!localOrderIDs.add(orderID)) {
				// fail("BLAD: Numer zlecenia dla klienta " + id + " lokalnie
				// powtarza sie");
				// }
				// return;
				// }

				orderWaitingTime = PMO_TimeHelper.executionTime(() -> !gen.isReady(orderID), TIME_MEASURE_RESOLUTION);

				// odebranie wyniku
				result = gen.getAverage(orderID);
			} catch (Exception e) {
				fail("BLAD: W trakcie obslugi zlecenia " + orderID + " dla klienta " + id + " pojawil sie wyjatek "
						+ e.getMessage());
				return;
			}

		}

		@Override
		public boolean testIfOK() {
			// poprawnosc wyniku
			if (Math.abs(result - rmiGen.expectedValue()) > 0.01) {
				fail("BLAD: Wynikiem zlecenia powinno byc " + rmiGen.expectedValue() + ", a jest " + result);
				return false;
			} else {
				PMO_SystemOutRedirect.println("OK: Srednia to " + result + ", oczekiwano " + rmiGen.expectedValue());
			}
			// czy zlecenia byly kolejkowane?
			if (orderExecTime > PMO_Start.GETVALUE_EXECUTION_TIME * 2) {
				fail("BLAD: Czas przyjmowania zlecenia jest za dlugi. \n      Limit to "
						+ (PMO_Start.GETVALUE_EXECUTION_TIME * 2) + "msec, a zmierzono " + orderExecTime + "msec");
				return false;
			} else {
				PMO_SystemOutRedirect
						.println("OK: Czas przyjmowania zlecenia od uzytkownika to " + orderExecTime + " msec");
			}

			// jesli bylo tylko jedno zlecenie
			if (orders == 1) {
				// to sprawdzamy ile czasu trwalo jego zrealizowanie
				try {
					// tyle powinno trwac jesli pierwsza liczba jest podzielna
					// przez druga
					long expected = 0;
					if (avrOver % rmiGen.getThreads() == 0) {
						expected = (avrOver / rmiGen.getThreads()) * PMO_Start.GETVALUE_EXECUTION_TIME;
					} else { // tyle jesli niepodzielna
						expected = (avrOver / rmiGen.getThreads() + 1) * PMO_Start.GETVALUE_EXECUTION_TIME;
					}
					// ale czy tak bylo z dokladnoscia do czasu liczenia dwoch
					// liczb ?
					if (expected + 2 * PMO_Start.GETVALUE_EXECUTION_TIME < orderWaitingTime) {
						fail("BLAD: Oczekiwano, ze serwis RMI bedzie uzywany efektywnie. \n      Zlecenie powinno byc zrealizowane w "
								+ expected + " jest " + orderWaitingTime);
						return false;
					} else {
						PMO_SystemOutRedirect.println("OK: Oczekiwany czas wykonania zadania " + expected
								+ ", a zmierzony: " + orderWaitingTime);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					return false;
				}
			}

			return true;
		}
	}

	private void prepareOrderThreads() {
		threads = new ArrayList<>(orders);

		ordersBarrier = new CyclicBarrier(orders);
		ordersTests = new ArrayList<>(orders);

		Order o;
		for (Integer avgo : averagesOver) {
			o = new Order(avgo);
			threads.add(new Thread(o));
			ordersTests.add(o);
		}
	}

	private void bwait() {
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			PMO_SystemOutRedirect.println("BLAD: W trakcie await pojawil sie wyjatek " + e.getMessage());
			errorDetected.set(true);
		}
	}

	private void fail() {
		errorDetected.set(true);
	}

	private void fail(String description) {
		PMO_SystemOutRedirect.println(description);
		fail();
	}

	@Override
	public void run() {
		IntHolder ih = new IntHolder();

		// rejestracja uzytkownika serwisu CORBA
		try {
			bwait(); // synchronizacja przed rejestracja
			gen.register(rmiGen.getName(), ih);
		} catch (Exception e) {
			fail("W trakcie rejestracji uzytkownika pojawil sie wyjatek " + e.getMessage());
			return;
		}
		id = ih.value;
		// PMO_SystemOutRedirect.println("Klient Corba otrzymal ID " + id);

		// test unikalnosci ID
		if (!globalIDs.add(id)) {
			fail("BLAD: Wykryto powtorzenie ID uzytkownika - dalszy test nie ma sensu");
			globalIDs.remove(id);
			return;
		}
		bwait();
		globalIDs.remove(id);

		prepareOrders();
		prepareOrderThreads();
		bwait();
		PMO_ThreadsHelper.startThreads(threads);
		PMO_ThreadsHelper.joinThreads(threads);
	}

	@Override
	public boolean testIfOK() {
		boolean result = !errorDetected.get();
		if (!result) {
			PMO_SystemOutRedirect
					.println("Flaga errorDetected wskazuje na wykrycie bledu w trakcie pracy programu klienta Corba");
		}
		result &= PMO_Testable.allOK(ordersTests);
		return result;
	}
}
