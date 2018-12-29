#include<stdio.h>
#include<ctype.h>          /* zawiera F_OK itp.   */
#include<unistd.h>        /* zawiera funkcje access(), usleep()  */
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%lf",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf(" ")

/* Sprawdzanie, czy wejœciowy plik nie istnieje, jesli nie istnieje zwraca wartosc "TRUE" */
int nieistnieje(const char* nazwa)
{
    return access(nazwa, F_OK);        
}

int main()
{
    FILE *fpunkty, *fwynik;
    char plik_a[30];
    char plik_b[30];
    
    double x[8] = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7};
    double y[8] = {-7.43239, -6.93795, -5.99228, -5.95172, -4.83701, -4.67837, -4.05978, -3.00445};  
    double n = 8;
    double a, b, Y, f;
    double suma1 = 0;
    double suma2 = 0;
    double suma3 = 0;
    double suma4 = 0;
    int i;
    
    wyp(Podaj nazwe pliku wyjsciowego dla punktow wejsciowych:);
    scanf("%s",  &plik_a[0]);
    while(!nieistnieje(plik_a))
    {
        karetka;
        wyp(Taka nazwa pliku juz istnieje);
        wyp(Wprowadz inna nazwe pliku);
        karetka;
        scanf("%s",  &plik_a[0]);                  
    }
    karetka;wyp(Podaj nazwe pliku wyjsciowego dla prostej:);
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
    
    fpunkty = fopen(plik_a, "w");
    
    for(i = 0; i < n; i++)
    {
        fprintf(fpunkty,"%1.1f %f\n", x[i],y[i]);
    }
        
    
    for(i = 0; i < n; i++)
    {
        suma1 += x[i]*y[i];
        suma2 += x[i];
        suma3 += y[i];
        suma4 += x[i]*x[i];
    }
    
    a = ( n*suma1 - suma2*suma3 )/( n*suma4 - suma2*suma2 );
    b = ( suma3 - a*suma2)/n; 
    
    fwynik = fopen(plik_b, "w");
    
    Y = 0;
    for(f = 0; f < 0.7; f += 0.01)
    {
        Y = a*f + b;
        fprintf(fwynik,"%1.2f %f\n", f,Y);
        printf("%1.2f %f\n", f,Y);
    }
    /*system("pause");*/
    fclose(fwynik);
    fclose(fpunkty);
    return 0;   
}
