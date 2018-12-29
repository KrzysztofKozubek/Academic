public class Watek3 implements Runnable{

    protected int no;

    public Watek3(int i) {
        this.no = i;
    }

    public void run(){
        int a = (int) (Math.random()*10);
        Petelka.setA(a);
        int b = (int) (Math.random()*10);
        Petelka.setB(b);
        System.out.println(a + " + " + b + " = " + Petelka.dodajAB());
        
    }
}