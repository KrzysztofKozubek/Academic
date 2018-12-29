
public class SquareRoots {
	public static double[] getRoots(double a, double b, double c)
	{
	    double[] roots = new double[3];
	    double delta = b*b-4*a*c;
	    if (delta<0){
	        roots[0] = 0;
	        return roots;
	    }else{
	        roots[1] = (-b+Math.sqrt(delta))/(2*a);
	        roots[2] = (-b-Math.sqrt(delta))/(2*a);
	        roots[0] = (delta==0)?1:2;
	    }
	    return roots;

	}
	
	public static void main(String[] args)
	{
	    double a=Integer.parseInt(args[0]);
	    if(a==0) System.out.println("Nieprawid\u0142owe dane");
	    double b=Integer.parseInt(args[1]);
	    double c=Integer.parseInt(args[2]);
	    double[] results = getRoots(a, b, c);
	    String[] sa = {"Liczba rzeczywistych pierwiastk\u00f3w: ",
	                   "x1 = ", "x2 = "};
	    for(int i=0; i<results[0]+1; i++)
	        System.out.println(sa[i] + results[i]);
	}
}

