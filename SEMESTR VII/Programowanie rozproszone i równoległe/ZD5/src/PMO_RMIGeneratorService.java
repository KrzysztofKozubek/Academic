import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PMO_RMIGeneratorService implements RMIGenerator, PMO_Testable {

	private final String name;
	private final int valueToReturn;
	private final int threads;
	private static Registry reg;
	private PMO_AtomicCounter maxConcurentUsages = PMO_CountersFactory.prepareCommonMaxStorageCounter();
	private PMO_AtomicCounter concurentUsages = PMO_CountersFactory.prepareCounterWithMaxStorageSet();
	private PMO_AtomicCounter usages = new PMO_AtomicCounter();
	private PMO_AtomicCounter expectedUsages = new PMO_AtomicCounter();
	private final long sleepTime;

	public PMO_RMIGeneratorService(String name, int threads, int valueToReturn, long sleepTime) {
		this.name = name;
		this.threads = threads;
		this.valueToReturn = valueToReturn;
		this.sleepTime = sleepTime;

		if (reg == null) {
			Registry tmpReg = null;
			try {
				tmpReg = LocateRegistry.createRegistry(1099);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			reg = tmpReg;
		}

		try {
			Remote ref = UnicastRemoteObject.exportObject(this, 0);
			reg.rebind(this.name, ref);
			PMO_SystemOutRedirect.println("Zarejestrowano serwis RMI o nazwie " + this.name + 
					"\n serwis do uzycia przez " + this.getThreads() + " watkow" +
					"\n generuje liczbe        " + this.expectedValue() );
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public int expectedValue() {
		return valueToReturn;
	}
	
	public int expectedThreads() {
		return threads;
	}

	@Override
	public int getThreads() throws RemoteException {
		return threads;
	}

	@Override
	public int getValue() throws RemoteException {
		concurentUsages.incAndStoreMax();

		usages.inc();
		PMO_TimeHelper.sleep(sleepTime);

		concurentUsages.dec();
		return valueToReturn;
	}

	public boolean testIfOK() {
		boolean result = true;
		
		PMO_SystemOutRedirect.println( "PMO_RMIGeneratorService");
		
		if (maxConcurentUsages.get() != threads) {
			PMO_SystemOutRedirect.println("BLAD: Serwis RMI powinien byc uzywany za pomoca " + threads
					+ " watkow, a stwierdzono " + maxConcurentUsages.get());
			result = false;
		} else {
			PMO_SystemOutRedirect.println("OK: Serwis RMI " + name + " uzywano za pomoca " + maxConcurentUsages.get() + 
					" watkow na " + expectedThreads() + " oczekiwanych" );
		}
		if (expectedUsages.get() != usages.get()) {
			PMO_SystemOutRedirect.println("BLAD: Do zrealizowania zlecen serwis RMI powinien wygenerowac "
					+ expectedUsages.get() + " liczb, a odebrano " + usages.get());
			result = false;
		} else {
			PMO_SystemOutRedirect.println("OK: Do zrealizowania zlecen serwis RMI " + name + " powinien wygenerowac "
					+ expectedUsages.get() + " liczb i odebrano " + usages.get());			
		}
		return result;
	}

	public void addExpectedNumberOfReads(int reads) {
		expectedUsages.add(reads);
	}
}
