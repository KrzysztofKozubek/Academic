
//public class Service extends GeneratorPOA {
//
//	private final Map<Integer,RMIGenerator> users = new HashMap<>();
//	private final Map<RMIGenerator,ExecutorService> executors = new HashMap<>();
//	private final Map<Integer,Books> orders = new HashMap<>();
//
//	private static int aUsr = 0;
//	private static int aOrd = 0;
//
//	public synchronized void register( String rmiServiceURL, IntHolder userID ) {
//
//		RMIGenerator rmiService = Helper.getGenerator(rmiServiceURL);
//
//		if ( !users.containsKey(userID) ) {
//
//			aUsr++;
//
//			userID.value = aUsr;
//			users.putIfAbsent(userID.value,rmiService);
//
//			try {
//				executors.putIfAbsent(rmiService , Executors.newFixedThreadPool(rmiService != null ? rmiService.getThreads() : 0));
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Klasa rozdziela zadania
//	 */
//	private class Scheduler implements Callable<Boolean> {
//		private int userID;
//		int averageOver;
//		int orderID;
//		RMIGenerator rmiGen;
//
//		public Boolean call() throws Exception {
//
//			int i = 0;
//			orders.put(orderID, new Books(averageOver));
//			ExecutorService executor = executors.get(rmiGen);
//
//			while (i < averageOver) {
//				callableGetValue c = new callableGetValue(rmiGen, orderID);
//				executor.submit(c);
//				i++;
//			}
//			return true;
//		}
//
//		Scheduler(int uID, int aOver, int oID) {
//			this.userID 	 = uID;
//			this.averageOver = aOver;
//			this.orderID 	 = oID;
//			this.rmiGen		 = users.get(userID);
//		}
//	}
//
//	public void order( int userID, int averageOver, IntHolder orderID ) {
//
//		aOrd++;
//		int orderVal = aOrd;
//
//		Scheduler scheduler;
//		ExecutorService schedulerExecutor;
//
//		scheduler = new Scheduler( userID, averageOver, orderVal );
//		schedulerExecutor = Executors.newSingleThreadExecutor();
//		schedulerExecutor.submit(scheduler);
//
//		orderID.value = orderVal;
//	}
//
//	public boolean isReady( int orderID ) {
//		return orders.containsKey(orderID) && orders.get(orderID).isReady();
//	}
//
//	public float getAverage( int orderID ) {
//		int sum = 0;
//		List<Integer> temp = orders.get(orderID).var;
//
//		for (Integer aTemp : temp) {
//			int i = aTemp;
//			sum += i;
//		}
//
//		return (float)sum / (float)orders.get(orderID).averageOver;
//	}
//
//	/**
//	 *
//	 */
//	private class callableGetValue implements Callable<Boolean> {
//		private RMIGenerator rmiGen;
//		int orderID;
//		int val;
//
//		public Boolean call() throws Exception {
//
//			val = rmiGen.getValue();
//			Books o;
//
//			synchronized ( orders ) {
//				o = orders.get(orderID);
//				o.var.add( val );
//			}
//			return true;
//		}
//
//		callableGetValue (RMIGenerator r, int or) {
//			this.rmiGen   = r;
//			this.orderID  = or;
//		}
//	}
//
//	/**
//	 *
//	 */
//	private class Books{
//		private Integer averageOver;
//		List<Integer> var;
//
//		boolean isReady() {
//			return !var.isEmpty() && var.size() == averageOver;
//		}
//
//		Books(int a) {
//			this.averageOver = a;
//			this.var = new ArrayList<>();
//		}
//	}
//}
//
///**
// * Klasa Start zgodnie ze specyfikacja.
// *
// * @author dwgadomski
// *
// */
//class Start {
//	public static void main(String[] argv) throws CannotProceed, InvalidName, NotFound, org.omg.CORBA.ORBPackage.InvalidName, ServantNotActive, WrongPolicy, AdapterInactive {
//
//		ORB orb = ORB.init(argv, null);
//		POA rootpoa = (POA) orb.resolve_initial_references("RootPOA");
//		rootpoa.the_POAManager().activate();
//
//		Service service = new Service();
//		org.omg.CORBA.Object ref = rootpoa.servant_to_reference(service);
//		org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
//		NamingContext namingContext = NamingContextHelper.narrow(namingContextObj);
//
//		NameComponent[] path = {
//				new NameComponent("Generator", "Object")
//		};
//
//		namingContext.rebind(path, ref);
//		orb.run();
//	}
//}
