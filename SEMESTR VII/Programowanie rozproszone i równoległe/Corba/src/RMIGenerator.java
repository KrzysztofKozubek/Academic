
public interface RMIGenerator extends java.rmi.Remote {
/**
   Zwraca liczbe watkow, ktora powinna zostac uzyta do 
   generowania liczb.
   @return liczba watkow, ktora powinna zostac uzyta przez klienta
*/
   public int getThreads() throws java.rmi.RemoteException;
/**
   Metoda zwraca wygenerowana liczbe.
   @return wygenerowan przez metode liba
*/
   public int getValue() throws java.rmi.RemoteException;   
}
