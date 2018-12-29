import java.util.*;

public class a{
	public static void main(String[] args){
		int[][] tab = new int[1][5];
		int dodaj = 0;
		for(int i=0; i < tab.length;i++){
			for(int j=0; j < tab[i].length;j++){
			System.out.println("tab["+i+"]["+j+"] = " + dodaj);
			tab[i][j]=dodaj;
			dodaj++;
			}		
		}
		for(int[] wiersz: tab)
			for(int element: wiersz){
			System.out.println(element);
			}
	}
}
