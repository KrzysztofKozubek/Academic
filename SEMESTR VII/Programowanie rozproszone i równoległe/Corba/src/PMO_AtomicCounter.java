
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;

public class PMO_AtomicCounter {
	private AtomicInteger counter = new AtomicInteger(0);
	private PMO_AtomicCounter maxValue;
	private IntPredicate okTest;
	private IntPredicate failTest;
	private Runnable codeToAutorun; // kod do wykonania
	private int autorunTrigger = Integer.MIN_VALUE; // osiagniecie tej wartosci wyzwala kod
	private AtomicBoolean codeToAutorunExecuted = new AtomicBoolean( false );

	public int inc() {
		return counter.incrementAndGet();
	}
	
	public void clear() {
		counter.set(0);
	}

	public void setAutoRun(int autorunTrigger, Runnable codeToRun) {
		this.autorunTrigger = autorunTrigger;
		codeToAutorun = codeToRun;
	}

	public int incAndStoreMax() {
		int v = inc();
		if ( maxValue != null ) maxValue.setIfBigger(v);
		return v;
	}

	public int add(int v) {
		return counter.addAndGet(v);
	}

	public int addAndStoreMax(int v) {
		int res = add(v);
		if ( maxValue != null ) maxValue.setIfBigger(res);
		return res;
	}

	public int dec() {
		return counter.decrementAndGet();
	}

	public int sub(int v) {
		return counter.addAndGet(-v);
	}

	public void setMaxStorage(PMO_AtomicCounter ac) {
		maxValue = ac;
	}

	public int get() {
		return counter.get();
	}

	public void setOKPredicate(IntPredicate okTest) {
		this.okTest = okTest;
	}

	public void setFailPredicate(IntPredicate failTest) {
		this.failTest = failTest;
	}

	private void setIfBigger(int value) {
		if (value > counter.get()) {
			synchronized (counter) {
				if (value > counter.get()) {
					counter.set(value);
					if (autorunTrigger == value) {
						codeToAutorun.run();
						codeToAutorunExecuted.set( true );
					}
				}
			}
		}
	}

	public Optional<Boolean> isOK() {
		if (okTest == null)
			return Optional.empty();

		return Optional.of(okTest.test(counter.get()));
	}

	public Optional<Boolean> isFail() {
		if (failTest == null)
			return Optional.empty();

		return Optional.of(failTest.test(counter.get()));
	}
	
	public boolean autorunExecuted() {
		return codeToAutorunExecuted.get();
	}
}
