import java.util.ArrayList;
import java.util.List;

public class PMO_ThreadsHelper {
	
	public static List<Thread> RunnableToThread( List<? extends Runnable> workers ) {
		List<Thread> ths = new ArrayList<>();
		for ( Runnable worker : workers ) {
			ths.add( new Thread( worker ));
		}
		return ths;
	}
	
	public static void startThreads(List<Thread> threads) {
		for (Thread th : threads) {
			th.setDaemon(true);
			th.start();
		}
	}

	public static boolean joinThreads(List<Thread> threads) {
		for (Thread th : threads) {
			try {
				th.join();
			} catch (InterruptedException e) {
				PMO_SystemOutRedirect.println("W trakcie join doszlo do wyjatku");
				return false;
			}
		}
		return true;
	}

	public static boolean runTest(PMO_RunnableAndTestable run, long timeToFinish) {
		Thread th = new Thread(run);
		th.setDaemon(true);
		th.start();

		PMO_SystemOutRedirect.println("Maksymalny czas oczekiwania to " + (timeToFinish / 1000) + " sekund");

		try {
			th.join(timeToFinish);
		} catch (InterruptedException e) {
			PMO_SystemOutRedirect.println( "BLAD: W trakcie join doszlo do wyjatku");
		}
		PMO_SystemOutRedirect.println("Koniec sekcji join()");

		if (th.isAlive()) {
			PMO_SystemOutRedirect.println("BLAD: Test nie zostal ukonczony na czas");
			return false;
		} else {
			PMO_SystemOutRedirect.println("Uruchamiam test");
			return run.testIfOK();
		}

	}
	
	public static void shutdown() {
		System.out.println("HALT");
		Runtime.getRuntime().halt(0);
		System.out.println("EXIT");
		System.exit(0);
	}


}
