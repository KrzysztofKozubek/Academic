import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PMO_Worker implements Runnable, PMO_Testable {

	private RAIDInterface2 ri;
	private List<Integer> sectors;
	private boolean stateOK = true;
	private int shift;
	private boolean writeAllowed;

	PMO_Worker(Iterator<Integer> sectors, int leases, PMO_RAIDParameters params, RAIDInterface2 ri, int shift,
			boolean writeAllowed, boolean addSectorsFromOtherDisks ) {
		this.sectors = new ArrayList<>(leases * params.disks);
		
		for (int i = 0; i < leases; i++) {
			if (sectors.hasNext()) {
				int sector = sectors.next();
				if ( addSectorsFromOtherDisks )
					for (int d = 0; d < params.disks - 1; d++) {
						this.sectors.add(sector + d * params.sectorsPerDisk);
					}
				else {
					this.sectors.add( sector );
				}
			}
		}
		Collections.shuffle(this.sectors);
		PMO_SystemOutRedirect.println("Tu Worker, mam do wykonania " + this.sectors.size() + " operacji");
		PMO_SystemOutRedirect.println("Tu Worker, mam do wykonania " + Arrays.toString( this.sectors.toArray( new Integer[ this.sectors.size() ] )));
		this.shift = shift;
		this.ri = ri;
		this.writeAllowed = writeAllowed;
	}

	@Override
	public void run() {
		try {
			for (Integer sector : sectors) {
				int value = (sector + shift) % 255;
				if (writeAllowed) {
					ri.write(sector, value);
				}
				int valueR = ri.read(sector);
				if (valueR != value) {
					PMO_SystemOutRedirect
							.println("BLAD: Do sektora " + sector + " zapisano " + value + " a odczytano " + valueR);
					stateOK = false;
				}
			}
		} catch (Exception e) {
			stateOK = false;
			PMO_SystemOutRedirect.println("BLAD: W trakcie uzycia doszlo do wyjatku " + e.getMessage());
		}
	}

	@Override
	public boolean test() {
		return stateOK;
	}

}
