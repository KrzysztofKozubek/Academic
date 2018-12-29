public class Petelka {
    static int a, b;
        
    public static void setA(int i){ a = i;}
    public static void setB(int i){ b = i;}
    public static int dodajAB(){ return (a + b);}
    
    public static void zrobPetelke(int no, int length) throws InterruptedException{
        for(int i=0; i<length; i++){
            System.out.println("petelka " + no + ": " + i);
            Thread.sleep((long)(1000*Math.random()));
        }
    }
    
    synchronized public static void zrobPetelkeSyn(int no, int length) throws InterruptedException{
        for(int i=0; i<length; i++){
            System.out.println("petelka " + no + ": " + i);
            Thread.sleep((long)(1000*Math.random()));
        }
    }


}