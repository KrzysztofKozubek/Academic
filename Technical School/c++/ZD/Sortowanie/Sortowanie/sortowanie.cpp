#include <iostream>
#include <conio.h>
#include <time.h>
#include <windows.h>

using namespace std;


int main()
{
	int t[]={10,9,8,7,6,5,4,3,2,1,10,9,8,7,6,5,4,3,2,1,10,9,8,7,6,5,4,3,2,1,10,9,8,7,6,5,4,3,2,1,10,9,8,7,6,5,4,3,2,1,10,9,8,7,6,5,4,3,2,1};
	int p;
	for(int j=0;j<60;j++)
	{
		for(int i=0;i<(59-j);i++)
		{
			if(t[i]>t[i+1]){
				p=t[i];
				t[i]=t[i+1];
				t[i+1]=p;
			}
		}
	}

	for(int i=0;i<60;i++) cout << t[i] <<"\a";
	_getch();
    return 0;
}