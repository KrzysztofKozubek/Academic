import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kriss on 20.01.2017.
 */
public class Service extends GeneratorPOA {

    private final Map<Integer, RMIGenerator> persons = new HashMap<>();
    private final Map<RMIGenerator, ExecutorService> executors = new HashMap<>();
    private final Map<Integer, Books> orders = new HashMap<>();

    private static int intPersons = 0;
    private static int intOrders = 0;

    private void incrementPerson(){intPersons++;}
    private void incrementOrders(){intOrders++;}

    private RMIGenerator getRMIGenerator(String rmiServiceURL){return Helper.getGenerator(rmiServiceURL);}

    private void addUserID(RMIGenerator rmiGenerator, IntHolder userID){

        incrementPerson();
        userID.value = intPersons;
        persons.putIfAbsent(userID.value, rmiGenerator);
    }

    private void getExecute(RMIGenerator rmiGenerator) throws Exception{
        executors.putIfAbsent(rmiGenerator, Executors.newFixedThreadPool(rmiGenerator != null ? rmiGenerator.getThreads() : 0));
    }

    public synchronized void register(String rmiServiceURL, IntHolder userID) {

        RMIGenerator rmiGenerator = getRMIGenerator(rmiServiceURL);

        if (!persons.containsKey(userID)) {
            addUserID(rmiGenerator, userID);
            try {
                getExecute(rmiGenerator);
            } catch (Exception e) {}
        }
    }

    private void submitExecutor(int userID, int averageOver, int orderVal){

        Scheduler scheduler = new Scheduler(userID, averageOver, orderVal);
        ExecutorService schedulerExecutor = Executors.newSingleThreadExecutor();
        schedulerExecutor.submit(scheduler);
    }

    public void order(int userID, int averageOver, IntHolder orderID) {

        incrementOrders();
        int orderVal = intOrders;
        submitExecutor(userID, averageOver, orderVal);
        orderID.value = orderVal;
    }

    public boolean isReady(int orderID) {
        if (orders.containsKey(orderID))
            if(orders.get(orderID).isReady()) return true;
        else return false;
        return false;
    }

    public float getAverage(int orderID) {

        int sum = 0;
        List<Integer> temp = orders.get(orderID).listValue;
        for (Integer var : temp) sum += var;
        return sum / (float) orders.get(orderID).averageOver;
    }


    private class Scheduler implements Callable<Boolean> {

        int averageOver;
        int orderID;
        RMIGenerator rmiGenerator;

        Scheduler(int userID, int averageOver, int orderID) {
            this.averageOver = averageOver;
            this.orderID = orderID;
            this.rmiGenerator = persons.get(userID);
        }

        private ExecutorService getExecutorService(){ return executors.get(rmiGenerator); }

        private ConnectGetValue getConnectGetValue(){ return new ConnectGetValue(rmiGenerator, orderID);}

        public Boolean call() throws Exception {

            orders.put(orderID, new Books(averageOver));
            ExecutorService executor = getExecutorService();

            int i = 0;
            for (; i < averageOver; i++) {
                ConnectGetValue connectGetValue = getConnectGetValue();
                executor.submit(connectGetValue);
            }
            return true;
        }

    }

    private class ConnectGetValue implements Callable<Boolean> {
        private RMIGenerator rmiGenerator;
        int orderID;
        int var;

        ConnectGetValue(RMIGenerator rmiGenerator, int IDOrder) {
            this.rmiGenerator = rmiGenerator;
            this.orderID = IDOrder;
        }


        private int getValueRMI() throws Exception{
            return rmiGenerator.getValue();
        }

        private void addOrders(){
            orders.get(orderID).listValue.add(var);
        }

        public Boolean call() throws Exception {

            var = getValueRMI();
            synchronized (orders) {
                addOrders();
            }
            return true;
        }
    }

    private class Books {

        private Integer averageOver;
        List<Integer> listValue = new ArrayList<>();

        Books(int averageOver) {
            this.averageOver = averageOver;
        }

        boolean isReady() {
            if (!listValue.isEmpty() && listValue.size() == averageOver) return true;
            else return false;
        }
    }
}


class Start {

    private static void running(String[] argv) throws Exception{

        ORB orb = ORB.init(argv, null);
        POA poa = (POA) orb.resolve_initial_references("RootPOA");
        poa.the_POAManager().activate();

        Service service = new Service();
        org.omg.CORBA.Object ref = poa.servant_to_reference(service);
        org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
        NamingContext namingContext = NamingContextHelper.narrow(namingContextObj);

        NameComponent[] path = {
                new NameComponent("Generator", "Object")
        };

        namingContext.rebind(path, ref);
        orb.run();
    }

    public static void main(String[] argv)  {

        try {
            running(argv);
        }catch (Exception e){}
    }
}
