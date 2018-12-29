import java.util.function.BooleanSupplier;

public class PMO_TimeHelper {
	public static long executionTime(Runnable run) {
		long start = System.currentTimeMillis();

		run.run();

		return System.currentTimeMillis() - start;
	}

	public static long executionTime(BooleanSupplier run, long resolution ) {
		long start = System.currentTimeMillis();

		while ( run.getAsBoolean() ){
			sleep( resolution );
		}

		return System.currentTimeMillis() - start;
	}
	
	
	public static boolean sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
