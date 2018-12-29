#include<cmath>
#include<iostream>
#include<iomanip>
#include<complex>
#include<cstdio> 
#define wyp(a) printf(#a "\n\n")
#define wyp2(a) printf(#a)
#define karetka2 printf("\n\n")
#define space printf(" ")

using namespace std;

typedef complex<double> zespolona ;

// funkcja ktorej miejsc zerowych poszukujemy w tablicy
zespolona wartosc_f(zespolona a[], double n,zespolona z)
{
	 zespolona wiel=0;
	 for(double i=0; i<=n; i=i+1) wiel += a[(int)i]*pow(z,i);
	 return wiel;
}

// pierwsza pochodna wielomianu przechowywanego w a[] 
zespolona  pochodna1 (zespolona a[], double n, zespolona z)
{
	 zespolona wiel=0;
	 for(double i=1; i<=n; i=i+1) wiel += a[(int)i]*i*pow(z,i-1);
	 return wiel;
}

// druga pochodna wielomianu a[] 
zespolona pochodna2(zespolona a[], double n, zespolona z)
{
	 zespolona wiel=0;
	 for(double i=2; i<=n; i=i+1) wiel += a[(int)i]*i*(i-1)*pow(z,i-2);
	 return wiel;
}

// implementacja wzoru laguera 
zespolona laguerre(zespolona a[], double n, zespolona z)
{
	 zespolona W = pochodna1(a, n, z)+sqrt((n-1)*((n-1)*pow(pochodna1(a, n, z), 2) - n*wartosc_f(a, n, z)*pochodna2(a, n, z)));
	 zespolona H = pochodna1(a, n, z)-sqrt((n-1)*((n-1)*pow(pochodna1(a, n, z), 2) - n*wartosc_f(a, n, z)*pochodna2(a, n, z)));
	 zespolona mianownik;
	 zespolona licznik = n*wartosc_f(a, n, z);
	 if(abs(H)>abs(W)) mianownik = H;
	 else mianownik = W;
	 zespolona wynik = z-licznik/mianownik;
	 return wynik;
}  

void deflacja(zespolona a[], int n, zespolona z, zespolona b[])
{
	 b[n-1] = a[n];
	 for(int i=n-1; i>0; i--) b[i-1]=a[i]+z*b[i];
}


int main() 
{
    zespolona a[11] = {-4, -4, -12, -8, -11, -3, -1, 2, 3, 1, 1};
    zespolona b[10], c[9], d[8], e[7], f[6], g[5], h[4], i[3], j[2];
    zespolona z0 = -3;
    zespolona z1, z1p, z2, z2p, z3, z3p, z4, z4p, z5, z5p, z6, z6p, z7, z7p, z8, z8p, z9, z9p, z10, z10p;
    
    wyp(Rozwiazanie 2a:);		
    z1 = laguerre(a, 10, z0);
    while(abs(wartosc_f(a, 10, z1))>0.000001)
    {
		z1 = laguerre(a, 10, z1);
    }
    printf(	"z = Re(%f) Im(%f)\n",real(z1),imag(z1));
    deflacja(a, 10, z1, b);
    z2p = laguerre(b, 9, z0);
    while(abs(wartosc_f(b, 9, z2p))>0.000001)
    {
		z2p = laguerre(b, 9, z2p);
    }
    z2 = laguerre(b, 9, z2p);
    while(abs(wartosc_f(b, 9, z2))>0.000001)
    {
		z2 = laguerre(b, 9, z2);
    }
    printf(	"z^2 = Re(%f) Im(%f)\n",real(z2),imag(z2));
    deflacja(b, 9, z2, c);
    z3p = laguerre(c, 8, z0);
    while(abs(wartosc_f(c, 8, z3p))>0.00000001)
    {
		z3p = laguerre(c, 8, z3p);
    }
    z3 = laguerre(a, 10, z3p);
    while(abs(wartosc_f(a, 10, z3))>0.00000001)
    {
		z3 = laguerre(a, 10, z3);
    }
    printf(	"z^3 = Re(%f) Im(%f)\n",real(z3),imag(z3));
    deflacja(c, 8, z3, d);
    z4p = laguerre(d, 7, z0);
    while(abs(wartosc_f(d, 7, z4p))>0.00000001)
    {
		z4p = laguerre(d, 7, z4p);
    }
    z4 = laguerre(a, 10, z4p);
    while(abs(wartosc_f(a, 10, z4))>0.00000001)
    {
		z4 = laguerre(a, 10, z4);
    }
    printf(	"z^4 = Re(%f) Im(%f)\n",real(z4),imag(z4));
    deflacja(d, 7, z4, e);
    z5p = laguerre(e, 6, z0);
    while(abs(wartosc_f(e, 6, z5p))>0.00000001)
    {
		z5p = laguerre(e, 6, z5p);
    }
    z5 = laguerre(a, 10, z5p);
    while(abs(wartosc_f(a, 10, z5))>0.00000001)
    {
		z5 = laguerre(a, 10, z5);
    }
    printf(	"z^5 = Re(%f) Im(%f)\n",real(z5),imag(z5));
    deflacja(e, 6, z5, f);
    z6p = laguerre(f, 5, z0);
    while(abs(wartosc_f(f, 5, z6p))>0.00000001)
    {
		z6p = laguerre(f, 5, z6p);
    }
    z6 = laguerre(a, 10, z6p);
    while(abs(wartosc_f(a, 10, z6))>0.00000001)
    {
		z6 = laguerre(a, 10, z6);
    }
    printf(	"z^6 = Re(%f) Im(%f)\n",real(z6),imag(z6));
    deflacja(f, 5, z6, g);
    z7p = laguerre(g, 4, z0);
    while(abs(wartosc_f(g, 4, z7p))>0.00000001)
    {
		z7p = laguerre(g, 4, z7p);
    }
    z7 = laguerre(a, 10, z7p);
    while(abs(wartosc_f(a, 10, z7))>0.00000001)
    {
		z7 = laguerre(a, 10, z7);
    }
    printf(	"z^7 = Re(%f) Im(%f)\n",real(z7),imag(z7));
    deflacja(g, 4, z7, h);
    z8p = laguerre(h, 3, z0);
    while(abs(wartosc_f(h, 3, z8p))>0.00000001)
    {
		z8p = laguerre(h, 3, z8p);
    }
    z8 = laguerre(a, 10, z8p);
    while(abs(wartosc_f(a, 10, z8))>0.00000001)
    {
		z8 = laguerre(a, 10, z8);
    }
    printf(	"z^8 = Re(%f) Im(%f)\n",real(z8),imag(z8));
    deflacja(h, 3, z8, i);
    z9p = laguerre(i, 2, z0);
    while(abs(wartosc_f(i, 2, z9p))>0.00000001)
    {
		z8p = laguerre(i, 2, z9p);
    }
    z9 = laguerre(a, 10, z9p);
    while(abs(wartosc_f(a, 10, z9))>0.00000001)
    {
		z9 = laguerre(a, 10, z9);
    }
    printf(	"z^9 = Re(%f) Im(%f)\n",real(z9),imag(z9));
    deflacja(i, 2, z9, j);
    z10p = laguerre(j, 1, z0);
    while(abs(wartosc_f(j, 1, z10p))>0.00000001)
    {
		z9p = laguerre(j, 1, z10p);
    }
    z10 = laguerre(a, 10, z10p);
    while(abs(wartosc_f(a, 10, z10))>0.00000001)
    {
		z10 = laguerre(a, 10, z10);
    }
    printf(	"z^10 = Re(%f) Im(%f)\n",real(z10),imag(z10));
    
    
    karetka2;
    wyp(Rozwiazanie 2b:);
    
    zespolona cplx_1(1.0, 0.0);
    zespolona cplx_2(0.0, 1.0);
    zespolona cplx_3(-1.0, 0.0);
    zespolona cplx_4(0.0, -1.0);
    zespolona cplx_5(1.0, 0.0);
    zespolona a2[5] = {cplx_1, cplx_2, cplx_3, cplx_4, cplx_5};
    zespolona b2[4], c2[3], d2[2];
    z0 = -3;		
    
    z1 = laguerre(a2, 4, z0);
    while(abs(wartosc_f(a2, 4, z1))>0.000001)
    {
    	z1 = laguerre(a2, 4, z1);
    }
    printf(	"z = Re(%f) Im(%f)\n",real(z1),imag(z1)); 
    deflacja(a2, 4, z1, b2);
    // wygladzenie 
    z2p = laguerre(b2, 3, z0);
    while(abs(wartosc_f(b2, 3, z2p))>0.000001)
    {
		z2p = laguerre(b2, 3, z2p);
    }
    // wygladzenie 
    z2 = laguerre(b2, 3, z2p);
    while(abs(wartosc_f(b2, 3, z2))>0.000001)
    {
		z2 = laguerre(b2, 3, z2);
    }	
    printf("z^2 = Re(%f) Im(%f)\n",real(z2),imag(z2)); 
    deflacja(b2, 3, z2, c2);
    z3p = laguerre(c2, 2, z0);
    while(abs(wartosc_f(c2, 2, z3p))>0.00000001)
    {
		z3p = laguerre(c2, 2, z3p);
    }
    // wygladzenie
    z3 = laguerre(a2, 4, z3p);
    while(abs(wartosc_f(a2, 4, z3))>0.00000001)
    {
		z3 = laguerre(a2, 4, z3);
    }
     printf("z^3 = Re(%f) Im(%f)\n",real(z3),imag(z3)); 
    deflacja(c2, 2, z3, d2);
    z4p = laguerre(d2, 1, z0);
    while(abs(wartosc_f(d2, 1, z4p))>0.00000001)
    {
        z4p = laguerre(d2, 1, z4p);
    }
    // wygladzenie 
    z4 = laguerre(a2, 4, z4p);
    while(abs(wartosc_f(a2, 4, z4))>0.00000001)
    {
		z4 = laguerre(a2, 4, z4);  
    }
    printf(	"z^4 = Re(%f) Im(%f)\n",real(z4),imag(z4)); 
    karetka2;
    
    /*system("pause");*/
    return 0 ;
}
