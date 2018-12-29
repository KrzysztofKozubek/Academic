import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;

public class PMO_Start {
	public static final long GETVALUE_EXECUTION_TIME = 60; // msec

	private static Generator gen;

	private static void connectToServer(String[] argv) {
		PMO_SystemOutRedirect.println("Polaczenie do serwera - poczatek");
		ORB orb = ORB.init(argv, null);
		org.omg.CORBA.Object namingContextObj;
		try {
			namingContextObj = orb.resolve_initial_references("NameService");
			NamingContext namingContext = NamingContextHelper.narrow(namingContextObj);

			NameComponent[] path = { new NameComponent("Generator", "Object") };

			org.omg.CORBA.Object envObj = namingContext.resolve(path);
			gen = GeneratorHelper.narrow(envObj);
			PMO_SystemOutRedirect.println("Polaczenie do serwera - OK");
			return;
		} catch (Exception e) {
			PMO_SystemOutRedirect.println("Odebrano wyjatek juz w trakcie polaczenia do systemu");
			PMO_SystemOutRedirect.println("Test polaczenia bezposrednio z Generator");
			try {
				namingContextObj = orb.resolve_initial_references("NameService");
				NamingContextExt ncRef = NamingContextExtHelper.narrow(namingContextObj);
				String name = "Generator";
				NameComponent path[] = ncRef.to_name(name);
				org.omg.CORBA.Object envObj = ncRef.resolve(path);
				gen = GeneratorHelper.narrow(envObj);
				PMO_SystemOutRedirect.println("Polaczenie do serwera - OK");
				return;
			} catch (Exception e1) {
				PMO_SystemOutRedirect.println("Podobnie bez skutku - nie mozna odszukac serwisu");
			}

			PMO_SystemOutRedirect.println("Brak polacznia z serwisem - nie mozna kontynuowac testu");
			PMO_ThreadsHelper.shutdown();
		}
	}

	private abstract static class TestSuperClass implements PMO_RunnableAndTestable {
		protected List<PMO_RMIGeneratorService> rmiServices = new ArrayList<>();
		protected List<PMO_CorbaClient> corbaClients = new ArrayList<>();
		protected List<PMO_Testable> tests = new ArrayList<>();
		protected List<Thread> threads;

		abstract protected void generateRMIServices();

		abstract protected void generateCorbaClients();

		@Override
		public void run() {
			generateRMIServices();
			generateCorbaClients();
			threads = PMO_ThreadsHelper.RunnableToThread(corbaClients);
			PMO_ThreadsHelper.startThreads(threads);
			PMO_ThreadsHelper.joinThreads(threads);
		}

		@Override
		public boolean testIfOK() {
			PMO_SystemOutRedirect.showCurrentMethodName();
			corbaClients.forEach(cc -> tests.add(cc));
			rmiServices.forEach(cc -> tests.add(cc));
			return PMO_Testable.allOK(tests);
		}

	}

	// test pracy systemu w przypadku gdy jeden klient uzywa
	// jednego serwisu RMI i zleca jedna prace
	private static class Test1 extends TestSuperClass {
		@Override
		protected void generateRMIServices() {
			for (int i = 1; i <= 10; i++) {
				rmiServices.add(
						new PMO_RMIGeneratorService("Test1_" + i, 1 + i / 2, i, PMO_Start.GETVALUE_EXECUTION_TIME));
			}
		}

		@Override
		protected void generateCorbaClients() {
			final CyclicBarrier cb = new CyclicBarrier(rmiServices.size());
			final Set<Integer> uniqID = Collections.synchronizedSet(new HashSet<>());
			int avrOver;
			for (int i = 0; i < rmiServices.size(); i++) {
				avrOver = 10 * rmiServices.get(i).expectedThreads();
				corbaClients.add(new PMO_CorbaClient(gen, rmiServices.get(i), cb, 1, avrOver, avrOver, uniqID));
			}
		}
	}

	// test pracy systemu w przypadku gdy jeden klient uzywa
	// jednego serwisu RMI i zleca wiele prac do wykonania
	private static class Test2 extends TestSuperClass {
		@Override
		protected void generateRMIServices() {
			for (int i = 1; i <= 10; i++) {
				rmiServices.add(
						new PMO_RMIGeneratorService("Test2_" + i, 1 + i / 2, i, PMO_Start.GETVALUE_EXECUTION_TIME));
			}
		}

		@Override
		protected void generateCorbaClients() {
			final CyclicBarrier cb = new CyclicBarrier(rmiServices.size());
			final Set<Integer> uniqID = Collections.synchronizedSet(new HashSet<>());
			int avrOver;
			for (int i = 0; i < rmiServices.size(); i++) {
				avrOver = 10 * rmiServices.get(i).expectedThreads();
				corbaClients.add(new PMO_CorbaClient(gen, rmiServices.get(i), cb, 15, avrOver, avrOver + 10, uniqID));
			}
		}
	}

	// test pracy systemu w przypadku gdy wielu klientow uzywa
	// tego samego serwisu RMI i zleca wiele prac do wykonania
	private static class Test3 extends TestSuperClass {
		@Override
		protected void generateRMIServices() {
			for (int i = 1; i <= 7; i++) {
				rmiServices.add(
						new PMO_RMIGeneratorService("Test3_" + i, 1 + i / 2, i, PMO_Start.GETVALUE_EXECUTION_TIME));
			}
		}

		@Override
		protected void generateCorbaClients() {
			final CyclicBarrier cb = new CyclicBarrier(rmiServices.size());
			final Set<Integer> uniqID = Collections.synchronizedSet(new HashSet<>());
			int avrOver;
			for (int i = 0; i < rmiServices.size(); i++) {
				avrOver = 10 * rmiServices.get(i).expectedThreads();
				corbaClients.add(new PMO_CorbaClient(gen, rmiServices.get(i), cb, 10, avrOver, avrOver + 20, uniqID));
				corbaClients.add(new PMO_CorbaClient(gen, rmiServices.get(i), cb, 10, avrOver, avrOver + 25, uniqID));
				corbaClients.add(new PMO_CorbaClient(gen, rmiServices.get(i), cb, 10, avrOver, avrOver + 30, uniqID));
			}
		}
	}

	public static void main(String[] args) {
		connectToServer(args);
		boolean result = true;

		result &= PMO_ThreadsHelper.runTest(new Test1(), 5000 * GETVALUE_EXECUTION_TIME);

		if (!result) {
			PMO_Verdict.show(false);
			PMO_ThreadsHelper.shutdown();
		}

		result &= PMO_ThreadsHelper.runTest(new Test2(), 10000 * GETVALUE_EXECUTION_TIME);
		result &= PMO_ThreadsHelper.runTest(new Test3(), 45000 * GETVALUE_EXECUTION_TIME);

		PMO_Verdict.show(result);
		PMO_ThreadsHelper.shutdown();
	}

}
