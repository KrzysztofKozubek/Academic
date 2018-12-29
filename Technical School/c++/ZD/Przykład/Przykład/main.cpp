#include "head.hpp"


int dodaj(int a, int b);
int odejmij(int a, int b);
typedef int (* dzialanie)(int, int );

int oblicz(int a, int b, dzialanie podaj);

int dodaj(int a, int b){
	return a+b;
}
int odejmij(int a, int b){
	return a-b;
}


int main()
{
	/*int n=2;
	int m=4;
	int a[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
	int b[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
	int c[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
	int d[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
	int x[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};

    cout << "A)Suma(od 0 do n) Ai*Bi/Di*Ci: " << ExampleFunctionOne(n, a, b, c, d) << endl;
	cout << "B)Suma(od 0 do n) Ai*Xi+(Suma(od 0 do m)Bi*Ci/n): " << ExampleFunctionTwo(n, a, b, m, c, x) << endl;
	cout << "C)Iloczyn(od 0 do n) Xi+(Suma(od 0 do m)Bi*Ci): " << ExampleFunctionTree(n, b, m, c, x) << endl;

	cout << "suma: " << oblicz( 10, 5, dodaj);
	cout << "rozn: " << oblicz( 10, 5, odejmij);*/


	double a,b;
	a=0;
	b=5;
	double WartoscA=0;
	double WartoscB=0;
	double help=0;
	double Zero_place=0;
	double procesing = 1;
	do{
		help = (a + b)/2;
		WartoscA=a*a-1;
		WartoscB=b*b-1;
		if(WartoscA>0 && WartoscB>0) b=help;
		else if(WartoscA<0 && WartoscB<0) a=help;
		else if(WartoscA<0 && WartoscB>0) a=help;
		cout << Zero_place << endl;
		Zero_place = (b-a)/2;
	}while( abs(b-a) > procesing );
	cout << Zero_place;







	

    _getch();
    return( 0 );
}


