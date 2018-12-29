#include<iostream>
using namespace std;

class LU{
private:
	double **tab;
	int rozmiar;
public:
	LU(int);
	~LU();
	int getSize(){ return rozmiar; }
	friend istream& operator >> (istream&, LU&);
	friend ostream& operator << (ostream&, LU);



};
LU::LU(int x){
	cout << "Robie tablice" << endl;
	rozmiar = x;
	tab = new double*[x];
	for (int i = 0; i < x; i++)
		tab[i] = new double[x];
	for (int i = 0; i < x;i++)
	for (int j = 0; j < x; j++)
		tab[i][j] = 0;
}

LU::~LU(){

	delete[]tab;
}

istream& operator >> (istream& in, LU& Y)
{
	for (int i = 0; i<Y.rozmiar; i++)
	for (int j = 0; j<Y.rozmiar; j++)
		in >> Y.tab[i][j];
	return in;
}

ostream& operator << (ostream& out, LU Y)
{
	for (int i = 0; i<Y.getSize(); i++)
	{
		for (int j = 0; j<Y.getSize(); j++)
		{
			out << Y.tab[i][j] << " | ";
		}
		out << endl;
	}
	return out;
}


int main(){
cout<<"wtf";

	}
