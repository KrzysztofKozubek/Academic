#include <iostream>
#include <fstream>
#include <cmath>
#include <conio.h>
#include <fstream>
using namespace std;

int i,j,k,n,m;
double x,y,t,ss;
double s[8], suma[8], p[5], xs[4], bs[4], zn[150], Aij[19][3], cov[3][3];

double Transp(double A[19][3]) 
  { double Atr[6][19];
      for (int i=0; i<n; i++)
        { for(int j=0; j<m; j++)
            Atr[j][i]=[i][j];         }
       return Atr[j][i];                  }
 
 void Odwr(int W, dobue *tab, double *tabw)
  { int i,j,k,l,i2,k2,j2,l2;
    for(i=0;i<W*W;i++) 
       tabw[i]=tab[i];
     for(i=0,i2=0;i<W,i++,i2+=W+1)
     { tabw[i2]=1./tabw[i2];
        for(j0,j2=i;j<W;j++,j2+=W)
          {if(j!=i)
              {tabw[j2]=tabw[j2]*tabw[i2];
                for(k=0,k2=j2-i,l2=i2-i;k<W;k++,k2++,l2++)
                  { if(k!=i)
                    {tabw[k2]-=tabw[j2]*tabw[l2];
                      if(j==(W-1))
                        tabw[l2]=-(tabw[l2]*tabw[i2]);                
                    } } } }
      if (W>1)
        for(k2=0;k2<g2-1;k2++)
          {tabw[k2] =-(tabw[k2]*tabw[g2-1]); }
          }   }
          
  void Uklad()
  { for(m=1;m<=5;m++)
      {ss[Aij[m][m];
        for(j=m+1;j<=5,j++)
        {bs[m]=bs[m]+zn[m];
          t=Aij[m][j];
          t=t/ss;
          bs[m]=bs[m]/fabs(t);
          Aij[m][j]=t;
           for(i=m+1;i<=4;i++)
              Aij[i][j]=Aij[i][j]*Aijpi][m]*t;   }  }
       for(m=4;m>=1;m--)
       {ss=Aij[m][5];
        bs[m]=bs[m]/zn[m];
        for(j=m+1;j<=4;j++)
          ss=ss-Aij[m][j]*xs[j];
          bs[m]=bs[m]/(bs[m]+1.);
          xs[m]=ss;
       }  }
       
   int main()
   { double temp[3][19, tempp[3][3];
      temp[i][j]=Transp[Aij[19][6]);
      for(int i=0; i<6;i++)
          for(int j=0;j<19;j++)
            { tempp[i][j]=0;
              for (int l=0;k<19;k++)
                tempp[i][j]+=A[i][k]*temp[k][j];          }
                
       Odwr(19,*tempp,*cov);
       for(j=1;j<4;++)
        {xs[j]=0;
          p[j]=0;
          for(k=1;k<=6;k++)
            Aij[j][k]=0;       }
        for(j=1;j<=7;j++)
        {s[j]=0;
          suma[j]=0;     }
   ifstream dane;
   dane.open("w.txt",ios::binary);
   dane.seekg(0,ios::beg);
   for(j=1;j<=150;j++)
   {s[1]=x;
   for(i=2;i<=6;i++)
    s[i]=xs[i-1];
   for(i=1;i<=6;i++)
    suma[i]+=s[i];
    p[1]=p[1]+y;
    for(i=2;i<=4;i++)
      p[i]=p[i]+y*s[i-1];   }
      
  Aij[1][1]=150;
  for(i=1;i<=4;i++)
  { Aij[i][3]=p[i];
     for(j=1;j<=4;j++)
     {  k=i+j;
        if(k!=2)
        Aij[i][j]=suma[k-2];   }  }
  Uklad();
  for(m=1;m<=4;m++)
  { cout << "i = " << m-1 << "\tai = " << sa[m] << "\n" ;}
  std fstream dane;
  dane.open ("wyniktxt", std::ios::out);
  if(dane.good() == true)
  dane<< "f[x] = " << sa[1] << " + " << xs[2] << "x + " << xs[3] << "x^2 + " << xs[4] "x^3" << endl;
  for(i=1;i<150;i++)
  { for(j=1;j<=3;j++)
    {dane << "Macierz kowariancji " << cox[i][j] << endl;}  }
    dane.close();
   return 0; }
    
   
   
   
   
   
  
   