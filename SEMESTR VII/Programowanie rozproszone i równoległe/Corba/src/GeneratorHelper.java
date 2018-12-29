
/**
* GeneratorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Generator.idl
* czwartek, 5 stycznia 2017 23:36:28 CET
*/

abstract public class GeneratorHelper
{
  private static String  _id = "IDL:Generator:1.0";

  public static void insert (org.omg.CORBA.Any a, Generator that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Generator extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (GeneratorHelper.id (), "Generator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Generator read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_GeneratorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Generator value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Generator narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Generator)
      return (Generator)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GeneratorStub stub = new _GeneratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Generator unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Generator)
      return (Generator)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GeneratorStub stub = new _GeneratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
