#include<stdio.h>
#include<ctype.h>          /* zawiera F_OK itp.   */
#include<unistd.h>        /* zawiera funkcje access(), usleep()  */
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%lf",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf(" ")
#define N 100

/* Sprawdzanie, czy wejœciowy plik nie istnieje, jesli nie istnieje zwraca wartosc "TRUE" */
int nieistnieje(const char* nazwa)
{
    return access(nazwa, F_OK);        
}

void lu(double X[N+1][1], double A[N+1][N+1], double b[N+1][1])
{
    int p1, p2 ,p3;
    double Z[N+1][1];
    double L[N+1][N+1];
    double U[N+1][N+1];
    double suma_pomoc_1 = 0;
    double suma_pomoc_2 = 0;
    
    /* wypelniam macierz zerami jedynkami do LU */
    for(p1 = 0; p1<N+1; p1++)
    {
        for(p2 = 0; p2<N+1; p2++)
        {
            if(p1==p2) L[p1][p2] = 1;
            else L[p1][p2] = 0;
            U[p1][p2] = 0;
        }
    }
    

    
    for(p1 = 0; p1<N+1; p1++)
    {
        for(p2 = p1; p2<N+1; p2++)
            {
            for(p3 = 0; p3 <= p1-1; p3++)
            {
                suma_pomoc_1 += L[p1][p3]*U[p3][p2];
                suma_pomoc_2 += L[p2][p3]*U[p3][p1];
            }
            U[p1][p2] = A[p1][p2] - suma_pomoc_1;
            
            if(p2>= p1+1)
            {
                L[p2][p1] = (A[p2][p1] - suma_pomoc_2)/U[p1][p1];   
            }
            suma_pomoc_1 = 0;
            suma_pomoc_2 = 0;
        }
    }
    
    suma_pomoc_1 = 0;
    
    for(p1 = 0; p1 < N+1; p1++)
    {
        for(p2 = 0; p2 < p1+1; p2++)
        {
            if( p1 != p2)
            {
                suma_pomoc_1 += L[p1][p2]*Z[p2][0];  
            }   
        } 
        Z[p1][0] =  (b[p1][0] - suma_pomoc_1)/L[p1][p1]; 
        suma_pomoc_1 = 0;
    }
    
    suma_pomoc_2 = 0;
    
    for(p1 = N+1; p1 >= 0; p1--)
    {
        for(p2 = p1; p2 <N+1; p2++)
        {
            if( p1 != p2)
            {
                suma_pomoc_2 += U[p1][p2]*X[p2][0];  
            }   
        } 
        X[p1][0] =  (Z[p1][0] - suma_pomoc_2)/U[p1][p1]; 
        suma_pomoc_2 = 0;
    }
 
}


int main(void)
{
    FILE *fwynik;
    char plik_b[30];
    int d, p1, p2;
    double h = 0.01;
    double X[N+1][1];
    double A[N+1][N+1];
    double b[N+1][1];
    
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

    /* tworzenie zadanej macierzy */
    for(p1 = 0; p1<N+1; p1++)
    {
        for(p2 = 0; p2<N+1; p2++)
        {
            if((p1 == 0 && p2 == 0)||((p1==N) && (p2 == N)))
            {
                A[p1][p2] = 1;
            }
            else
            {
                if(p1 == p2-1)
                {
                    A[p1][p2] = 1;   
                }
                else if( p1 == p2 )
                {
                    A[p1][p2] = -2;   
                }
                else if(p1 == p2+1)
                {
                    A[p1][p2] = 1;   
                }
                else A[p1][p2] = 0;
            }
        }   
    }
    A[0][1] = 0;
    A[100][99] = 0;
    
    /* tworzenie wektora wyrazow wolnych z zadania */
    for(p1 = 0; p1<N+1; p1++)
    {
        if(p1 == 0) 
        {
            b[p1][0] = 0;
        }
        else if( p1 == N)
        {
            b[p1][0] = 1;   
        }
        else
        {
            b[p1][0] =  -2*h*h;
        }
    }

    lu(X, A, b);

    fwynik = fopen(plik_b, "w");
    for(p1 = 0; p1 < N+1; p1++)
    {
        fprintf(fwynik,"x%i = %f \n", p1+1 ,X[p1][0]);
        printf("x%i = %f", p1+1, X[p1][0]);
        karetka;
    }  
    fclose(fwynik);
    
    karetka2;
    d=0;
    wyp2(Wynik obliczony i zapisany jako:);
    space;
    while(plik_b[d]!='\0')  printf("%c",plik_b[d++]); 
    karetka2;
    
    return 0; 
       
}

