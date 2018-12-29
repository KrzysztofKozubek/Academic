import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class PMO_Start {
	private static boolean runTest(PMO_RunnableAndTestable run, long timeToFinish) {
		Thread th = new Thread(run);
		th.setDaemon(true);
		th.start();

		PMO_SystemOutRedirect.println( "Maksymalny czas oczekiwania to " + ( timeToFinish / 1000 ) + " sekund");
		
		try {
			th.join(timeToFinish);
		} catch (InterruptedException e) {
		}

		PMO_SystemOutRedirect.println( "Zakonczyl sie czas oczekiwania na join()");

		if (th.isAlive()) {
			PMO_SystemOutRedirect.println("BLAD: Test nie zostal ukonczony na czas");
			return false;
		} else {
			PMO_SystemOutRedirect.println("Uruchamiam test");			
			return run.test();
		}

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

	private static boolean executeTests(List<PMO_Testable> tests) {
		AtomicBoolean ab = new AtomicBoolean(true);

		// uruchamiamy wszystkie testy -> & zamiast &&
		tests.forEach(t -> ab.set(ab.get() & t.test()));

		return ab.get();
	}
	
	private static boolean executeShutdown( RAIDInterface raid, int maxWaitTime ) {
		Thread th = new Thread( () -> raid.shutdown() );
		th.setDaemon( true );
		th.start();
	
		try {
			th.join( maxWaitTime );
		} catch ( InterruptedException e ) {
			PMO_SystemOutRedirect.println( "Pojawil sie wyjatek " + e );
			return false;
		}
		return !th.isAlive(); // watek powinien zostac zamkniety
	}

	/**
	 * Metoda dokonuje sekwencyjnego testu poprawnosci zapisu.
	 * Po uszkodzeniu jednego z dyskow nastepuje ponowny odczyt.
	 * @return
	 */
	private static boolean basicRAIDTest() {
		PMO_SystemOutRedirect.showCurrentMethodName();
		
		PMO_SystemOutRedirect.println( "Test sprawdzajacy czy RAID dziala");
		
		PMO_RAIDParameters params = new PMO_RAIDParameters();
		params.disks = 10;
		params.sectorsPerDisk = 100;
		params.logicalSectors = 900;

		PMO_DiskArray array = new PMO_DiskArray(params, 1);
		RAIDInterface raid = new RAID();
		raid.addDisks(array.getArray());

		// zapis danych na dysk
		int shift = 37;
		for (int i = 0; i < params.logicalSectors; i++) {
			raid.write(i, (i + shift) % 255);
			raid.read( i );
		}
		
		PMO_SystemOutRedirect.println( array.toString() );
		
		executeShutdown( raid, 1000 );
		
		int writes = array.totalWriteOperations();

		if (writes != params.logicalSectors * 2) {
			PMO_SystemOutRedirect.println(
					"UWAGA: zapisano " + params.logicalSectors + " sektorow, a wykonano " + writes + " zapisow");
		}

		raid = new RAID(); // nowy obiekt do testu
		array.clearCounters();
		raid.addDisks(array.getArray());

		// uszkodzimy jeden z dyskow
		array.damageDisk(5);
		int expected, value;

		// test odzyskiwania danych
		for (int i = 0; i < params.logicalSectors; i++) {
			expected = (i + shift) % 255;
			value = raid.read(i);
			if (expected != value) {
				PMO_SystemOutRedirect
						.println("BLAD: odczyt z macierzy jest bledny, powinno byc " + expected + " a jest " + value);
				return false;
			}
		}

		int reads = array.totalReadOperations();
		writes = array.totalWriteOperations();
		if (writes != 0) {
			PMO_SystemOutRedirect.println("UWAGA: nie zapisano niczego, a wykonano " + writes + " zapisow");
		}

		// extra 1, bo kontroler musi sie dowiedziec o bledzie
		if (reads != (params.logicalSectors + 1 + params.sectorsPerDisk * (params.disks - 2))) {
			PMO_SystemOutRedirect.println(
					"UWAGA: Odczytano " + params.logicalSectors + " sektorow, a wykonano " + reads + " odczytow");
			array.showReadOperations();
		}
		executeShutdown( raid, 1000 );
		PMO_SystemOutRedirect.println( array.toString() );

		return true;
	}
/**
 * Metoda dokonuje pomiaru czasu realizacji zapisow i odczytow z macierzy.
 * Macierz nie ulega uszkodzeniu.
 * @return
 */
	private static boolean rwTimeTest() {
		PMO_SystemOutRedirect.showCurrentMethodName();
		PMO_RAIDParameters params = new PMO_RAIDParameters();
		List<PMO_Testable> tests = new ArrayList<PMO_Testable>();

		params.disks = 11;
		params.sectorsPerDisk = 20;
		params.logicalSectors = 200;
		
		PMO_DiskArray array = new PMO_DiskArray(params, 2);
		int sleepTime = 50;
		array.setSleepTime( sleepTime );
		tests.addAll(Arrays.asList(array.getDisksAsTests()));
		tests.add(array);
		
		//////////////////////////////////////////////////////////////////////
		PMO_SystemOutRedirect.println("****   Test z pomiarem czasu");
		RAIDInterface raid = new RAID();
		raid.addDisks(array.getArray());
		
		PMO_SystemOutRedirect.returnToStandardStream();
		
		long time = PMO_TimeHelper.executionTime( new Runnable() {
			@Override
			public void run() {
				PMO_WriteReadTest rw = new PMO_WriteReadTest(params, raid, 133, params.disks-1, true, true );
				// tu sa zapisy + odczyty
				runTest( rw, sleepTime * params.logicalSectors * ( params.disks + 1 ) );
				for ( int i = 0; i < params.disks; i++ ) {
					// tylko odczyty
					PMO_WriteReadTest ro = new PMO_WriteReadTest(params, raid, 133, params.disks-1, false, false );
					runTest( ro, sleepTime * params.logicalSectors );					
				}
			}
		});

		// na razie tylko odczyty
		int requiredOperations = params.logicalSectors * params.disks;

		requiredOperations += params.logicalSectors * 2 ; // doliczamy zapisy
		if ( array.getReadsFromSumDisk() == params.logicalSectors ) {
			// zakladamy, ze odczyty byly potrzebne do wyliczenia zmiany 
			// sektora z suma kontolna
			requiredOperations += params.logicalSectors; // liczba dodatkowych odczytow
		} else {
			// suma kontolna wyliczana przez odczyty ze zwyklych dyskow
			requiredOperations += params.logicalSectors * ( params.disks -2 ); // -2, bo dysk modyfikowany i dysk z suma			
		}
		
		long seqTime = sleepTime * requiredOperations;
		float speedup = (float)seqTime/time;
		float limit = params.disks / 3.f;
		int operationsDone = array.totalDiskOperations();
		long seqOpTime = operationsDone * sleepTime;
		
		PMO_SystemOutRedirect.println( "* Liczba sektorow dysku logicznego              : " + params.logicalSectors );
		PMO_SystemOutRedirect.println( "* Wymagana minimalna liczba operacji na dyskach : " + requiredOperations );
		PMO_SystemOutRedirect.println( "* Zrealizowana liczba operacji na dyskach       : " + operationsDone );
		PMO_SystemOutRedirect.println( "* Szacowany czas wykonania zadania sekwencyjnie : " + seqTime );
		PMO_SystemOutRedirect.println( "* Zmierzony czas wykonania zadania rownolegle   : " + time );
		PMO_SystemOutRedirect.println( "* Przyspieszenie dzieki pracy rownoleglej       : " + speedup );
		PMO_SystemOutRedirect.println( "* Limit przyspieszenia                          : " + limit );
		PMO_SystemOutRedirect.println( "* Maksymalna liczba jednoczesnie uzytych dyskow : " + array.getMaxParallelDiskUsage());
		PMO_SystemOutRedirect.println( "* Czas trwania wykonanych operacji sekwencyjnie : " + seqOpTime );
		PMO_SystemOutRedirect.println( "* Przyspieszenie wg. wykonanych operacji        : " + (float)seqOpTime / time );
		
		PMO_SystemOutRedirect.println( array.toString() );
		
		if ( seqOpTime < seqTime ) {
			PMO_SystemOutRedirect.println( "UWAGA: Program wykonal mniej operacji niz musial w celu realizacji zadania");
			PMO_SystemOutRedirect.println( "UWAGA: Jak to jest mozliwe?????");
			PMO_Verdict.show( false );
			return false;
		}
		
		boolean result = speedup >= limit;
		
		PMO_Verdict.show( result );
		
		return result;
	}
	
	/**
	 * Metoda dokonuje rownoleglego testu zapisu/odczytu
	 * @return
	 */
	private static boolean rwTest() {
		PMO_SystemOutRedirect.showCurrentMethodName();
		PMO_RAIDParameters params = new PMO_RAIDParameters();
		List<PMO_Testable> tests = new ArrayList<PMO_Testable>();

		params.disks = 5;
		params.sectorsPerDisk = 50;
		params.logicalSectors = 200;

		PMO_DiskArray array = new PMO_DiskArray(params, 2);
		tests.addAll(Arrays.asList(array.getDisksAsTests()));
		tests.add(array);

		//////////////////////////////////////////////////////////////////////
		PMO_SystemOutRedirect.println("Test sekwencyjny zapis+odczyt");
		RAIDInterface raid = new RAID();
		raid.addDisks(array.getArray());

		PMO_WriteReadTest sw = new PMO_WriteReadTest(params, raid, 13, 1, true, true );

		// tu decyduje tylko to, czy odczyty daly poprawna wartosc
		if (!runTest(sw, 5000))
			return false; // nie kontynuujemy, skoro juz wystapil blad

		// z tego obiektu raid juz nie bedziemy korzystac
		executeShutdown( raid, 1000 );
		PMO_SystemOutRedirect.println( array.toString() );

		// liczba operacji na dyskach - dane do porownania z wersja rownolegla
		int readsS = array.totalReadOperations();
		int writesS = array.totalWriteOperations();

		//////////////////////////////////////////////////////////////////
		PMO_SystemOutRedirect.println("Test rownolegly zapis+odczyt");
		raid = new RAID(); // nowy obiekt - ta sama macierz
		PMO_WriteReadTest pw = new PMO_WriteReadTest(params, raid, 23, params.disks - 1, true, true );
		array.clearCounters();
		raid.addDisks(array.getArray());

		long sleepTime = 10;
		array.setSleepTime(sleepTime); // spowalniamy operacje na dyskach
		
		boolean result = runTest(pw, 2 * params.disks * params.logicalSectors * sleepTime);
		executeShutdown( raid, 1000 );
		PMO_SystemOutRedirect.println( array.toString() );
		result &= executeTests(tests);

		int readsP = array.totalReadOperations();
		int writesP = array.totalWriteOperations();
		if (readsP != readsS) {
			result = false;
			PMO_SystemOutRedirect
					.println("BLAD: Liczba operacji odczytu w wersji sekwencyjnej jest inna niz w wspolbieznej");
			PMO_SystemOutRedirect.println("    : Sekwencyjnie bylo " + readsS + " wspolbieznie jest " + readsP);
		}
		if (writesP != writesS) {
			result = false;
			PMO_SystemOutRedirect
					.println("BLAD: Liczba operacji zapisu w wersji sekwencyjnej jest inna niz w wspolbieznej");
			PMO_SystemOutRedirect.println("    : Sekwencyjnie bylo " + writesS + " wspolbieznie jest " + writesP);
		}

		if (!result)
			return false;

		PMO_SystemOutRedirect.println("Test rownolegly tylko odczyt");
		raid = new RAID(); // nowy obiekt - ta sama macierz
		PMO_WriteReadTest ro = new PMO_WriteReadTest(params, raid, 23, params.disks - 1, false, false );
		array.clearCounters();
		raid.addDisks(array.getArray());
		array.setParallelUsageLimit(params.disks - 1);

		// (disk)*sectors operacji
		result &= runTest(ro, 2 * (params.disks - 1) * params.logicalSectors * sleepTime);
		result &= executeTests(tests);

		executeShutdown( raid, 1000 );
		PMO_SystemOutRedirect.println( array.toString() );
		return result;
	}
	
	/**
	 * Metoda sprawdza czy shutdown zatrzymuje operacje na dyskach
	 * @return
	 */
	private static boolean shutdownTest() {
		PMO_SystemOutRedirect.showCurrentMethodName();
		
		PMO_SystemOutRedirect.println( "Test sprawdzajacy czy shutdown blokuje operacje na dysku");
		
		PMO_RAIDParameters params = new PMO_RAIDParameters();
		params.disks = 6;
		params.sectorsPerDisk = 20;
		params.logicalSectors = 100;
		PMO_DiskArray array = new PMO_DiskArray(params, 1);
		array.setSleepTime( 10 ); // spowalniamy operacje na dysku
		RAIDInterface raid = new RAID();
		raid.addDisks(array.getArray());
		
		class ParallelShutdownTest implements PMO_RunnableAndTestable {
			// bariera synchronizuje prace watkow
			private CyclicBarrier cb = new CyclicBarrier( params.logicalSectors );
			private List<Thread> threads = new ArrayList<>( params.logicalSectors );
			private AtomicBoolean result = new AtomicBoolean( false );
			@Override
			public void run() {
				// tworzymy tyle watkow ile jest sektorow
				for ( int i = 1; i < params.logicalSectors; i++ ) {
					Integer ii = new Integer(i);
					threads.add( new Thread( new Runnable() {
						public void run() {
							try {
								cb.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								e.printStackTrace();
							}
							raid.write( ii , ii % 255 );
							try {
								cb.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								e.printStackTrace();
							}
							raid.write( ii , ii % 255 );
						}
					} ) );
				}
				
				// ten watek zleci shutdown
				threads.add( new Thread( new Runnable() {
					@Override
					public void run() {
						PMO_SystemOutRedirect.println( "Przygotowanie do shutdown");
						try {
							cb.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}
						raid.shutdown();
						array.setShutdownFlag(); // wlaczamy test po shutdown
						result.set( true ); // wykonano shutdown, wiec test bedzie miec jakis sens
						PMO_SystemOutRedirect.println( "Wykonano shutdown i ustawiono flagi");
						try {
							cb.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}
						raid.write( 0 , 0 );						
					}
				}));
				
				threads.forEach( th -> th.start() );
				threads.forEach( th -> {
					try {
						th.join();
					} catch ( Exception e ) {
						PMO_SystemOutRedirect.println( "W trakcie join doszlo do wyjatku " + e );
					}
				} );
			}
			
			@Override
			public boolean test() {
				if ( ! result.get() ) {
					PMO_SystemOutRedirect.println( "BLAD: nie doczekano sie na zakonczenie shutdown()");
					return false;
				}
				int operationsAfterShutdown = array.totalOperationsAfterShutdown();
				if ( operationsAfterShutdown > 0 ) {
					if ( operationsAfterShutdown >= params.logicalSectors ) {
						PMO_SystemOutRedirect.println( "BLAD: shutdown pozwala na operacje na dysku po jego wykonaniu");
					} else {
						PMO_SystemOutRedirect.println( "UWAGA: shutdown nie uwzglednia operacji juz zaczetych");						
					}
					return false;
				} else {
					PMO_SystemOutRedirect.println( "OK: shutdown spowodowal, ze zliczono " + operationsAfterShutdown + " operacji po jego zakonczeniu");
					return true;
				}
			}
			
		}
		
		boolean result = runTest( new ParallelShutdownTest(), 2500 );
		System.out.println(result);
		PMO_SystemOutRedirect.println( array.toString() );
		return result;
	}

	private static boolean diskErrorTest() {
		PMO_SystemOutRedirect.showCurrentMethodName();
		
		PMO_SystemOutRedirect.println( "Test sprawdzajacy czy blad blokuje operacje na dysku");
		
		PMO_RAIDParameters params = new PMO_RAIDParameters();
		params.disks = 6;
		params.sectorsPerDisk = 20;
		params.logicalSectors = 100;
		PMO_DiskArray array = new PMO_DiskArray(params, 1);
		array.setSleepTime( 10 ); // spowalniamy operacje na dysku
		RAIDInterface raid = new RAID();
		raid.addDisks(array.getArray());
		
		class ParallelShutdownTest implements PMO_RunnableAndTestable {
			// bariera synchronizuje prace watkow
			private CyclicBarrier cb = new CyclicBarrier( params.logicalSectors );
			private List<Thread> threads = new ArrayList<>( params.logicalSectors );
			private AtomicBoolean result = new AtomicBoolean( false );
			@Override
			public void run() {
				// tworzymy tyle watkow ile jest sektorow
				for ( int i = 1; i < params.logicalSectors; i++ ) {
					Integer ii = new Integer(i);
					threads.add( new Thread( new Runnable() {
						public void run() {
							try {
								cb.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								e.printStackTrace();
							}
							raid.read( ii );
							try {
								cb.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								e.printStackTrace();
							}
							raid.read( ii );
						}
					} ) );
				}
				
				// ten watek zleci shutdown
				threads.add( new Thread( new Runnable() {
					@Override
					public void run() {
						PMO_SystemOutRedirect.println( "Przygotowanie do awarii dysku");
						try {
							cb.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}
						array.damageDisk(1); // uszkadzamy dysk
						result.set( true ); // zrealizowano uszkodzenie, wiec test bedzie miec jakis sens
						PMO_SystemOutRedirect.println( "Awaria dysku i ustawiono flagi");
						try {
							cb.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}
						raid.read( 0 );						
					}
				}));
				
				threads.forEach( th -> th.start() );
				threads.forEach( th -> {
					try {
						th.join();
					} catch ( Exception e ) {
						PMO_SystemOutRedirect.println( "W trakcie join doszlo do wyjatku " + e );
					}
				} );
			}
			
			@Override
			public boolean test() {
				if ( ! result.get() ) {
					PMO_SystemOutRedirect.println( "BLAD: nie doczekano sie na zakonczenie shutdown()");
					return false;
				}
				int operationsAfterError = array.totalOperationsAfterError();
				if ( operationsAfterError > 1 ) {
					if ( operationsAfterError > params.logicalSectors ) {
						PMO_SystemOutRedirect.println( "BLAD: pomimo uzyskania informacji o bledzie nadal sa odczyty");
					} else {
						PMO_SystemOutRedirect.println( "UWAGA: obsluga bledu dysku nie uwzglednia operacji juz zaczetych");						
					}
					return false;
				} else {
					PMO_SystemOutRedirect.println( "OK: zliczono " + operationsAfterError + " operacji odczytu po bledzie dysku");
					return true;
				}
			}
			
		}
		
		boolean result = runTest( new ParallelShutdownTest(), 5000 );
		PMO_SystemOutRedirect.println( array.toString() );
		return result;
	}
	
	public static void main(String[] args) {
		int repeates = 3;
		
		PMO_SystemOutRedirect.startRedirectionToNull();
		shutdownIfFail(basicRAIDTest());
		
		boolean timeTestResult = rwTimeTest();
		PMO_Verdict.show( timeTestResult ); // blad zostanie wyswietlony, ale nie zablokuje innych testow 
		
		for ( int i = 0; i < repeates; i++ ) {
			PMO_SystemOutRedirect.startRedirectionToNull();
			shutdownIfFail(rwTest());
		}

		boolean shutdownResult = true;
		for ( int i = 0; i < repeates; i++ ) {
			PMO_SystemOutRedirect.startRedirectionToNull();
			shutdownResult &= shutdownTest();
			if ( ! shutdownResult ) break;
		}
		PMO_Verdict.show( shutdownResult );
		boolean errorResult = true;
		for ( int i = 0; i < repeates; i++ ) {
			PMO_SystemOutRedirect.startRedirectionToNull();
			errorResult &= diskErrorTest();
			if ( ! errorResult ) break;
		}

		PMO_Verdict.show( timeTestResult );
		PMO_Verdict.show( shutdownResult );
		PMO_Verdict.show( errorResult );
		shutdown();
	}
}
