//Metoda gradientow sprzezonych
 #include <iostream>
 #include <cmath>
 #include <iomanip>
 using namespace std;

 int main() {
 int dim = 100;
 int iteracji = 50;
 double A[dim][dim];
 double x[dim];
 double e[dim];
 double p[dim];
 double r[dim];
 double Ap[dim];
 double rr[dim];
 double pp[dim];
 double norma[dim];
 double nnorma[dim];

 for(int i=0; i<dim; i++){
 x[i] = e[i] = norma[i] = 1;
 for(int j=0; j<dim; j++) {
 if(i==j) {
 A[i][j] = 4;
 if(j<dim-1) A[i+1][j] = A[i][j+1] = 1;
 if(j<dim-4) A[i+4][j] = A[i][j+4] = 1;
 } } }

 r[0] = e[0]+(-A[0][0]*x[0]-A[0][1]*x[1]-A[0][4]*x[4]);
 r[1] = e[1]+(-A[1][0]*x[0]-A[1][1]*x[1]-A[1][2]*x[2]-A[1][5]*x[5]);
 r[2] = e[2]+(-A[2][1]*x[1]-A[2][2]*x[2]-A[2][3]*x[3]-A[2][6]*x[6]);
 r[3] = e[3]+(-A[3][2]*x[2]-A[3][3]*x[3]-A[3][4]*x[4]-A[3][7]*x[7]);

 for(int j=4; j<dim-4; j++) r[j] = e[j]-(A[j][j-4]*x[j-4]+A[j][j-1]*x[j
-1]+A[j][j]*x[j]+A[j][j+1]*x[j+1]+A[j][j+4]*x[j+4]);

 r[dim-4] = e[dim-4]+(-A[dim-4][dim-8]*x[dim-8]-A[dim-4][dim-5]*x[dim-5
]-A[dim-4][dim-4]*x[dim-4]-A[dim-4][dim-3]*x[dim-3]);
 r[dim-3] = e[dim-3]+(-A[dim-3][dim-7]*x[dim-7]-A[dim-3][dim-4]*x[dim-4
]-A[dim-3][dim-3]*x[dim-3]-A[dim-3][dim-2]*x[dim-2]);
 r[dim-2] = e[dim-2]+(-A[dim-2][dim-6]*x[dim-6]-A[dim-2][dim-3]*x[dim-3
]-A[dim-2][dim-2]*x[dim-2]-A[dim-2][dim-1]*x[dim-1]);
 r[dim-1] = e[dim-1]+(-A[dim-1][dim-5]*x[dim-5]-A[dim-1][dim-2]*x[dim-2
]-A[dim-1][dim-1]*x[dim-1]);

 for(int i=0; i<dim; i++)
 p[i] = r[i];

 for(int i=0; i<iteracji; i++) {
 Ap[0] = A[0][0]*p[0]+A[0][1]*p[1]+A[0][4]*p[4];
 Ap[1] = A[1][0]*p[0]+A[1][1]*p[1]+A[1][2]*p[2]+A[1][5]*p[5];
 Ap[2] = A[2][1]*p[1]+A[2][2]*p[2]+A[2][3]*p[3]+A[2][6]*p[6];
 Ap[3] = A[3][2]*p[2]+A[3][3]*p[3]+A[3][4]*p[4]+A[3][7]*p[7];

 for(int j=4; j<dim-4; j++) Ap[j] = A[j][j-4]*p[j-4]+A[j][j-1]*p[j-1
]+A[j][j]*p[j]+A[j][j+1]*p[j+1]+A[j][j+4]*p[j+4];

 Ap[dim-4] = A[dim-4][dim-8]*p[dim-8]+A[dim-4][dim-5]*p[dim-5]+A[dim-
4][dim-4]*p[dim-4]+A[dim-4][dim-3]*p[dim-3];
 Ap[dim-3] = A[dim-3][dim-7]*p[dim-7]+A[dim-3][dim-4]*p[dim-4]+A[dim-
3][dim-3]*p[dim-3]+A[dim-3][dim-2]*p[dim-2];


 Ap[dim-2] = A[dim-2][dim-6]*p[dim-6]+A[dim-2][dim-3]*p[dim-3]+A[dim-
2][dim-2]*p[dim-2]+A[dim-2][dim-1]*p[dim-1];
 Ap[dim-1] = A[dim-1][dim-5]*p[dim-5]+A[dim-1][dim-2]*p[dim-2]+A[dim-
1][dim-1]*p[dim-1];

 double l, m, a, b;
 l = m = a = b = 0;

 for(int j=0; j<dim; j++) {
 l = l + r[j]*r[j];
 m = m + p[j]*Ap[j]; }
 a = l/m;
 for(int j=0; j<dim; j++) {
 rr[j] = r[j] - a*Ap[j];
 x[j] = x[j] + a*p[j]; }
 m = l;
 l = 0;
 for(int j=0; j<dim; j++)
 l = l + rr[j]*rr[j];
 b = l/m;
 for(int j=0; j<dim; j++)
 pp[j] = rr[j] + b*p[j];
 for(int j=0; j<dim; j++) {
 p[j] = pp[j];
 r[j] = rr[j]; }
 for(int j=0; j<dim; j++)
 nnorma[j] = x[j];
 double normaa = 0;
 for(int j=0; j<dim; j++)
 normaa = normaa+(nnorma[j]-norma[j])*(nnorma[j]-norma[j]);
 normaa = sqrt(normaa);
 for(int j=0; j<dim; j++) norma[j] = nnorma[j];
 if(i>=1) cout << setprecision(12) << fixed << "||xk - x(k-1)|| = " <<
normaa << endl;}
 for(int i=0; i<dim; i++) cout << "x" << i << " = " << x[i] << endl;
 return 0;}
