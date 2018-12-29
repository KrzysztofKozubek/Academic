public class Watek3 implements Runnable{
    
    private Object lock;

    public Watek3(Object lock) {
        this.lock = lock;
    }

    public void run(){
    	 int a = (int) (Math.random()*10);
    	 int b = (int) (Math.random()*10);
    	 	synchronized(this.lock){
    	 		Petelka.setA(a);
    	 		Petelka.setB(b);
    	 		System.out.println(a + " + " + b + " = " + Petelka.dodajAB());
    	 	}
    }
}