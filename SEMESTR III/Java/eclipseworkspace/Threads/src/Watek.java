public class Watek implements Runnable{

    protected int no;
    
    public Watek(int i){
        this.no = i;
    }
    
    public void run() {
        for (int i=0; i<5; i++){
            System.out.println("moj numer to " + this.no + ": "+ i);
            try {
                Thread.sleep((long)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

}