import java.util.Scanner;
import java.io.*;
public class Projekt{
	public static void main(String[] args) throws Exception{
		int[] tab = new int[10];		
		int pomo;	
		int blad = 0;
		for(int i=0;i<5;i++){
			
			try{
				tab[i]=Integer.parseInt(args[i]);
			}
			catch(Exception a)
			{
				tab[i]=0;
			}

			if(tab[i]<1 || tab[i]>10000){blad=1;}
		}
		if(blad==0){
			//Sortowanie
			for(int i=0;i<5;i++){
				for(int j=0;j<4;j++){
					
					if(tab[j]>tab[j+1]){
					pomo=tab[j];
					tab[j]=tab[j+1];
					tab[j+1]=pomo;
					
					}			
				}
			}
			
			//Wyswietlenie liczb posortowanych
			for(int i=0;i<5;i++){
				System.out.print(tab[i]+" ");
			}
			
		}else{System.out.println("Podales zle liczby");}
	}
}