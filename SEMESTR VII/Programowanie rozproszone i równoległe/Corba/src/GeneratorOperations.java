
/**
* GeneratorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Generator.idl
* czwartek, 5 stycznia 2017 23:36:28 CET
*/

public interface GeneratorOperations 
{
  void register (String rmiServiceURL, org.omg.CORBA.IntHolder userID);
  void order (int userID, int averageOver, org.omg.CORBA.IntHolder orderID);
  boolean isReady (int orderID);
  float getAverage (int orderID);
} // interface GeneratorOperations
