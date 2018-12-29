import java.io.Serializable;

public class Patient implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8090566389592281974L;
	private final long id;
	private static long counter;
	
	public long getID() {
		return id;
	}
	
	public Patient() {
		id = counter++;
	}
}
