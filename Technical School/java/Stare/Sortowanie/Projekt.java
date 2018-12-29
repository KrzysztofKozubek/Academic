import java.util.Scanner;
import java.io.*;
public class Projekt{
	public static void main(String[] args) throws Exception{
		int[] tab = new int[6];
		int pomoc;
		Scanner odczyt;
		
		for(int i=0;i<5;i++){
			do{
			
				System.out.println("Podaj liczbe " + (i+1) +" do posortowania. Zakres od 1 do 10.000");
				odczyt = new Scanner(System.in);
				
				
				try
				{				
					tab[i] = odczyt.nextInt();
				}
				catch (Exception e)
				{
					tab[i]=0;
				}
			
			}while(tab[i]<1 || tab[i]>=10000);
		}
		
		for(int i=0;i<5;i++){
			for(int j=0;j<4;j++){
				
				if(tab[j]>tab[j+1]){
				pomoc=tab[j];
				tab[j]=tab[j+1];
				tab[j+1]=pomoc;
				
				}			
			}
		}
		for(int i=0;i<5;i++){
			System.out.print(tab[i]+" ");
		}
	}
}