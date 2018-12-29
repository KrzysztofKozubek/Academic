import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PMO_Start {
	private static boolean runTest(PMO_RunnableAndTestable run, long timeToFinish) {
		Thread th = new Thread(run);
		th.setDaemon(true);
		th.start();

		PMO_SystemOutRedirect.println("Maksymalny czas oczekiwania to " + (timeToFinish / 1000) + " sekund");

		try {
			th.join(timeToFinish);
		} catch (InterruptedException e) {
		}

		PMO_SystemOutRedirect.println("Zakonczyl sie czas oczekiwania na join()");

		if (th.isAlive()) {
			PMO_SystemOutRedirect.println("BLAD: Test nie zostal ukonczony na czas");
			return false;
		} else {
			PMO_SystemOutRedirect.println("Uruchamiam test");
			return run.test();
		}

	}

	private static void joinThreads(List<Thread> ths) {
		ths.forEach((t) -> {
			try {
				t.join();
			} catch (InterruptedException ie) {
				PMO_SystemOutRedirect.println("Doszlo do wyjatku w trakcie join");
			}
		});
	}

	private static void shutdownIfFail(boolean testOK) {
		if (!testOK) {
			PMO_Verdict.show(false);
			shutdown();
		}
	}

	private static void shutdown() {
		System.out.println("HALT");
		Runtime.getRuntime().halt(0);
		System.out.println("EXIT");
		System.exit(0);
	}

	private static boolean executeTests(PMO_Testable... tests) {
		return executeTests(Arrays.asList(tests));
	}

	private static boolean executeTests(List<PMO_Testable> tests) {
		AtomicBoolean ab = new AtomicBoolean(true);

		// uruchamiamy wszystkie testy -> & zamiast &&
		tests.forEach(t -> ab.set(ab.get() & t.test()));

		return ab.get();
	}

	private static boolean executeShutdown(RAIDInterface2 raid, int maxWaitTime) {
		Thread th = new Thread(() -> raid.shutdown());
		th.setDaemon(true);
		th.start();

		try {
			th.join(maxWaitTime);
		} catch (InterruptedException e) {
			PMO_SystemOutRedirect.println("Pojawil sie wyjatek " + e);
			return false;
		}
		return !th.isAlive(); // watek powinien zostac zamkniety
	}

	/**
	 * Test ma sprawdzic czy sectorInUse zachowuje sie rozsadnie tj. pokazuje
	 * kiedy dane zostaly zapisane na dysku
	 * 
	 * @return true - poprawne zakonczenie testu
	 */
	private static boolean sectorInUseMakesSense() {

		PMO_SystemOutRedirect.showCurrentMethodName();
		PMO_RAIDParameters params = new PMO_RAIDParameters();

		params.disks = 5;
		params.sectorsPerDisk = 50;
		params.logicalSectors = 200;

		long opTime = 50, resolution = 10;

		PMO_DiskArray array = new PMO_DiskArray(params, 0);
		RAIDInterface2 raid = new RAID2();
		raid.addDisks(array.getArray());

		// tu generowana jest lista sektorow do zapisu - pochodza ze
		// wszystkich dyskow i nie uzywaja tych samych sektorow
		// idea: z dysku 0: 0,5,10,15,20 itd.
		// z dysku 1: 1,6,11,16,21 itd.
		// z dysku 2: 2,7,12,17,22 itd.
		List<Integer> sectors = new ArrayList<>();
		int s;
		for (int d = 0; d < params.disks - 1; d++) {
			for (int i = d; i < params.sectorsPerDisk; i += params.disks - 1) {
				s = d * params.sectorsPerDisk + i;
				sectors.add(s);
			}
		}

		boolean inUse = sectors.stream().anyMatch(se -> raid.sectorInUse(se));

		if (inUse) {
			PMO_SystemOutRedirect.println("BLAD: Brak zapisow, a raid zglasza, ze sektory sa w uzyciu");
			return false;
		}

		AtomicLong sumTimeWriteOrders = new AtomicLong(0);
		AtomicLong sumTimeWriteOperations = new AtomicLong(0);

		class sectorInUseTest implements PMO_RunnableAndTestable {

			long avrOrderTimeM = Long.MAX_VALUE;
			long avrWriteTimeM;
			long avrWriteMinLimit;
			long avrWriteMaxLimit;

			@Override
			public boolean test() {
				if (avrOrderTimeM > opTime) {
					PMO_SystemOutRedirect.println("BLAD: Czas zlecenia zapisu porownywalny z czasem operacji na dysku");
					return false;
				}
				if (avrWriteTimeM > avrWriteMaxLimit) {
					PMO_SystemOutRedirect
							.println("BLAD: Zmierzony czas zapisu jest dluzszy od czasu pracy sekwencyjnej");
					return false;
				}
				if (avrWriteTimeM < avrWriteMinLimit) {
					PMO_SystemOutRedirect
							.println("BLAD: Zmierzony czas zapisu jest krotszy od optymalnego czasu pracy");
					return false;
				}
				return true;
			}

			@Override
			public void run() {
				PMO_SystemOutRedirect.println("Poczatek zapisu");
				sectors.forEach(s -> raid.write(s, s % 10));
				do {
					PMO_TimeHelper.sleep(10);
				} while (sectors.stream().anyMatch(s -> raid.sectorInUse(s)));
				PMO_SystemOutRedirect.println("Koniec zapisu");
				int totalDiskOp = array.totalDiskOperations();

				// szukamy najwiekszej liczby operacji na pojedynczym dysku
				int maxOps = array.getDisk(0).getReads() + array.getDisk(0).getWrites();
				for (int i = 1; i < params.disks; i++) {
					int ops = array.getDisk(i).getReads() + array.getDisk(i).getWrites();
					if (maxOps < ops) {
						maxOps = ops;
					}
				}

				PMO_SystemOutRedirect.println("Total disk operations : " + totalDiskOp);
				PMO_SystemOutRedirect.println(array.toString());
				array.setSleepTime(opTime); // przestawiam czas wykonywania
											// operacji

				List<Thread> ths = new ArrayList<>(sectors.size());
				sectors.forEach(s -> ths.add(new Thread(() -> {
					sumTimeWriteOrders.addAndGet(PMO_TimeHelper.executionTime(() -> raid.write(s, s % 15)));
					sumTimeWriteOperations
							.addAndGet(PMO_TimeHelper.executionTime(() -> raid.sectorInUse(s), resolution));
				})));

				ths.forEach((t) -> t.start()); // uruchamiamy wszystkie watki

				joinThreads(ths);

				// wyniki pomiaru czasu zlecenia zapisu i jego wykonania
				avrOrderTimeM = sumTimeWriteOrders.get() / sectors.size();
				avrWriteTimeM = sumTimeWriteOperations.get() / sectors.size();
				PMO_SystemOutRedirect.println("Sredni czas zlecenia  write: " + avrOrderTimeM);

				PMO_SystemOutRedirect.println("Sredni czas wykonania write: " + avrWriteTimeM);

				int operationsPerWrite = totalDiskOp / sectors.size();
				long worstOneWriteTime = operationsPerWrite * opTime;
				avrWriteMaxLimit = ((1 + sectors.size()) * worstOneWriteTime) / 2;
				avrWriteMinLimit = (opTime * maxOps) / 2;

				PMO_SystemOutRedirect.println("Liczba operacji na dyskach na jeden zapis:       " + operationsPerWrite);
				PMO_SystemOutRedirect.println("Sredni czas zapisu wg. najgorszego scenariusza:  " + avrWriteMaxLimit);
				PMO_SystemOutRedirect.println("Sredni czas zapisu wg. najszybszego scenariusza: " + avrWriteMinLimit);
			}
		}

		boolean result = runTest(new sectorInUseTest(), sectors.size() * params.disks * opTime * 2);

		result &= executeTests(array.getDisksAsTests());

		return result;
	}

	/**
	 * Test ma sprawdzic czy kontroler dziala poprawnie
	 * 
	 * @return true - poprawne zakonczenie testu
	 */
	private static boolean raidTest() {

		PMO_SystemOutRedirect.showCurrentMethodName();
		PMO_RAIDParameters params = new PMO_RAIDParameters();

		params.disks = 5;
		params.sectorsPerDisk = 50;
		params.logicalSectors = 200;

		PMO_DiskArray array = new PMO_DiskArray(params, 0);
		RAIDInterface2 raid = new RAID2();
		raid.addDisks(array.getArray());

		// tu generowana jest lista sektorow do zapisu - pochodza ze
		// wszystkich dyskow i nie uzywaja tych samych sektorow
		// idea: z dysku 0: 0,5,10,15,20 itd.
		// z dysku 1: 1,6,11,16,21 itd.
		// z dysku 2: 2,7,12,17,22 itd.
		List<Integer> sectors = new ArrayList<>();
		int s;
		for (int d = 0; d < params.disks - 1; d++) {
			for (int i = d; i < params.sectorsPerDisk; i += params.disks - 1) {
				s = d * params.sectorsPerDisk + i;
				sectors.add(s);
			}
		}

		boolean inUse = sectors.stream().anyMatch(se -> raid.sectorInUse(se));

		if (inUse) {
			PMO_SystemOutRedirect.println("BLAD: Brak zapisow, a raid zglasza, ze sektory sa w uzyciu");
			return false;
		}

		class RAIDTest implements PMO_RunnableAndTestable {

			@Override
			public boolean test() {
				RAIDInterface2 ri2 = new RAID2(); // nowy obiekt
				DiskInterface2[] disks = array.getArray();

				disks[2] = null; // uszkodzenie
																		// dysku
																		// !
				ri2.addDisks(disks);

				// jesli dla jakiegokolwiek sektora zawartosc bedzie inna niz
				// zapisana to blad
				return sectors.stream().allMatch(s -> {
					int v = ri2.read(s) ;
					if ( v != (s % 17) ) {
						PMO_SystemOutRedirect.println( "W sektorze " + s + " jest inna wartosc niz zapisano" );
						PMO_SystemOutRedirect.println( "W sektorze jest " + v + " a powinno byc " + ( s % 17 ) );
						
						return false;
					} else {
						return true;
					}
				});
			}

			@Override
			public void run() {
				PMO_SystemOutRedirect.println("Poczatek zapisu");
				sectors.forEach(s -> raid.write(s, s % 17));
				do {
					PMO_TimeHelper.sleep(100);
				} while (sectors.stream().anyMatch(s -> raid.sectorInUse(s)));
				PMO_SystemOutRedirect.println("Koniec zapisu - sektory nie sygnalizuja sectorInUse");

				executeShutdown(raid, 15000); // czekamy na zakonczenie operacji
												// na dysku
			}
		}

		return runTest(new RAIDTest(), 35000);
	}

	/**
	 * Test sprawdza czy odczyty maja priorytet na zapisami. Generowana jest
	 * pewna liczba operacji zapisu/odczytu. Uruchamiamy wszystkie operacje
	 * jednoczesnie. Po zakonczeniu wszystkich odczytow oczekuje sie, ze nadal
	 * pewna liczba zapisow wciaz czeka na realizacje.
	 * 
	 * @return true - odczyty realizowane sa przed zapisami
	 */
	private static boolean prefereReadOp() {
		PMO_SystemOutRedirect.showCurrentMethodName();

		class Test implements PMO_RunnableAndTestable {
			PMO_RAIDParameters params = new PMO_RAIDParameters();
			AtomicBoolean result = new AtomicBoolean(false);

			@Override
			public void run() {
				params.disks = 5;
				params.sectorsPerDisk = 200;
				params.logicalSectors = 800;

				long opTime = 25;

				PMO_DiskArray array = new PMO_DiskArray(params, 0);
				array.setSleepTime(opTime);
				RAIDInterface2 raid = new RAID2();
				raid.addDisks(array.getArray());

				List<Integer> sectors = new ArrayList<>();
				List<Thread> ths = new ArrayList<>();

				class BarrierHolder {
					CyclicBarrier cb;
				}

				Random rnd = new Random();
				BarrierHolder startB = new BarrierHolder();
				BarrierHolder readEndB = new BarrierHolder();

				class ReadOp implements Runnable {
					int sector;

					public ReadOp(int sector) {
						this.sector = sector;
					}

					@Override
					public void run() {
						try {
							startB.cb.await();
							raid.read(sector);
							readEndB.cb.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}

					}
				}

				class WriteOp implements Runnable {
					int sector;

					public WriteOp(int sector) {
						this.sector = sector;
					}

					@Override
					public void run() {
						try {
							startB.cb.await();
							raid.write(sector, sector % 255);
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}

					}
				}

				int s;
				int readOps = 0;
				List<Integer> sector2Write = new ArrayList<>();
				// tu generowana jest lista sektorow do zapisu - pochodza ze
				// wszystkich dyskow i nie uzywaja tych samych sektorow
				// idea: z dysku 0: 0,5,10,15,20 itd.
				// ..... z dysku 1: 1,6,11,16,21 itd.
				// ......z dysku 2: 2,7,12,17,22 itd.
				for (int d = 0; d < params.disks - 1; d++) {
					for (int i = d; i < params.sectorsPerDisk; i += params.disks - 1) {
						s = d * params.sectorsPerDisk + i;
						sectors.add(s);
						if (rnd.nextBoolean()) {
							ths.add(new Thread(new WriteOp(s)));
							sector2Write.add(s);
						} else {
							ths.add(new Thread(new ReadOp(s)));
							readOps++;
						}
					}
				}

				startB.cb = new CyclicBarrier(sectors.size());
				readEndB.cb = new CyclicBarrier(readOps + 1);

				// to jest watek testu - ruszy do pracy gdy tylko zakoncza sie
				// odczyty
				ths.add(new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							PMO_SystemOutRedirect.println("Test oczekuje na start");
							readEndB.cb.await();
							long stillInUse = sector2Write.stream().filter((s) -> raid.sectorInUse(s)).count();
							PMO_SystemOutRedirect.println("Po zakonczeniu odczytow, dla " + stillInUse + " na "
									+ sector2Write.size() + " zapisywanych sektorow sygnalizuje stillInUse");
							if (stillInUse >= (sector2Write.size() / 4)) { // wystarczy
																			// 25%
																			// wszystkich
																			// do
																			// zapisu
								result.set(true);
							} else {
								PMO_SystemOutRedirect.println("BLAD: Nie wskazuje to na priorytetyzacje odczytow");
							}
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						} // oczekiwanie na zakonczenie odczytow
					}
				}));

				// start watkow
				ths.forEach(th -> th.start());

				PMO_SystemOutRedirect.println("Join trwalo : " + PMO_TimeHelper.executionTime(() -> joinThreads(ths)));
			}

			@Override
			public boolean test() {
				return result.get();
			}

		}

		return runTest(new Test(), 30000);

	}

	/**
	 * Test sprawdza czy odczyty maja priorytet na zapisami. Generowana jest
	 * pewna liczba operacji zapisu/odczytu. Uruchamiamy wszystkie operacje
	 * jednoczesnie. Po zakonczeniu wszystkich odczytow oczekuje sie, ze nadal
	 * pewna liczba zapisow wciaz czeka na realizacje.
	 * 
	 * @return true - odczyty realizowane sa przed zapisami
	 */
	private static boolean writeBuffer() {
		PMO_SystemOutRedirect.showCurrentMethodName();

		class Test implements PMO_RunnableAndTestable {
			PMO_RAIDParameters params = new PMO_RAIDParameters();
			AtomicBoolean result = new AtomicBoolean(true);
			AtomicInteger testCounter = new AtomicInteger(0);

			@Override
			public void run() {
				params.disks = 5;
				params.sectorsPerDisk = 200;
				params.logicalSectors = 800;

				long opTime = 25;

				PMO_DiskArray array = new PMO_DiskArray(params, 0);
				array.setSleepTime(opTime);
				RAIDInterface2 raid = new RAID2();
				raid.addDisks(array.getArray());

				List<Integer> sectors = new ArrayList<>();
				List<Thread> ths = new ArrayList<>();

				class BarrierHolder {
					CyclicBarrier cb;
				}

				Random rnd = new Random();
				BarrierHolder startB = new BarrierHolder();

				class WriteOp implements Runnable {
					int sector;
					int value2test;
					Random rnd = ThreadLocalRandom.current();

					public WriteOp(int sector) {
						this.sector = sector;
						value2test = 150 + rnd.nextInt(100);
					}

					@Override
					public void run() {
						try {
							startB.cb.await();
							raid.write(sector, sector % 125);

							while (rnd.nextBoolean()) {
								raid.write(sector, rnd.nextInt(125));
							}
							raid.write(sector, value2test);

							int read = raid.read(sector);
							if (read != value2test) {
								PMO_SystemOutRedirect.println("BLAD: odczytano cos innego niz przed chwila zapisano");
								PMO_SystemOutRedirect.println("BLAD: odczytano " + read + " a zapisano " + value2test);
								result.set(false);
							}
							testCounter.decrementAndGet();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}

					}
				}

				int s;
				// tu generowana jest lista sektorow do zapisu - pochodza ze
				// wszystkich dyskow i nie uzywaja tych samych sektorow
				// idea: z dysku 0: 0,5,10,15,20 itd.
				// ..... z dysku 1: 1,6,11,16,21 itd.
				// ......z dysku 2: 2,7,12,17,22 itd.
				for (int d = 0; d < params.disks - 1; d++) {
					for (int i = d; i < params.sectorsPerDisk; i += params.disks - 1) {
						s = d * params.sectorsPerDisk + i;
						sectors.add(s);
						ths.add(new Thread(new WriteOp(s)));
					}
				}
				testCounter.set(sectors.size()); // liczba testow == liczba
													// sektorow do zapisu
				startB.cb = new CyclicBarrier(sectors.size());

				// start watkow
				ths.forEach(th -> th.start());

				PMO_SystemOutRedirect.println("Join trwalo : " + PMO_TimeHelper.executionTime(() -> joinThreads(ths)));
			}

			@Override
			public boolean test() {
				if (testCounter.get() > 0) {
					PMO_SystemOutRedirect
							.println("BLAD: licznik testow nie dotarl do 0 - czesc testow nie zostala wykonana");
					PMO_SystemOutRedirect.println("BLAD: licznik testow rowny " + testCounter.get());
					return false;
				}
				return result.get();
			}

		}

		return runTest(new Test(), 30000);

	}

	public static void main(String[] args) {

		PMO_SystemOutRedirect.startRedirectionToNull();

		shutdownIfFail(PMO_Start.raidTest());
		shutdownIfFail(PMO_Start.sectorInUseMakesSense());

		PMO_Verdict.show(PMO_Start.prefereReadOp() && PMO_Start.writeBuffer());
		shutdown();
	}
}
