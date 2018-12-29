import java.util.List;

public interface PMO_Testable {
	boolean testIfOK();
	
	public static boolean allOK( List<PMO_Testable> tests ) {
		PMO_SystemOutRedirect.println( "Wykonuje sie metoda allOK na liscie zawierajacej " + tests.size() + " testow");
		return ! tests.stream().anyMatch( e -> ! e.testIfOK() );
	}
}
