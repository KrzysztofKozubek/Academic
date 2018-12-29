#include<stdio.h>
#include<math.h>   
#include<ctype.h>          /* zawiera F_OK itp.   */
#include<unistd.h>        /* zawiera funkcje access(), usleep()  */
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%9.5f",a)
#define wypisz2(a) printf("%i",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf("  ")

/* Sprawdzanie, czy wejœciowy plik nie istnieje, jesli nie istnieje zwraca wartosc "TRUE" */
int nieistnieje(const char* nazwa)
{
    return access(nazwa, F_OK);        
}

double bifurkacje(double x, double k)
{
		return k*x*(1 - x);
}

int main(void)
{
    FILE *fwynik;
    char plik_b[30];
    double x = 0.64;
    double p1;
    int i, d;
    
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
    for(p1 = 2; p1 <= 4.04; p1 += 0.04) 
    {
        for (i = 0; i < 100; i++)
        {
            x = bifurkacje(x, p1);
        }
        
        for(i = 0; i < 500; i++)
        {
            x = bifurkacje(x, p1);
            fprintf(fwynik,"%2.2f %2.12f\n", p1 ,x);
            printf("%2.2f %2.12f\n", p1 ,x);
        }
    }
    fclose(fwynik);
    
    karetka2;
    d=0;
    wyp2(Wynik obliczony i zapisany jako:);
    space;
    while(plik_b[d]!='\0')  printf("%c",plik_b[d++]); 
    karetka2;
    /*system("pause");*/
    return 0;
}


