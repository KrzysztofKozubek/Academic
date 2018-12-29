//---------------------------------------------------------------------------
#include <cstdlib>
#include <vcl.h>
#include <math.h>
#include <stdlib.h>

#pragma hdrstop

#include "Unit1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm1 *Form1;

//pkt kola
double x=270;
double y=300;

//kierunek kuli
double dx=1;
double dy=2;

//pozycja gracza
double xg=270;

//ilosc przeszkod
double przeszkoda[6][8];

//wynik
double wynik=0;
double extrapoint=0;

//poziom
int level=1;

//s = szerokosc, g=gracza, k=kola, sg=szybkosc gry, si=sila uderzenia
double s_g=100;
double s_k=20;
double sg=0;
double szg=3;
double su=1;
//spr wygranej
int spr=0;
//---------------------------------------------------------------------------


__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner)
{

//if (OpenDialog1->Execute()) MediaPlayer1->FileName = OpenDialog1->FileName;
//MediaPlayer1->Open();
//MediaPlayer1->Play();
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
Form1->Label3->Caption="Wielkoœæ ko³a: "+FloatToStr(s_k);
Form1->Label4->Caption="Wielkoœæ gracza: "+FloatToStr(s_g);
Form1->Label5->Caption="Szybkoœæ ko³a: "+FloatToStr(sg);
Form1->Label6->Caption="Szybkoœæ gracza: "+FloatToStr(szg);
Form1->Label7->Caption="Wiêcej kasy: "+FloatToStr(extrapoint);
Form1->Label9->Caption="Sterowanie: strza³ki lewo prawo.. Zasady: zbij " + IntToStr(spr) + " elementów na planszy.: ";
Form1->Label11->Caption="Si³a uderzenia: "+FloatToStr(su);
Form1->Label12->Caption="Poziom: "+IntToStr(level);
//zacieranie sladow po obwodzie
Form1->Canvas->Pen->Color=clBlack;
Form1->Canvas->Rectangle(270,450,270+s_g,460);
//Form1->Canvas->Rectangle(xg,450,xg+s_g,460);
Form1->Canvas->Ellipse(x,y,x+s_k,y+s_k);

//malowanie pola
Form1->Canvas->Pen->Color=clWhite;
Form1->Canvas->Rectangle(0,0,640,480);

//rysowanie przeszkod
int xx;
int yy;
for(int i=0;i<6;i++){
 for(int j=0;j<8;j++){
   if(przeszkoda[i][j]>=1){
   Form1->Canvas->Pen->Color=clWhite;
   Form1->Canvas->Rectangle(j*80,100+(20*i),(j*80)+80,100+(20*i)+20);

   xx=j*80;
    yy=100+(20*i);
    //odbicie od dolu
    if(xx-s_k<=x && xx>=x-80 && y-yy<=20 && y-yy>=18){
    if(dy<0){
    dy=abs(dy); }
    przeszkoda[i][j]-=su;
    if(przeszkoda[i][j]<=0){
    spr--;}

    wynik +=(10*level)+extrapoint;
    }//odbicie od gory
    if(xx-s_k<=x && xx>=x-80 && yy-y<=s_k && yy-y>=18){
    wynik +=(10*level)+extrapoint;
    if(dy>0){
    dy=(dy*(-1));

    }
    przeszkoda[i][j]-=su;
    if(przeszkoda[i][j]<=0){
    spr--;}
    }//odbicie od lewej strony
    if(xx>=x && xx<=x+s_k && yy-20<=y && yy>=y-20){
    wynik +=(10*level)+extrapoint;
    if(dx>0){
    dx=(dx*(-1));
    }
    przeszkoda[i][j]-=su;
    if(przeszkoda[i][j]<=0){
    spr--;}
    }//odbicie od prawej strony
    if(xx+80>=x && xx+80<=x+s_k && yy-20<=y && yy>=y-20){
    wynik +=(10*level)+extrapoint;
    if(dx<0){
    dx=abs(dx);}
    przeszkoda[i][j]-=su;
    if(przeszkoda[i][j]<=0){
    spr--;}
    }

   
   }

 }
}
if(spr<=0){Form1->Timer1->Enabled=false;ShowMessage("Wygra³eœ! Kliknij next ¿eby zacz¹æ kolejny poziom...");}


//sterowanie paskiem
if (HiWord(GetKeyState(VK_LEFT)) != 0)xg-=szg;
if (HiWord(GetKeyState(VK_RIGHT)) != 0)xg+=szg;

//odbijanie od scian
if(x<=1){dx=abs(dx);}
if(x>=639-s_k){dx*=-1;}
if(y<=0){dy=abs(dy);}


//ogranicznie ruchu paska
if(xg>640-s_g)xg=640-s_g;
if(xg<0)xg=0;

//odbijanie od paska
if(y>=450-s_k && x-xg<s_g/5 && x-xg>-(s_k*2)){dy=-1;dx=-2;wynik+=level;}
if(y>=450-s_k && x-xg<(s_g/5)*2 && x-xg>s_g/5){dy=-2;dx=-1;wynik+=level;}
if(y>=450-s_k && x-xg<=(s_g/5)*3 && x-xg>=(s_g/5)*2){dy=-2;wynik+=level;}
if(y>=450-s_k && x-xg<(s_g/5)*4 && x-xg>(s_g/5)*3){dy=-2;dx=1;wynik+=level;}
if(y>=450-s_k && x-xg<s_g && x-xg>(s_g/5)*4){dy=-1;dx=2;wynik+=level;}

Form1->Label1->Caption = wynik;

if(dx>0){dx+=sg;}else{dx-=sg;}
if(dy>0){dy+=sg;}else{dy-=sg;}
x+=dx;
y+=dy;
if(dx<0){dx+=sg;}else{dx-=sg;}
if(dy<0){dy+=sg;}else{dy-=sg;}

//rysowanie kola i gracza 
Form1->Canvas->Pen->Color=clWhite;
Form1->Canvas->Rectangle(xg,450,xg+s_g,460);
Form1->Canvas->Ellipse(x,y,x+s_k,y+s_k);

if(y>=459 || y==458){Form1->Timer1->Enabled=false;ShowMessage("Koniec Gry Przegra³eœ!");}     
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button1Click(TObject *Sender)
{
if(wynik>1000){wynik-=1000;s_k++;}
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button2Click(TObject *Sender)
{
if(wynik>1000){wynik-=1000;s_g++;}
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button10Click(TObject *Sender)
{
if(wynik>1000){wynik-=1000;su++;}
}
void __fastcall TForm1::Button3Click(TObject *Sender)
{
if(wynik>2000){wynik-=2000;sg+=1;}    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button4Click(TObject *Sender)
{
if(wynik>1500){wynik-=1500;szg++;}    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button5Click(TObject *Sender)
{
if(wynik>10){wynik-=10;extrapoint++;}    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button6Click(TObject *Sender)
{  


for(int i=0;i<6;i++){
 for(int j=0;j<8;j++){
   przeszkoda[i][j]=rand()% (level+1);
   if(przeszkoda[i][j]>=1){
   Form1->Canvas->Pen->Color=clWhite;
   Form1->Canvas->Rectangle(j*80,100+(20*i),(j*80)+80,100+(20*i)+20);
   spr++;
   }
 }
}
Form1->Timer1->Enabled=true;
Form1->Button7->Enabled=true;
Form1->Button8->Enabled=true;
Form1->Button6->Enabled=false;
}   
//---------------------------------------------------------------------------

void __fastcall TForm1::Button7Click(TObject *Sender)
{
//spr-=10;
if(spr<=0){
for(int i=0;i<6;i++){
 for(int j=0;j<8;j++){
   przeszkoda[i][j]=0;
 }
}

level++;
wynik+=(100*level);
Form1->Timer1->Enabled=true;
x=270;
y=400;
dy=-2;
dx=0;
xg=270;
spr=0;

for(int i=0;i<6;i++){
 for(int j=0;j<8;j++){
   przeszkoda[i][j]=rand()% (level+1);
   if(przeszkoda[i][j]>=1){
   Form1->Canvas->Pen->Color=clWhite;
   Form1->Canvas->Rectangle(j*80,100+(20*i),(j*80)+80,100+(20*i)+20);
   spr++;
   }
 }
}
}          }
//---------------------------------------------------------------------------

void __fastcall TForm1::Button8Click(TObject *Sender)
{
if(Timer1->Enabled==true){Form1->Timer1->Enabled=false;}else{Form1->Timer1->Enabled=true;}
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button9Click(TObject *Sender)
{
Close();    
}
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------

