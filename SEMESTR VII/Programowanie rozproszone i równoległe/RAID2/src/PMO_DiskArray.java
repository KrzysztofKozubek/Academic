import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class PMO_DiskArray implements PMO_Testable {
	private PMO_AtomicCounter parallelDiskUsageMax;
	private PMO_AtomicCounter parallelDiskUsage;
	private AtomicBoolean shutdownFlag = new AtomicBoolean(false);
	private PMO_Disk[] disks;
	private int paralleUsageLimit;
	private PMO_RAIDParameters params;

	public PMO_DiskArray(PMO_RAIDParameters params, int paralleUsageLimit) {
		this.disks = new PMO_Disk[params.disks];
		parallelDiskUsageMax = PMO_CountersFactory.prepareCommonMaxStorageCounter();
		parallelDiskUsage = PMO_CountersFactory.prepareCounterWithMaxStorageSet();
		this.paralleUsageLimit = paralleUsageLimit;

		parallelDiskUsageMax.setOKPredicate(PMO_IntPredicateFactory.moreThenOne());
		parallelDiskUsageMax.setFailPredicate(PMO_IntPredicateFactory.not(PMO_IntPredicateFactory.moreThenOne()));
		this.params = params;

		for (int i = 0; i < params.disks; i++)
			this.disks[i] = new PMO_Disk(params.sectorsPerDisk, parallelDiskUsage, shutdownFlag);
	}
	
	@Override
	public String toString() {
		String result = "Disk array: \n" ;
		result +=       " liczba dyskow                      : " + disks.length + "\n";
		result +=       " liczba sektorow na dysku fizycznym : " + params.sectorsPerDisk + "\n";
		result +=       " liczba sektorow na dysku logicznym : " + params.logicalSectors + "\n";
		result +=       " liczba jednoczesnych uzyc dyskow   : " + parallelDiskUsageMax.get() + "\n";
		result +=       " wykonano shutdown na kontrolerze   : " + ( shutdownFlag.get() ? "TAK" : "NIE" ) + "\n";
		
		for ( int i = 0; i < disks.length; i++ )  {
			result += " - Dysk numer " + i +  "\n";			
			result += disks[i].toString();
		}
		
		return result;
	}
	
	public int getMaxParallelDiskUsage() {
		return parallelDiskUsageMax.get();
	}

	public DiskInterface2[] getArray() {
		return disks;
	}

	public PMO_Testable[] getDisksAsTests() {
		return disks;
	}
	
	public PMO_Disk getDisk( int i ) {
		return disks[ i ];
	}
	
	public int getReadsFromSumDisk() {
		return disks[ disks.length - 1 ].getReads();
	}
	
	public void setShutdownFlag() {
		shutdownFlag.set( true );
	}
	
	public void clearCounters() {
		Arrays.asList( disks ).forEach( d -> d.clearReads() );
		Arrays.asList( disks ).forEach( d -> d.clearWrites() );
		parallelDiskUsageMax.clear();
	}

	public void setParallelUsageLimit(int limit) {
		paralleUsageLimit = limit;
	}

	public int totalReadOperations() {
		return Arrays.asList( disks ).stream().mapToInt( d -> d.getReads() ).sum();
	}
	
	public void showReadOperations() {
		for ( int i = 0; i < disks.length; i++ )
			System.out.println( "Dysk numer " + i + " odczytow: " + disks[ i ].getReads() );
	}
	
	public int totalOperationsAfterShutdown() {
		return Arrays.asList( disks ).stream().mapToInt( d -> d.getOperationAfterShutdown() ).sum();		
	}

	public int totalOperationsAfterError() {
		return Arrays.asList( disks ).stream().mapToInt( d -> d.getOperationAfterError() ).sum();		
	}
	
	public int totalWriteOperations() {
		return Arrays.asList( disks ).stream().mapToInt( d -> d.getWrites() ).sum();
	}
	
	public int totalDiskOperations() {
		return totalReadOperations() + totalWriteOperations();
	}
	
	@Override
	public boolean test() {
		if (parallelDiskUsageMax.isFail().get()) {
			PMO_SystemOutRedirect.println("BLAD: Nie stwierdzono rownoleglego uzycia dyskow z macierzy");
			PMO_SystemOutRedirect.println("    : Jednoczesna liczba uzyc dyskow " + parallelDiskUsageMax.get());
			return false;
		}
		if (parallelDiskUsageMax.get() < paralleUsageLimit) {
			PMO_SystemOutRedirect.println("BLAD: Jednoczesnie uzyto " + parallelDiskUsageMax.get() + " dyskow");
			PMO_SystemOutRedirect.println("      Spodziewano sie co najmniej: " + paralleUsageLimit);
			return false;
		}
		return true;
	}

	public void setSleepTime(long sleepTime) {
		Arrays.asList(disks).forEach(e -> e.setSleepTime(sleepTime));
	}

}
