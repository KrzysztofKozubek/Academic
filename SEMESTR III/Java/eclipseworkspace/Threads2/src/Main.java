
public class Main {
	 public static void main(String[] args) throws InterruptedException {
	        for(int i=0; i<5; i++){

	//          Watek w = new Watek(i);
	//          w.run();

	            Thread t = new Thread(new Watek3(i));
	            t.start();
	            t.join();
	        }
	    }
}
