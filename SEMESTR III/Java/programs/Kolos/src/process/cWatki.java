package process;


public class cWatki extends Thread{

    public cWatki(String name){
        super(name);
    }

    public synchronized void run(){
        System.out.print(" " + getName() + " ");
        try{
            sleep(100);

        }catch (InterruptedException e){}
    }

    public static void main(String[] args){
        int watek = Thread.activeCount();
        Thread th1 = new cWatki("bla");
        Thread th2 = new cWatki("ozicx");
        Thread th3 = new cWatki("zxczxc");

        th1.start();
        th2.start();
        th3.start();

        th1.start();
        th2.start();
        th3.start();
        int j = 0;
        for(int i=watek ; i < Thread.activeCount(); )
            j++;


        //System.out.println(Thread.activeCount());

    }

}
