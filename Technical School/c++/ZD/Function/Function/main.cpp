#include <cstdio>
#include <iostream> 
#include <conio.h>

using namespace std;

int Function1(int a, int b)
{
	return (a+b)*(a-b);
}
int Function2(int x, int y)
{
	return x*(y-3);
}
int Function3(int n)
{
	if(n>0)
		return n* Function3(n-1);
	else 
		return 1;
}
int Function4(int x, int a, int b, int c)
{
	if(x>0)
		return ((a*b*x*x*x)-(b*c*x*x)+(c*x)+2);
	else 
		return (a*b*c*x*x*x*x*x);
}
int Function5(int n, int x1[10])
{
	int result=0;
	for(int i=0;i<n;i++){
		result += x1[i];
	}
	return result;
}
double Function6(int n, int x1[10])
{
	double result=Function5(n, x1);
	result /=(double)n;
	
	return result;
}
int Function7(int n, int a1[10], int x1[10])
{
	int result=0;
	for(int i=0;i<n;i++)
		result += (x1[i]*a1[i]);
	return result;
}
double Function8(int n, int a[10], int b[10], int c[10], int d[10])
{
	double result=0;
	for(int i=0;i<n;i++)
		result += ((double)(a[i]*b[i])/(double)(c[i]*d[i]));
	return result;
}
double Function9(int n, int m, int a[10], int b[10], int c[10], int d[10])
{
	double sumailoczynu=0;
	double srednia=0;
	for(int i=0;i<n;i++)
		sumailoczynu += (a[i]*b[i]);
	for(int i=0;i<m;i++)
		srednia += ((c[i]*d[i])/n);

	double result=0;
	result = sumailoczynu+srednia;

	return result;
}
double Function10(int n, int m, int x[10], int b[10], int c[10])
{
	double sumailoczynu=0;
	double srednia=0;
	for(int i=0;i<n;i++)
		sumailoczynu *= x[i];
	for(int i=0;i<m;i++)
		srednia += (b[i]*c[i]);

	double result=0;
	result = sumailoczynu+srednia;

	return result;
}



int main()
{
	int a=2;
	int b=3;
	int c=3;
	int x=3;
	int y=2;
	int n=7;
	int m=10;
	int x1[]={1,2,3,5,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	int a1[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	int b1[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	int c1[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	int d1[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	
	/*cout << "F(a,b): " << Function1(a,b) <<endl;
	cout << "F(x,y): " << Function2(x,y) <<endl;
	cout << "F(n): " << Function3(n) <<endl;
	cout << "F(x, a, b, c): " << Function4(x, a, b ,c) <<endl;
	cout << "F(n,x[]): " << Function5(n,x1) <<endl;
	cout << "F(n,x[]): " << Function6(n,x1) <<endl;
	cout << "F(n,a[],a[]): " << Function7(n,a1,x1) <<endl;*/
	cout << "ZD function one: " << Function8(n, a1, b1, c1, d1) <<endl;	
	cout << "ZD function two: " << Function9(n, m, a1, b1, c1, d1) <<endl;
	cout << "ZD function tree: " << Function10(n, m, x1, b1, c1) <<endl;

	system("pause");
	return 0;
}


