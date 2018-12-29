/**
 * Created by Kriss on 30.12.2016.
 */


public class Stream {

    public static void main(String[] args) {

        Thread streamNowIs = Thread.currentThread();
        try {
            System.out.println("One moments break ;)");
            streamNowIs.sleep(0);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
            System.out.println("I back ;)");
        }
    }
}





