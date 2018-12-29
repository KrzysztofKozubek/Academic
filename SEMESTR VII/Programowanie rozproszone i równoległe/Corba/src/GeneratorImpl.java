import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Float.floatToIntBits;
import static java.lang.Float.intBitsToFloat;

/**
 * Created by Piotr on 2016-12-27.
 */

class GeneratorImpl extends GeneratorPOA {
    private final Map<Integer, RMIService> userToService = new HashMap<>();
    private final AtomicInteger userIdGenerator = new AtomicInteger(1);
    private final AtomicInteger orderIdGenerator = new AtomicInteger(1);
    private final List<Order> orders = new LinkedList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void register(String rmiServiceURL, IntHolder userID) {
        final int userId = userIdGenerator.getAndIncrement();
        RMIService rmiService = getService(rmiServiceURL);
        if (rmiService == null) {
            RMIGenerator rmiGenerator = Helper.getGenerator(rmiServiceURL);
            int threadCount = 0;
            try {
                if (rmiGenerator != null) {
                    threadCount = rmiGenerator.getThreads();
                }
            } catch (RemoteException e) {
                System.out.println("Blad w trakcie rejestracji RMIGenerator");
                e.printStackTrace();
            }
            rmiService = new RMIService(rmiServiceURL, rmiGenerator, threadCount);
        }
        synchronized (userToService) {
            userToService.put(userId, rmiService);
        }
        userID.value = userId;
    }

    @Override
    public void order(int userID, int averageOver, IntHolder orderID) {
        final int orderId = orderIdGenerator.getAndIncrement();
        Order order = new Order(orderId);
        ReadingTask readingTask = new ReadingTask(order, userID, averageOver);
        synchronized (orders) {
            orders.add(order);
        }
        executorService.submit(readingTask);
        orderID.value = orderId;
    }

    @Override
    public boolean isReady(int orderID) {
        synchronized (orders) {
            for (Order order : orders) {
                if (order.getId() == orderID) {
                    return order.isReady();
                }
            }
        }
        return false;
    }

    @Override
    public float getAverage(int orderID) {
        synchronized (orders) {
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                Order order = iterator.next();
                if (order.getId() == orderID && order.isReady()) {
                    iterator.remove();
                    return order.getValue();
                }
            }
        }
        return 0;
    }

    private RMIService getService(String rmiServiceURL) {
        synchronized (userToService) {
            for (RMIService rmiService : userToService.values()) {
                if (rmiServiceURL.equals(rmiService.getUrl())) {
                    return rmiService;
                }
            }
        }
        return null;
    }

    private class RMIService {
        private final String url;
        private final ExecutorService executorService;
        private final RMIGenerator rmiGenerator;

        RMIService(String url, RMIGenerator rmiGenerator, int threadCount) {
            this.url = url;
            this.rmiGenerator = rmiGenerator;
            this.executorService = Executors.newFixedThreadPool(threadCount);
        }

        String getUrl() {
            return url;
        }

        Future<Integer> getFutureValue() {
            return executorService.submit(rmiGenerator::getValue);
        }

    }

    private class ReadingTask implements Callable<Boolean> {
        private final Order order;
        private final int userID;
        private final int averageOver;

        ReadingTask(Order order, int userID, int averageOver) {
            this.order = order;
            this.userID = userID;
            this.averageOver = averageOver;
        }

        @Override
        public Boolean call() throws Exception {
            RMIService rmiService;
            synchronized (userToService) {
                rmiService = userToService.get(userID);
            }
            int sum = 0;
            for (int i = 0; i < averageOver; i++) {
                Future<Integer> task = rmiService.getFutureValue();
                sum += task.get();
            }
            float average = sum / averageOver;
            synchronized (order) {
                order.setResult(average);
            }
            return true;
        }
    }

    private class Order {
        private final int id;
        private final AtomicBoolean isReady = new AtomicBoolean(false);
        private final AtomicInteger value = new AtomicInteger(0);

        Order(int id) {
            this.id = id;
        }

        private boolean isReady() {
            return isReady.get();
        }

        private void setResult(float value) {
            this.value.set(floatToIntBits(value));
            this.isReady.set(true);
        }

        private float getValue() {
            return intBitsToFloat(this.value.get());
        }

        int getId() {
            return id;
        }
    }
}

class Start {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = (POA) orb.resolve_initial_references("RootPOA");
            rootpoa.the_POAManager().activate();

            GeneratorImpl gimpl = new GeneratorImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(gimpl);
            System.out.println(orb.object_to_string(ref));
            org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
            NamingContext nCont = NamingContextHelper.narrow(namingContextObj);
            NameComponent[] path = {
                    new NameComponent("Generator", "Object")
            };

            nCont.rebind(path, ref);
            orb.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
