
public class PMO_CountersFactory {

	private static PMO_AtomicCounter maxCounter;

	public static PMO_AtomicCounter executeOnce() {
		PMO_AtomicCounter result = new PMO_AtomicCounter();

		result.setFailPredicate(i -> i != 1);
		result.setOKPredicate(i -> i == 1);

		return result;
	}

	public static PMO_AtomicCounter neverExecute() {
		PMO_AtomicCounter result = new PMO_AtomicCounter();

		result.setFailPredicate(i -> i != 0);
		result.setOKPredicate(i -> i == 0);

		return result;
	}

	public static PMO_AtomicCounter prepareCommonMaxStorageCounter() {
		maxCounter = new PMO_AtomicCounter();
		return maxCounter;
	}

	public static PMO_AtomicCounter prepareCounterWithMaxStorageSet() {
		PMO_AtomicCounter ac = new PMO_AtomicCounter();
		ac.setMaxStorage(maxCounter);
		return ac;
	}
}
