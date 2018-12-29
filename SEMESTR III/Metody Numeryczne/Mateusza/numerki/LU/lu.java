//dzialajaca wersja dla macierzy 3x3, wersja podstawowa
import java.util.Scanner;

class LU{
double [][]tab=new double[3][3];//macierz A

double [][] L=new double[3][3];//macierz L
double [][] U=new double[3][3];//macierz U

public void doA(){ //ustawiam rozmiar/y macierzy

for(int i=0;i<tab.length; i++){
	for(int j=0;j<tab[i].length;j++){
		tab[i][j]=0;		
				}
		}
	}

	
public void Load(){ //wczytuje liczby do macierzy
Scanner load=new Scanner(System.in);
double lap;
for(int i=0;i<tab.length; i++)
	for(int j=0;j<tab[i].length;j++)
tab[i][j]= load.nextDouble();
			
		}

public void showA(){ //wyswietlam macierz A
for(int i=0;i<tab.length; i++){
System.out.println("");
	for(int j=0;j<tab[i].length;j++){
	System.out.print("|"+tab[i][j]+"|");	
									}
System.out.print("\n ");
				}
				
			}

public void showL(){ //wyswietlam macierz L
System.out.println("macierz L:");
for(int i=0;i<L.length; i++){
System.out.println("");
	for(int j=0;j<L[i].length;j++){
	System.out.print("|"+L[i][j]+"|");	
									}
System.out.print("\n ");
				}	
}

	
public void showU(){ //wyswietlam macierz U
System.out.println("macierz U:");
for(int i=0;i<U.length; i++){
System.out.println("");
	for(int j=0;j<U[i].length;j++){
	System.out.print("|"+U[i][j]+"|");	
									}
System.out.print("\n ");
				}	
}
	
public double determinant(){
double det=0;
double q=0;
double w=0;
q=tab[0][0]*tab[1][1]*tab[2][2]+tab[0][1]*tab[1][2]*tab[2][0]+tab[0][2]*tab[1][0]*tab[2][1];
w=-tab[0][2]*tab[1][2]*tab[2][1]-tab[0][0]*tab[1][2]*tab[2][1]-tab[0][1]*tab[1][0]*tab[2][2];

det=w+q;
return det;

	}			
			
public void LUd(){
U[0][0]=tab[0][0];
L[1][0]=tab[1][0]/U[0][0];
L[2][0]=tab[2][0]/U[0][0];


U[0][1]=tab[0][1];
U[1][1]=tab[1][1]-L[1][0]*U[0][1];
L[2][1]=(tab[2][1]-L[2][0]*U[0][1])/U[1][1];
if(L[2][1]<1&&L[2][1]!=0){
L[2][1]=Math.round(L[2][1]*100); //zaokraglam w GORE!
L[2][1]/=100; }


U[0][2]=tab[0][2];
U[1][2]=tab[1][2]-L[1][0]*U[0][2];
U[2][2]=tab[2][2]-L[2][0]*U[0][2]-L[2][1]*U[1][2];

L[0][0]=1;
L[1][1]=1;
L[2][2]=1;


	}			
			
			
			
			
			
}

class Start{
public static void main(String[] args){

LU A=new LU();

A.doA();
A.Load();
A.showA();
System.out.println(A.determinant());
A.LUd();

A.showL();

A.showU();



	}
}