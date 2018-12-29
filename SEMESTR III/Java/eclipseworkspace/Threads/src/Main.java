
public class Main {
	public static void main(String[] args){
        for(int i=0; i<5; i++){

         Watek w = new Watek(i); //tworzy watek
         w.run();

          //  Thread t = new Thread(new Watek(i));  /dzialaja wsplobieznie
           // t.start();
        }
    }
}
