public class Watek2 extends Watek3{

    public Watek2(int i) {
        super(i);
    }

    public void run() {
        try {
//            Petelka.zrobPetelke(this.no, 5);
            Petelka.zrobPetelkeSyn(this.no, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }        
    }
}