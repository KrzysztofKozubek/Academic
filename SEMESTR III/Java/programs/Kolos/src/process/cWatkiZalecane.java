package process;

/**
 * Created by kris_ on 2015-01-18.
 */
public class cWatkiZalecane implements Runnable {

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.print("WATEK");
    }

    public static void main(String[] args) {
        Thread th = new Thread(new cWatkiZalecane());
        th.start();
        th.interrupt();
        

        System.out.println(" " + th.getState() + " " + th.getState() + " ");
        if (th.getState() == Thread.State.NEW) {
            th.start();
        }


    }
}
