import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FieldExample {

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class<?> c;
        try {
            c = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.out.println("Nie ma takiej klasy");
            return;
        }
        Field[] fa = c.getFields();
        for(Field f: fa){
            System.out.print(Modifier.toString(f.getModifiers()) + "\t" + f.getType() + "\t" + f.getName());
            if (Modifier.isStatic(f.getModifiers())){
                System.out.println(" = " + f.get(null));
            }else{
                System.out.println();
            }
        }
        
        Method[] ma = c.getMethods();
        for(Method m: ma){
            System.out.print(Modifier.toString(m.getModifiers()) +"\t" + m.getReturnType() + "\t" + m.getName() + "(");
            Class[] ca = m.getParameterTypes();
            for(Class c1 : ca){
                System.out.print(c1.getName() + " ");
            }
            System.out.println(")");
        }
        
        System.out.println(ma[5].invoke(null, Math.PI));

    }
}
