import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Klasa sekwencyjnego zapisu/odczytu
 *
 */
public class PMO_WriteReadTest implements PMO_RunnableAndTestable {

	protected List<PMO_Worker> workers = new ArrayList<>();
	protected List<Thread> threads = new ArrayList<>();
	protected AtomicBoolean joinCompleted = new AtomicBoolean(false);
	protected AtomicBoolean joinException = new AtomicBoolean(false);

	public PMO_WriteReadTest(PMO_RAIDParameters params, RAIDInterface2 ri, int shift, int threads,
			boolean writeAllowed, boolean addSectorsFromOtherDisks ) {
		int leases;
		
		List<Integer> sectors;
		Iterator<Integer> iterator;
		
		if ( addSectorsFromOtherDisks ) {
			sectors = PMO_SectorsGenerator.generate(params);
		} else {
			sectors = PMO_SectorsGenerator.generateAll(params);
		}
		leases = sectors.size() / threads;			
		iterator = sectors.iterator();
		int remaining = sectors.size();
		
//		PMO_SystemOutRedirect.println( "Leases per thread : " + leases );
			
		for (int i = 0; i < threads - 1; i++) {
			workers.add(new PMO_Worker( iterator, leases, params, ri, shift,
					writeAllowed, addSectorsFromOtherDisks));
			remaining -= leases;
		}
		workers.add(new PMO_Worker( iterator, remaining, params, ri, shift,
				writeAllowed, addSectorsFromOtherDisks));
	}

	@Override
	public void run() {
		for (PMO_Worker worker : workers)
			threads.add(new Thread(worker));
		PMO_SystemOutRedirect.startRedirectionToNull();
		threads.forEach(th -> {
			th.setDaemon(true);
			th.start();
		});
		PMO_SystemOutRedirect.returnToStandardStream();
		threads.forEach(th -> {
			try {
				th.join();
			} catch (InterruptedException e) {
				joinException.set(true);
				e.printStackTrace();
			}
		});
		if (!joinException.get()) // jesli nie doszlo do wyjatku w trakcie join
									// to OK
			joinCompleted.set(true);
	}

	@Override
	public boolean test() {
		if (!joinCompleted.get())
			return false; // nie doszlo do join wszystkich watkow
		AtomicBoolean ab = new AtomicBoolean(true);
		workers.forEach(w -> ab.set(ab.get() && w.test()));
		return ab.get();
	}

}
