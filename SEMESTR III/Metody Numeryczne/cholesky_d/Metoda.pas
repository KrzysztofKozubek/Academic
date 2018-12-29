//www.algorytm.org
//Tomasz Lubiñski (c)2001
//Algorytmy numeryczne - Metoda Choleskiego z macierz¹ symetryczn¹ i dodatnio okreslon¹

unit Metoda;
interface
uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls, ComCtrls, Grids, Buttons;
type
  TForm1 = class(TForm)
    RichEdit1: TRichEdit;
    Edit1: TEdit;
    StringGrid1: TStringGrid;
    Label1: TLabel;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    BitBtn3: TBitBtn;
    BitBtn4: TBitBtn;
    Edit2: TEdit;
    Label2: TLabel;
    Button1: TButton;
    Button2: TButton;
    Label3: TLabel;
    procedure Edit1Change(Sender: TObject);
    procedure w_lewo(Sender: TObject);
    procedure w_prawo(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure do_gory(Sender: TObject);
    procedure na_dol(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Oblicz_1(Sender: TObject);
    procedure StringGrid1SelectCell(Sender: TObject; ACol, ARow: Integer;
      var CanSelect: Boolean);
  private
    { Private declarations }
  public
    { Public declarations }
  end;
var
  Form1: TForm1;
  a,b,c,n:Integer;
  prawo: Integer=1;
  gora: Integer=1;
  MA, ML: Array of Array of real;
  MB, MY, MX: Array of real;

implementation

{$R *.DFM}
procedure OBLICZ(var blad:Byte);
var i,j,k:Integer;
    s:    Real;
begin
SetLength(ML, n+2);  SetLength(MX, n+1);  SetLength(MY, n+2);
for i:=1 to n do SetLength(ML[i], i+1);
for i:=2 to n do                          //sprawdzenie symetrycznosci
 for j:=1 to i-1 do
  if Form1.StringGrid1.Cells[i,j]<>Form1.StringGrid1.Cells[j,i]
   then begin blad:=1; exit; end;
for k:=1 to n do                     //obliczenie macierzy L
 for i:=k to n do
  begin
   s:=0;                             //wzór dla L[k,k]
   if i=k then
    begin
     for j:=1 to k-1 do s:=s+ML[k,j]*ML[k,j];
     s:=MA[k,k]-s;
     if s<0 then begin blad:=2; exit; end;
     ML[k,k]:=sqrt(s);
    end                             //wzór dla L[i,k]
   Else
    begin
     for j:=1 to k-1 do s:=s+ML[i,j]*ML[k,j];
     s:=MA[i,k]-s;
     if ML[k,k]=0 then begin blad:=2; exit; end;
     ML[i,k]:=s/ML[k,k];
    end
  end;
for i:=1 to n do                  //obliczenie Y
 begin
  s:=0;
  for k:=1 to i-1 do s:=s+ML[i,k]*MY[k];
  s:=MB[i]-s;
  MY[i]:=s/ML[i,i];
 end;                             //obliczenie koñcowego wyniku X
for i:=n downto 1 do
 begin
  s:=0;
  for k:=i+1 to n do s:=s+ML[k,i]*MX[k];
  s:=MY[i]-s;
  MX[i]:=s/ML[i,i];
 end;
end;


procedure TForm1.FormCreate(Sender: TObject);
begin
RichEdit1.Clear;
StringGrid1.Col:=1;
StringGrid1.Row:=1;
Edit1Change(Form1);
end;

//Zrobienie siatki i ustawienie tablic dynamicznych
procedure TForm1.Edit1Change(Sender: TObject);
var i,j:Integer;
begin
Finalize(MA); Finalize(MB);
n:=StrToInt(Edit1.Text);
StringGrid1.ColCount:=n+2;
StringGrid1.RowCount:=n+1;
SetLength(MA, n+1, n+1);
SetLength(MB, n+1);
for i:=0 to n+2 do for j:=1 to n+1 do StringGrid1.Cells[i,j]:='';
for i:=1 to n do StringGrid1.Cells[i,0]:='x'+IntToStr(i);
for i:=1 to n do StringGrid1.Cells[0,i]:='rownanie '+IntToStr(i);
StringGrid1.Cells[n+1,0]:='b'
end;

//Nawigacja
procedure TForm1.w_lewo(Sender: TObject);
begin
if prawo>1 then
 begin
 prawo:=prawo-1;
 StringGrid1.Col:=prawo;
 StringGrid1.Row:=gora;
 end;
end;
procedure TForm1.w_prawo(Sender: TObject);
begin
if prawo<n+1 then
 begin
  prawo:=prawo+1;
  StringGrid1.Col:=prawo;
  StringGrid1.Row:=gora;
 end;
end;
procedure TForm1.do_gory(Sender: TObject);
begin
if gora>1 then
 begin
  gora:=gora-1;
  StringGrid1.Col:=prawo;
  StringGrid1.Row:=gora;
 end;
end;
procedure TForm1.na_dol(Sender: TObject);
begin
if gora<n then
 begin
  gora:=gora+1;
  StringGrid1.Col:=prawo;
  StringGrid1.Row:=gora;
 end;
end;
procedure TForm1.StringGrid1SelectCell(Sender: TObject; ACol,
  ARow: Integer; var CanSelect: Boolean);
begin
if (ARow>0) and (ACol>0) then
 begin
  prawo:=ACol;   gora:=ARow;
 end;
end;

//wpis
procedure TForm1.Button1Click(Sender: TObject);
var i:Integer;
begin
if prawo=n+1 then Val(Edit2.Text,MB[gora],i) Else Val(Edit2.Text,MA[gora,prawo],i);
if i<>0 then ShowMessage('Blad podczas wpisu') Else StringGrid1.Cells[prawo,gora]:=Edit2.Text;
end;

//wpisanie rozwiazania do Rich Edit'a
procedure TForm1.Oblicz_1(Sender: TObject);
var i,j:Integer;
    tmp: String;
    blad: Byte;
begin
RichEdit1.Clear;
RichEdit1.Lines.Add('Uklad '+IntToStr(n)+' rownan z '+IntToStr(n)+' niewiadomymi: ');
RichEdit1.Lines.Add('*****************************************************');
for i:=1 to n do
 begin
  tmp:='';
  for j:=1 to n do
   if MA[i,j]=0 then continue Else
   if (MA[i,j]>0) and (j<>1) then tmp:=tmp+' + '+FloatToStr(MA[i,j])+'x'+IntToStr(j) Else tmp:=tmp+' '+FloatToStr(MA[i,j])+'x'+IntToStr(j);
  tmp:=tmp+' = '+FloatToStr(MB[i]);
  RichEdit1.Lines.Add(tmp);
 end;
OBLICZ(blad);
Form1.RichEdit1.Lines.Add('*************ROZWI¥ZANIE***************');
if blad=1 then Form1.RichEdit1.Lines.Add('Macierz nie jest symetryczna');
if blad=2 then Form1.RichEdit1.Lines.Add('Macierz nie jest dodatnio okreslona');
if blad=0 then for i:=1 to n do Form1.RichEdit1.Lines.Add('x'+IntToStr(i)+' = '+FloatToStr(MX[i]));
end;

end.
