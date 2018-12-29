import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kriss on 16.11.2016.
 */

public class Test {

    Vector<Boolean> v = new Vector<>();


    public static void foo() {

        Random random = new Random();
        int tmp = 0;
        for (int i = 0; i < 100000; i++) {
            tmp = random.nextInt();
            tmp %= 100;
        }
    }


    public static void main(String args[]) throws Exception {

        int nDisksRW = 10000;
        long start, stop;


        start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(nDisksRW + 1);
        Future<Integer>[] future = new Future[nDisksRW + 1];
        Callable<Integer>[] task = new Callable[nDisksRW + 1];
        for (int i = 0; i <= nDisksRW; i++) {
            final int tmp = i;
            task[i] = () -> {
                foo();
                return 0;
            };
            future[i] = executor.submit(task[i]);
        }

        for (int i = 0; i <= nDisksRW; i++) {
            try {
                future[i].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        stop = System.currentTimeMillis();
        System.out.println("Czas wykonania:" + (stop - start));




        start = System.currentTimeMillis();
        AtomicInteger[] values = new AtomicInteger[nDisksRW + 1];
        Thread[] t = new Thread[nDisksRW + 1];

        for (int i = 0; i <= nDisksRW; i++) {
            AtomicInteger AII = new AtomicInteger(i);

            t[i] = new Thread(new Runnable() {
                public void run() {
                    int i = AII.get();
                    foo();
                    values[i] = new AtomicInteger(0);
                }
            });
            t[i].start();
        }

        int sum = 0;
        for (int i = 0; i <= nDisksRW; i++) {
            try {
                t[i].join();
                sum += values[i].get();
            } catch (InterruptedException e) {
            }
        }
        stop = System.currentTimeMillis();
        System.out.println("Czas wykonania:" + (stop - start));




//        Random random = new Random();
//        int modul = 10;
//        System.out.println();
//        for (int i = 0; i < 5; i++) {
//            int tmp = Math.abs(random.nextInt() % 100);
//            System.out.println(tmp + " " + (tmp & modul) + " " + tmp % modul);
//        }
//        System.out.println();
//
//
//        System.out.println(0 / 3);
//        Test t = new Test();
//        Thread thread = new Thread(new A());
//        thread.start();
//        System.out.println("Jestem");
//        thread.notifyAll();
    }
}


class A implements Runnable {


    public A() {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("watek");

    }
}

class B implements Callable<Integer> {

    public B() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future future = executorService.submit(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });

        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}