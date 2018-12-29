#include<stdio.h>
#include<math.h>
#include<ctype.h>          /* zawiera F_OK itp.   */
#include<unistd.h>        /* zawiera funkcje access(), usleep()  */
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%f",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf(" ")

/* Sprawdzanie, czy wejœciowy plik nie istnieje, jesli nie istnieje zwraca wartosc "TRUE" */
int nieistnieje(const char* nazwa)
{
    return access(nazwa, F_OK);        
}

double f( double x ) 
{ 
    return cos((1.0+x )/(0.04+x*x))*pow(M_E,-(x*x)) ;
} 

double Metoda_Trapezow(double a , double b)
{ 
    return (( b-a ) / 2 )*(f(a)+f(b)); 
} 

double kwadratura_adaptacyjna(double a , double b , double I )
{
    double pom_I , pom_II ; 
    pom_I=Metoda_Trapezow(a,(a+b)/2) ; 
    pom_II=Metoda_Trapezow((a+b)/2,b) ;   	
    if(pom_I+pom_II-I>-0.00000001 && pom_I+pom_II-I < 0.00000001)  return pom_I+pom_II; 
    else  return kwadratura_adaptacyjna(a,(a+b)/2,pom_I)+kwadratura_adaptacyjna((a+b)/2,b,pom_II);
} 


int main () 
{ 
    FILE *fwynik;
    char plik_b[30];
    double x_koniec = 7; 
    double x_poczatek = -7 ; 
    double i ;
    int p1;
    
    wyp(Podaj nazwe pliku wyjsciowego:);
    scanf("%s",  &plik_b[0]);
    while(!nieistnieje(plik_b))
    {
        karetka;
        wyp(Taka nazwa pliku juz istnieje);
        wyp(Wprowadz inna nazwe pliku);
        karetka;
        scanf("%s",  &plik_b[0]);                  
    }
    karetka;   
    fwynik = fopen(plik_b, "w");
    
    for(i=x_poczatek; i<=x_koniec ; i+=0.01)
    {
        printf("%1.2f %f \n",i,kwadratura_adaptacyjna(x_poczatek,i,0));
        fprintf(fwynik, "%1.2f %f \n",i,kwadratura_adaptacyjna(x_poczatek,i,0));
    }
    
    fclose(fwynik);
    
    karetka2;
    p1=0;
    wyp2(Funkcja obliczona i zapisana do pliku o nazwie:);
    space;
    while(plik_b[p1]!='\0')  printf("%c",plik_b[p1++]); 
    karetka2;
    
    wyp2(Dla F(x) : gdzie x dazy do nieskonczonosci =); 
    wypisz(kwadratura_adaptacyjna(x_poczatek,x_koniec,0));
    karetka2;
    
    /*system("pause");*/
    return 0 ; 
} 
