import java.util.Scanner;
import java.io.*;
public class Projekt{
	public static void main(String[] args) throws Exception{
		int[] tab = new int[10];
		int[] pomoc = new int[6];
		int k=0;
		String dopisz = "0";
		String tan;
		int dlugosc;
		int pomo;
		Scanner odczyt;
		outerLoop:
		for(int i=0;i<5;i++){
			do{
				if(i>4){ break outerLoop;}
				System.out.println("Podaj liczbe " + (i+1) +" do posortowania. Zakres od 1 do 10.000 " + tab[0]+" "+tab[1]+" "+tab[2]+" "+tab[3]+" "+tab[4]);
				odczyt = new Scanner(System.in);
				tan = odczyt.nextLine();
				dlugosc = tan.length();
				tan += "koniec";
				for(int j=0;j<=dlugosc;j++){
					try
					{											
													
						dopisz += tan.substring(j, j+1);k = Integer.parseInt(dopisz);
					}
					catch (Exception a)
					{				
						if(dopisz!="")
						{
							try
							{
							tab[i] = k;
							}
							catch(Exception b)
							{
							i=4;
							}
							i++;
							dopisz = "0";
							k=0;
						}
					}				
				}

			for(int p=4;p>=0;p--){
			if(tab[p]<1 || tab[p]>=10000){i=p;}
			}
			}while(tab[i]<1 || tab[i]>=10000);
		}
		System.out.println(tab[0]+" "+tab[1]+" "+tab[2]+" "+tab[3]+" "+tab[4]);
		for(int i=0;i<5;i++){
			for(int j=0;j<4;j++){
				
				if(tab[j]>tab[j+1]){
				pomo=tab[j];
				tab[j]=tab[j+1];
				tab[j+1]=pomo;
				
				}			
			}
		}
		for(int i=0;i<5;i++){
			System.out.print(tab[i]+" ");
		}
	}
}