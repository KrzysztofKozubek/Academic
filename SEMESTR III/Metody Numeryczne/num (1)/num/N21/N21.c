#include<math.h>
#include<stdio.h>
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%.8f",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf(" ")

double f( double x ) 
{ 
    return sin( (1.0+pow(x,0.5) )/(1.0+x*x))*pow(M_E,-x) ;
} 

double rekurencja(double kolumna ,double R_biezacy , double R_poprzedni ) 
{
    return (pow(4.0, kolumna+1.0)*R_biezacy - R_poprzedni)/(pow(4, kolumna+1.0)-1.0);
}
/* b > a */
double Romb(int i ,double a , double b  )
{ 
    double k;
    double h_i = (b-a)/pow(2.0,i);
    double wynik = 0 ; 
    for(k = 0 ; k < pow(2.0,i)-1.0 ; k++ )
    {
        wynik = wynik + (f(a+k*h_i)+f(a+(k+1)*h_i))/2.0 ; 
    }
    wynik = h_i*wynik ; 
    return wynik ; 
}

int main () 
{ 
    double a = 0.0 , b = 17.0 ;
    double *tab_poprzedni;
    int k, i=0; 
    double last = 1 ; 
    while(i<20) 
    {
        double *tab_nowy;
        tab_nowy = malloc(sizeof *tab_nowy * (i+1));
        if ( i == 0 )
        {
            tab_nowy[i] = Romb(1,a,b);
        }
        else 
        {
            for(k =0 ;k<i ;k++)
            {
                if ( k == 0 )
                {
                    tab_nowy[0] = Romb( i ,a ,b ); 
                }
                else 
                {
                    tab_nowy[k] = rekurencja(k-1,tab_nowy[k-1],tab_poprzedni[k-1]); 
                } 
            }
            if((i>2) && ((tab_nowy[i-1]-last)< 0.0000001) &&  ((tab_nowy[i-1]-last)> -0.0000001))
            {
            wyp(Wynik calkowania);
            wypisz(tab_nowy[i-1]); 
            karetka2;
            break; 
            }
        }
        last = tab_nowy[i-1] ; 
        tab_poprzedni=tab_nowy;
        i++ ; 
    } 
    /*system("pause"); */
    return 0 ; 
} 

