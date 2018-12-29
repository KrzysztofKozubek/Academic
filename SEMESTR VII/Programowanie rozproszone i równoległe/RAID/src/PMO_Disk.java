import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PMO_Disk implements DiskInterface, PMO_Testable {
	
	private PMO_AtomicCounter ac; // liczba jednoczesnych wywolan read/write
	private PMO_AtomicCounter acMax; // maksymalna liczba jednoczesnych wywolan read/write
	private PMO_AtomicCounter raidParallelUsage; // licznik jednoczesnych wywolan dyskow w RAID
	private AtomicBoolean isDiskBroken = new AtomicBoolean(); // dysk uszkodzony
	private AtomicInteger usageAfterDiskError = new AtomicInteger(0); // liczba uzyc uszkodzonego dysku
	private PMO_AtomicCounter usageAfterShutdown = PMO_CountersFactory.neverExecute(); // detektor uzycia
	private AtomicInteger writes = new AtomicInteger(0);
	private AtomicInteger reads = new AtomicInteger(0);
	private AtomicBoolean shutdown; // wyslano do obiektu polecenie shutdown
	private AtomicBoolean arrayOutOfBoundsErrorDetected = new AtomicBoolean( false );
	
	private int[] surface; // powierzchnia do zapisu
	private long sleepTime;
	
	@Override
	public String toString() {
		String result = "      liczba odczytow / zapisow     : " + reads.get() + " / " + writes.get() + "\n" ;
		if ( acMax.get() > 1 ) {
			result       += "      max jednoczesnych wywolan     : " + acMax.get() + "\n";
		}
		if ( arrayOutOfBoundsErrorDetected.get() ) 
			result       += "      czy wystapil blad outOfBounds : " + ( arrayOutOfBoundsErrorDetected.get() ? "TAK" : "NIE" ) + "\n";		
		if ( shutdown.get() )
			result       += "      liczba uzyc po shutdown       : " + usageAfterShutdown.get() + "\n" ;
		if ( isDiskBroken.get() ) {
			result       += "      czy dysk jest uszkodzony      : " + ( isDiskBroken.get() ? "TAK" : "NIE" ) + "\n" ;
			result       += "      liczba uzyc po bledzie        : " + usageAfterDiskError.get() + "\n" ;
		}
		return result;
	}
	
	public int getOperationAfterShutdown() {
		return usageAfterShutdown.get();
	}

	public int getOperationAfterError() {
		return usageAfterDiskError.get();
	}
	
	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public void damage() {
		isDiskBroken.set( true );
	}
	
	public int getReads() {
		return reads.get();
	}
	
	public void clearReads() {
		reads.set(0);
	}
	
	public int getWrites() {
		return writes.get();
	}
	
	public void clearWrites() {
		writes.set(0);
	}
	
	public PMO_Disk( int size, PMO_AtomicCounter raidParallelUsage, AtomicBoolean shutdown ) {
		acMax = PMO_CountersFactory.prepareCommonMaxStorageCounter();
		this.raidParallelUsage = raidParallelUsage;
		this.shutdown = shutdown;
		ac = PMO_CountersFactory.prepareCounterWithMaxStorageSet();
		
		// dobrze gdy pojedyncze wywolanie w danej chwili
		acMax.setOKPredicate( PMO_IntPredicateFactory.exactlyOne() );
		// zle gdy nie 1
		acMax.setFailPredicate( PMO_IntPredicateFactory.not( PMO_IntPredicateFactory.exactlyOne() ));
		
		surface = new int[ size ];
	}

	@Override
	public void write(int sector, int value) throws DiskError {
		raidParallelUsage.incAndStoreMax();
		ac.incAndStoreMax();
		
		writes.incrementAndGet();
		
		if ( shutdown.get() ) usageAfterShutdown.inc();
		
		Thread.yield();
		
		PMO_TimeHelper.sleep( sleepTime );
		try {
			if ( isDiskBroken.get() ) {
				usageAfterDiskError.incrementAndGet();
				throw new DiskError();
			} else {
				if ( sector < 0 ) {
					arrayOutOfBoundsErrorDetected.set( true );
					PMO_SystemOutRedirect.println( "BLAD: Zapis do sektora < 0");
					return;
				}
				if ( sector >= surface.length ) {
					PMO_SystemOutRedirect.println( "BLAD: Zapis do sektora >= rozmiar dysku fizycznego");
					arrayOutOfBoundsErrorDetected.set( true );
					return;					
				}
				surface[ sector ] = value;
				Thread.yield();
			}
		}
		finally {
			ac.dec();
			raidParallelUsage.dec();			
		}
	}

	@Override
	public int read(int sector) throws DiskError {
		raidParallelUsage.incAndStoreMax();
		ac.incAndStoreMax();
		
		reads.incrementAndGet();
		
		if ( shutdown.get() ) usageAfterShutdown.inc();
		
		Thread.yield();
		
		
		try {
			int value = surface[ sector ];
			PMO_TimeHelper.sleep( sleepTime );

			if ( isDiskBroken.get() ) {
				usageAfterDiskError.incrementAndGet();
				throw new DiskError();
			} else {
				if ( sector < 0 ) {
					arrayOutOfBoundsErrorDetected.set( true );
					PMO_SystemOutRedirect.println( "BLAD: Odczyt z sektora < 0");
					return Integer.MIN_VALUE;
				}
				if ( sector >= surface.length ) {
					arrayOutOfBoundsErrorDetected.set( true );
					PMO_SystemOutRedirect.println( "BLAD: Odczyt z sektora >= rozmiar dysku fizycznego");
					return -1;					
				}

				return value;
			}
		}
		finally {
			ac.dec();
			raidParallelUsage.dec();			
		}
	}

	@Override
	public int size() {
		return surface.length;
	}

	@Override
	public boolean test() {
		if ( acMax.isFail().get() ) {
			PMO_SystemOutRedirect.println( "BLAD: Metody read/write nie byly wywolywane sekwencyjnie");
			PMO_SystemOutRedirect.println( "      Wykryto " + acMax.get() + " jednoczesnych wywolan" );
			return false;
		}
		
		if ( usageAfterShutdown.isFail().get() ) {
			PMO_SystemOutRedirect.println( "BLAD: Po zakonczeniu metody shutdown wykryto uzycie dysku");
			return false;
		}

		if ( usageAfterDiskError.get() > 2 ) {
			PMO_SystemOutRedirect.println( "BLAD: Wykryto uzycie uszkodzonego dysku");
			PMO_SystemOutRedirect.println( "      Wykryto " + (usageAfterDiskError.get()-1) + " wywolan po zgloszeniu bledu" );
			return false;
		}

		if ( arrayOutOfBoundsErrorDetected.get() ) {
			PMO_SystemOutRedirect.println( "BLAD: Wykryto uzycie dysku prowadzace do ArrayIndexOutOfBoundsException");
			return false;
		}
		
		return true;
	}
	
}
