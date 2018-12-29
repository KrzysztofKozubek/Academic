#include <iostream>
#include <fstream>
using namespace std;

int main(int argc, char* argv[])
{
  int n;
  double k,xp,xk,yp,w;

  cout<<"Podaj ilosc wezlow: ";
  cin>>n;

  double *a=new double[n];
  double *x=new double[n];
  double *y=new double[n];

  for(int i=0;i<n;i++)
  {
   cout<<"x=";
   cin>>x[i];
   cout<<"y=";
   cin>>y[i];
  }

  //Obliczanie a[i]
  for(int i=0;i<n;i++)
  {
   a[i]=1;
   for(int j=0;j<n;j++)
    {
     if(i!=j) a[i]=a[i]*(x[i]-x[j]);
    }
   a[i]=y[i]/a[i];
  }

  //Wyswietlanie wielomianu
  cout<<"\n\nW(x)=";
  for(int i=0;i<n;i++)
  {
   cout<<a[i];
   for(int j=0;j<n;j++)
   {
    if(j!=i)cout<<"(x-"<<x[j]<<")";
   }
  if(i!=n-1 && a[i+1]>=0)cout<<"+";
  }
cout << " " << endl;


        return 0;
}
//W(x)=15.1988(x--1.19)(x--0.74)(x-0.11)(x-2.56)-16.1379(x--1.23)(x--0.74)(x-0.11)(x-2.56)+0.885364(x--1.23)(x--1.19)(x-0.11)(x-2.56)-0.00333543(x--1.23)(x--1.19)(x--0.74)(x-2.56)+0.0570334(x--1.23)(x--1.19)(x--0.74)(x-0.11) 

