import java.util.Scanner;
class LU{
private boolean blok=false;
//tworze miejsce na macierze
double[][] macierz=new double[3][3];
double[][] L=new double[3][3];
double[][] U=new double[3][3];
double sum;

//sprawdzam, czy moja macierz jest kwadratowa, czy tez nie :C
public boolean czykwadrat(double[][] m) {
      if (macierz[0].length == m.length) {
	  return true;
      } else {
	  return false;
      }
  }
  
//teraz robie refaktoryzacje metoda Doolitle'a
void metodka(){
for(int k=1;k<=3;k++){
        L[k][k]=1;
        for(int i=k;i<=3;i++)
        {
            sum=0;
            for(int p=1;p<=k-1;p++){
                sum=+L[i][p]*U[p][k];
            L[i][k]=macierz[i][k]-sum;}
        }

        for(int j=k+1;j<=3;j++)
        {
            sum=0;
            for(int p=1;p<=k-1;p++){
                sum+=L[k][p]*U[p][j];
            U[k][j]=(macierz[k][j]-sum)/L[k][k];}
        }
    }
	
	for(int i=1;i<=3;i++)
    {
        for(int j=1;j<=3;j++)
            System.out.print(+L[i][j]);
    }
	System.out.println();
	
    for(int i=1;i<=3;i++)
    {
        for(int j=1;j<=3;j++)
            System.out.print(+U[i][j]);
        
    }
	System.out.println();
}

public void Load(){ //wczytuje liczby do macierzy
System.out.println("podaj 9 elementow macierzy!(wierszami, moga byc wszystkie naraz )");
Scanner load=new Scanner(System.in);
double lap;
for(int i=0;i<macierz.length; i++){
	for(int j=0;j<macierz[i].length;j++){
macierz[i][j]= load.nextDouble(); 
	if(macierz[i][j]==0) blok=true;
		}
	}
			
		}
		
		
public void showA(){ //wyswietlam macierz A
for(int i=0;i<macierz.length; i++){
System.out.println("");
	for(int j=0;j<macierz[i].length;j++){
	System.out.print("|"+macierz[i][j]+"|");	
									}
System.out.print("\n ");
				}
				
			}
	public void showL(){ //wyswietlam macierz L
if(blok==false){
System.out.println("macierz L:");
for(int i=0;i<L.length; i++){
System.out.println("");
	for(int j=0;j<L[i].length;j++){
	System.out.print("|"+L[i][j]+"|");	
									}
System.out.print("\n ");
				}	
			}
else System.out.println("Pokaz L: Opcja niedostepna");			
			
}

	
public void showU(){ //wyswietlam macierz U
if(blok==false){
System.out.println("macierz U:");
for(int i=0;i<U.length; i++){
System.out.println("");
	for(int j=0;j<U[i].length;j++){
	System.out.print("|"+U[i][j]+"|");	
									}
System.out.print("\n ");
				}	
			}
else System.out.println("Pokaz U: Opcja niedostepna");		
			}


}

class LUS{
public static void main(String[] args){
LU s=new LU();
s.Load();
s.showA();
s.metodka();
s.showL();
s.showU();

} }