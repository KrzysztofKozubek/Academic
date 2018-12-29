
public class Main {
    
    public static void main(String[] args) throws InterruptedException{
        for(int i=0; i<5; i++){
            Thread t = new Thread(new Watek2(i));
            t.start();
        }
    }

}