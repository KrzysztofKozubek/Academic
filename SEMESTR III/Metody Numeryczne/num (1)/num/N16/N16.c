#include<stdio.h>
#include<math.h>
#include<stdlib.h>
#include<time.h>
#include<ctype.h>          /* zawiera F_OK itp.   */
#include<unistd.h>        /* zawiera funkcje access(), usleep()  */
#define wyp(a) printf(#a "\n")
#define wyp2(a) printf(#a)
#define wypisz(a) printf("%.16f",a)
#define wypisz2(a) printf("%d",a)
#define wypisz3(a) printf("%i",a)
#define karetka printf("\n")
#define karetka2 printf("\n\n")
#define space printf(" ")

double losuj()
{
    return (double)(((rand())% 9999)+1) /10000.0;
}

double f( double x )
{
    return (pow(x,2.0)-1)*pow(sinh(x),3.0);
}

double interpolacja_lagrangea(double x[], double y[], double x0 )
{
    double t;
    double wynik = 0.0;
    int k , i ;
    for( k = 0; k< 3; k++)
    {
        t = 1.0;
        for(i = 0; i < 3 ; i++)
        {
            if(i != k )
            {
                t = t*((x0-x[i])/(x[k]-x[i]));
            }
        }
        wynik += t*y[k];
    }
    return wynik;
}

double metoda_siecznych ( double x_1 , double x_2 , double eps)
{
    double f1 ,f0 ,f2 , x1=x_1 , x2=x_2 , x0;
    int i ,k=0;
    f1 = f(x1);
    f2 = f(x2);
    i = 64;
    
    while(i && ((fabs(x1 - x2) > eps)))
    {
        if(f1 - f2 == 0)
        {
            wyp(Zle punkty startowe);
            i = 0;
            break;
        }
        x0 = x1 - f1 * (x1 - x2) / (f1 - f2);
        f0 = f(x0);
        if(fabs(f0) < eps)
        {
            break;
        }
        x2 = x1; f2 = f1;
        x1 = x0; f1 = f0;
        if(!(--i))
        {
            wyp(Przekroczony limit obiegow);
        }
        k++ ;
    }
    
    karetka;
    wyp2(Metoda siecznych: iteracja);
    space;
    wypisz2(k);
    karetka;
    return x0;
}

double odwrocona_interpolacja(double a , double b , double c ,double epsylon )
{
    int i = 0;
    double x0 = 100;
    while(fabs(f(x0)) > epsylon)
    {
        double x[4];
        double y[4];
        x[0] = a;
        x[1] = b;
        x[2] = c;
        y[0] = f(a);
        y[1] = f(b);
        y[2] = f(c);
        
        x0 = (interpolacja_lagrangea(y,x,0.0));
        a = b;
        b = c;
        c = x0;
        i++;
    }
    
    karetka;
    wyp2(Odwrocona interpolacja: iteracja);
    space;
    wypisz2(i);
    karetka;
    return x0;
}


int main()
{
    int i;
    const double ypsylon = 0.00000001 ;
    double x0 , x1 , x2 ;
    srand(time(0));
    i = 0;
    while(i < 16)
    {
        wypisz3(i+1);
        space;
        wyp(LOSOWANIE);
        x0 = losuj () ;
        x1 = losuj () ;
        x2 = losuj () ;
        if ( x0 == x1 )
        {
             continue;
        }
        karetka2;
        wyp2(Metoda siecznych);
        space;
        wypisz(metoda_siecznych( x0 , x1 , ypsylon ));
        karetka;
        
        
        wyp2(Odwrocona interpolacja);
        space;
        wypisz(odwrocona_interpolacja ( x0 , x1 , x2 , ypsylon ));
        karetka;
        karetka2;
        i++ ;
    }
    /*system("pause");*/
    return 0;
}

