public class Helper {
/**
   Metoda zwraca referencje do serwisu RMI
   @return referencja do namiastki reprezentujacej serwis RMI 
*/
   public static RMIGenerator getGenerator( String url ) {
      try {
         return (RMIGenerator)java.rmi.Naming.lookup(url);
      }
      catch ( Exception e ) {
         System.err.println( "W trakcie pracy metody getGenerator doszlo do wyjatku"+e.getMessage() );
         return null;
      }
   }
}
