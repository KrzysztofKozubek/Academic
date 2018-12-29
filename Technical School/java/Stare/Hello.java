public class Hello{
	public static void main(String[] args){
		double a=1;
		double b=2;
		double c=3;
		System.out.println((a+b)*c);
		System.out.println(a-b/c);
		a++;
		b++;
		c++;
		boolean sprawdz1 = (a+b)>c;
		boolean sprawdz2 = a==b;
		System.out.println("sprawdz (a+b)>c"+ sprawdz1);
		System.out.println("sprawdz czy a=b"+ sprawdz2);
		
	}

}
