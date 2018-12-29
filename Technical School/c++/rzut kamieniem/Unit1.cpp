//---------------------------------------------------------------------------
#include <vcl.h>
#include <math.h>

#pragma hdrstop

#include "Unit1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
#define PI 3.14159265

TForm1 *Form1;
double x=25;
double y=335;

double masa = 1.0;//masa ciala w kg
double v0=120;//szybkosc poczatkowa
double kat=30;//kat wyrzutu
double hp=10;//wysoko�� pocz�tkowa


double g = 10*masa;
double sinus = sin(kat*PI/180);
double cosinus = 1-sinus; 
double tc =((v0*sinus)/g)*2;
double hmax =(((v0*sinus)*(v0*sinus))/(2*g))+hp;
double t = 0;
double h = (((v0*sinus)*t)-((g/2)*t*t))+hp;
double poczatkowa = 335;
double czas = 0.05; //dokladnosc rzutu

//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button2Click(TObject *Sender)
{
Close();    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button1Click(TObject *Sender)
{
Shape1->Height = 300;
Shape1->Width = 1010;
Timer1->Enabled = true;

try{
kat = Edit1->Text.ToDouble();
}catch(...){sinus=45;}
try{
v0 = Edit2->Text.ToDouble();
}catch(...){v0=200;}
try{
hp = Edit3->Text.ToDouble();
}catch(...){hp=0;}

t = 0;
sinus = sin(kat*PI/180);
cosinus = cos(kat*PI/180);
tc =((v0*sinus)/g)*2;
hmax =(((v0*sinus)*(v0*sinus))/(2*g))+hp;
h = (((v0*sinus)*t)-((g/2)*t*t))+hp;
poczatkowa = 335;
czas = 0.01;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
if(h>=0 && x<1005){


h = (((v0*sinus)*t)-((g/2)*t*t))+hp;
Canvas->Pen->Color = clWhite;
Form1->Canvas->Ellipse(x, y, x+20, y+20);

x=(v0*cosinus*t)+25;
t+=czas;

y=-h+335;
Canvas->Pen->Color = clBlack;
Form1->Canvas->Ellipse(x, y, x+20, y+20);
}
}
//---------------------------------------------------------------------------