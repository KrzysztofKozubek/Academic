import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.concurrent.atomic.AtomicInteger;

public class Narrow {

	// limit wyswietlec bledow
	private static final int MAX = 5;
	private static AtomicInteger exec = new AtomicInteger(MAX);

	private synchronized static void listServices() {

		Registry reg = null;
		try {
			reg = java.rmi.registry.LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			PMO_SystemOutRedirect.println( "Wystapil wyjatek zwiazany z getRegistry");
			return;
		}
		try {
			PMO_SystemOutRedirect.println("Lista zarejestrowanych serwisow");
			int i = 1;
			for (String service : reg.list()) {
				PMO_SystemOutRedirect.println("Serwis #" + (i++) + " : " + service);
			}
		} catch (RemoteException e) {
			PMO_SystemOutRedirect.println( "Wystapil wyjatek w trakcie listowania serwisow");
		}
	}

	public static AbstractWard find(String callbackServiceName) {

		if (callbackServiceName == null) {
			PMO_SystemOutRedirect.println("BLAD: Zlecono poszukiwanie serwisu o nazwie == null");
			return null;
		}

		Registry reg = null;
		try {
			reg = java.rmi.registry.LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			PMO_SystemOutRedirect.println( "Wystapil wyjatek wywolany getRegistry");
			return null;
		}
		
		try {
			return (AbstractWard) reg.lookup(callbackServiceName);
		} catch (Exception e) {
			if (exec.decrementAndGet() >= 0) { // ograniczenie liczby prezentowanych bledow

				synchronized (PMO_SystemOutRedirect.class) {
					PMO_SystemOutRedirect.returnToStandardStream();
					e.printStackTrace();
					PMO_SystemOutRedirect.println("W trakcie poszukiwania serwisu " + callbackServiceName
							+ " pojawil sie wyjatek " + e.getMessage());
					listServices();
					PMO_SystemOutRedirect.startRedirectionToNull();
				}

			}
			return null;
		}
	}
}
