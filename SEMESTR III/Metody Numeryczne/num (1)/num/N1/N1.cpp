#include <iostream>
#include<string>
#include <cmath>
#include <fstream>
using namespace std;

/* wybor jezyka uzasadniony zepsuciem sie dysku, na ktorym byla wersja w C, trochê szybciej "chodz¹ca"*/

double metoda_pochodna_1(int x, double h)
{
	return (sin(x+h) - sin(x)) / h;
}	
double metoda_pochodna_2(int x, double h)
{
	return (sin(x+(0.5*h)) - sin(x-(0.5*h))) / h;
}
double metoda_pochodna_3(int x, double h)
{
	return ((sin(x-2*h) - 8*sin(x-h) + 8*sin(x+h) - sin(x+2*h)) / (12*h));
}


int main()
{
	double h, w1, w2, w3, kos = cos(1.0);
	int x=1;
	ofstream wyniki;
	wyniki.open("wyniki.dat");

	for (h = pow(10.0, -15.0); h < 1; h *= 1.01) 
	{
        w1 = log10(abs(metoda_pochodna_1(x,h) - kos));
        w2 = log10(abs(metoda_pochodna_2(x,h) - kos));
        w3 = log10(abs(metoda_pochodna_3(x,h) - kos));
		wyniki << log10(h)<<"  "<< w1 <<"  "<< w2 <<"  "<< w3 << "\n";
		cout << log10(h)<<"  "<< w1 <<"  "<< w2 <<"  "<< w3 << "\n";
	}

	wyniki.close();
	cout << endl;
	/*system("pause");*/ //uruchamiane z windowsa
	return 0;
}

