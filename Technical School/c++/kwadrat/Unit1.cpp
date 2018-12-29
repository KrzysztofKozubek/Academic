//---------------------------------------------------------------------------
#include <vcl.h>
#include <cstdio>
#include <cstdlib>
#include <cmath>
#include <math.h>
#pragma hdrstop

#include "Unit1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm1 *Form1;
int x1 = 100;
int y1 = 130;
int dx1= 1;
int dy1=-1;
int x2 = 250;
int y2 = 190;
int dx2=-1;
int dy2=-1;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner)
{
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button1Click(TObject *Sender)
{
    Form1->Timer1->Enabled = true;
Form1->Shape1->Height = 321;
Form1->Shape1->Width = 809;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
Canvas->Pen->Color = clWhite;
Form1->Canvas->Ellipse(x1,y1,x1+50,y1+50);
Canvas->Pen->Color = clWhite;
Form1->Canvas->Ellipse(x2,y2,x2+50,y2+50);

float przeciwprostokatna;
przeciwprostokatna=((abs(x1-x2))^2)+((abs(y1-y2))^2);
przeciwprostokatna=sqrt(przeciwprostokatna);

if((x1>x2 && x1-x2<50 && y1-y2<50 && y1-y2>=0 && przeciwprostokatna<=8) || (x1<x2 && x2-x1<50 && y1-y2<50 && y1-y2>=0 && przeciwprostokatna<=8)){dy1=1;dy2=-1;}
if((y1>y2 && y2-y1<50 && x2-x1<50 && x2-x1>=0 && przeciwprostokatna<=8) || (y1<y2 && y2-y1<50 && x2-x1<50 && x2-x1>=0 && przeciwprostokatna<=8)){dx1=-1;dx2=1;}

if((x2>x1 && x2-x1<50 && y2-y1<50 && y2-y1>=0 && przeciwprostokatna<=8) || (x2<x1 && x1-x2<50 && y2-y1<50 && y2-y1>=0 && przeciwprostokatna<=8)){dy1=-1;dy2=1;}
if((y2>y1 && y2-y1<50 && x1-x2<50 && x1-x2>=0 && przeciwprostokatna<=8) || (y2<y1 && y1-y2<50 && x1-x2<50 && x1-x2>=0 && przeciwprostokatna<=8)){dx1=1;dx2=-1;}

if(x1>=782)dx1=-1;
if(x1<=25)dx1=1;
if(y1<=81)dy1=1;
if(y1>=350)dy1=-1;

if(x2>=782)dx2=-1;
if(x2<=25)dx2=1;
if(y2<=81)dy2=1;
if(y2>=350)dy2=-1;

if(dx1==-1){x1-=1;}else{x1+=1;}
if(dy1==-1){y1-=1;}else{y1+=1;}

if(dx2==-1){x2-=1;}else{x2+=1;}
if(dy2==-1){y2-=1;}else{y2+=1;}

Canvas->Pen->Color = clBlack;
Form1->Canvas->Ellipse(x1,y1,x1+50,y1+50);

Canvas->Pen->Color = clBlack;
Form1->Canvas->Ellipse(x2,y2,x2+50,y2+50);
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button2Click(TObject *Sender)
{
Close();    
}
//---------------------------------------------------------------------------

