
/**
* GeneratorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Generator.idl
* środa, 28 grudnia 2016 10:10:39 CET
*/

public final class GeneratorHolder implements org.omg.CORBA.portable.Streamable
{
  public Generator value = null;

  public GeneratorHolder ()
  {
  }

  public GeneratorHolder (Generator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = GeneratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    GeneratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return GeneratorHelper.type ();
  }

}
