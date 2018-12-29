//---------------------------------------------------------------------------
#include <vcl.h>
#include<math.h>
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
double v0=200;//szybkosc poczatkowa
double kat=5;//kat wyrzutu
double hp=200;//wysokoœæ pocz¹tkowa

double t = 0;
double poczatkowa = 335;
double czas = 0.01;
double g = 10*masa;
double sinus = sin(kat*PI/180);
double cosinus = cos(kat*PI/180);
double tc =((v0*sinus)/g)*2;
double hmax =(((v0*sinus)*(v0*sinus))/(2*g))+hp;
double h = (((v0*sinus)*t)-((g/2)*t*t))+hp;
double h3= (((v0*sinus)*(t+(0.5)))-((g/2)*(t+(0.5))*(t+(0.5))))+hp;
double z = (2*(v0)*(v0)*sinus*cosinus)/g;


//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner)
{

Form1->Canvas->Pen->Color = clBlack;
Form1->Canvas->MoveTo(29,100);

}

//---------------------------------------------------------------------------

void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
if((-h3+335)<340 && ((v0*cosinus*(t+0.5)))<775 && (-h3+335)>64){


Form1->Shape1->Height=275;
Form1->Shape1->Width=782;
Canvas->Pen->Color = clBlack;
Form1->Canvas->Rectangle(16, 339-hp, 36, 339);
h = (((v0*sinus)*t)-((g/2)*(t*t)))+hp;
h3= (((v0*sinus)*(t+0.5))-((g/2)*((t+0.5)*(t+0.5))))+hp;
Form1->Canvas->Pen->Color = clBlack;
Form1->Canvas->Rectangle(14,64,800,339);


x=(v0*cosinus*t)+25;
t+=czas;

y=-h+335;
Canvas->Pen->Color = clBlack;

Form1->Canvas->MoveTo(x,y);
   Canvas->LineTo(((v0*cosinus*(t+0.5))+25),(-h3+335));
   //Canvas->LineTo(((v0*cosinus*(t+0.5))+25)+15,(-h3+335)+3);
   //Canvas->LineTo(((v0*cosinus*(t+0.5))+25)+15,(-h3+335)-3);
   //Canvas->LineTo(((v0*cosinus*(t+0.5))+25)+20,(-h3+335));
}    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::StartClick(TObject *Sender)
{
x=25;
y=335;
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
h3= (((v0*sinus)*(t+(0.5)))-((g/2)*(t+(0.5))*(t+(0.5))))+hp;
poczatkowa = 335;
czas = 0.01;
z = (2*(v0)*(v0)*sinus*cosinus)/g;
Form1->Canvas->Rectangle(14,64,800,339);    
}
//---------------------------------------------------------------------------

