#include "head.hpp"

double ExampleFunctionOne(const int n, int a[], int b[], int c[], int d[])
{

	double result=0;
	for(int l=0;l<n;l++)
		result += (a[l]*b[l])/(d[l]*c[l]);

	return result;
}

double ExampleFunctionTwo(const int n, int a[], int b[], int m, int c[], int x[])
{

	double suma=0;
	double help=0;
	double result=0;
	for(int i=0;i<n;i++)
		suma = a[i]*x[i];
	for(int j=0;j<m;j++)
		help += (b[j]*c[j])/n;
		result +=suma+help;

	return result;
}

double ExampleFunctionTree(const int n, int b[], int m, int c[], int x[])
{
	
	double iloczyn=0;
	double help=0;
	double result=0;
	for(int j=0;j<m;j++)
			help += b[j]*c[j];
	for(int i=0;i<n;i++){
		iloczyn = x[i];		
		cout << "iloczyn : " << iloczyn << " Suma : " << help << endl;
		result =result * iloczyn+help;
		iloczyn=0;
		help=0;
	}
	return result;
}