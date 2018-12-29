import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PMO_SystemOutRedirect {
	private static PrintStream ps;
	private static PrintStream orig = System.out;
	private static boolean redirectionON = false;

	static {
		try {
			ps = new PrintStream("/dev/null");
		} catch (FileNotFoundException e) {
			System.out.println( "System.out.println can not be redirected to /dev/null");
		}
	}

	public static void startRedirectionToNull() {
		if (ps != null) {
			System.setOut(ps);
			System.setErr(ps);
		}
		redirectionON = true;
	}

	public static void returnToStandardStream() {
		System.setOut(orig);
		System.setErr(orig);
		redirectionON = false;
	}

	public static void closeNullStream() {
		ps.close();
	}

	public static void print(String s) {
		if (redirectionON) {
			returnToStandardStream();
			System.out.print(s);
			startRedirectionToNull();
		} else {
			System.out.print(s);
		}
	}

	public static void println(String s) {
		if (redirectionON) {
			returnToStandardStream();
			System.out.println(s);
			startRedirectionToNull();
		} else {
			System.out.println(s);
		}
	}
	
	public static void showCurrentMethodName() {
		println( "-------------------------------------" );
		println( Thread.currentThread().getStackTrace()[2].getMethodName()) ;
		println( "-------------------------------------" );
	}

}
