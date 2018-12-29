
public class PMO_Verdict {
	public static void show(boolean result) {
		if (result) {
			PMO_SystemOutRedirect.println("--------------------------------------------------------");
			PMO_SystemOutRedirect.println("   ___  _  __");
			PMO_SystemOutRedirect.println("  / _ \\| |/ /");
			PMO_SystemOutRedirect.println(" | | | | ' / ");
			PMO_SystemOutRedirect.println(" | |_| | . \\ ");
			PMO_SystemOutRedirect.println("  \\___/|_|\\_\\");

			PMO_SystemOutRedirect.println("");
			PMO_SystemOutRedirect.println("--- NIE WYKRYTO BLEDU (co nie oznacza, ze go nie ma) ---");
			PMO_SystemOutRedirect.println("--------------------------------------------------------");
		} else {
			PMO_SystemOutRedirect.println("  ____  _        _    ____  ");
			PMO_SystemOutRedirect.println(" | __ )| |      / \\  |  _ \\ ");
			PMO_SystemOutRedirect.println(" |  _ \\| |     / _ \\ | | | |");
			PMO_SystemOutRedirect.println(" | |_) | |___ / ___ \\| |_| |");
			PMO_SystemOutRedirect.println(" |____/|_____/_/   \\_\\____/ ");
		}
	}

}
