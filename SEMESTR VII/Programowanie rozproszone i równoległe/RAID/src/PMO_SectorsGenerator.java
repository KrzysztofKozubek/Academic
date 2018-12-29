import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PMO_SectorsGenerator {
	public static List<Integer> generate(PMO_RAIDParameters params) {
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < params.sectorsPerDisk; i++)
			result.add(i);

		Collections.shuffle(result);
		return result;
	}
	
	public static List<Integer> generateAll(PMO_RAIDParameters params) {
		List<Integer> result = new ArrayList<>();

		for ( int j = 0; j < params.disks-1; j++ ) // jeden z dyskow - suma kontrolna
			for (int i = 0; i < params.sectorsPerDisk; i++)
				result.add(i + j * params.sectorsPerDisk );

//		PMO_SystemOutRedirect.showCurrentMethodName();
//		PMO_SystemOutRedirect.println( "Wygenerowano ciag : " + result.size() + " sektorow");
		
		return result;
	}
}
