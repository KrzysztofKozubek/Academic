//Metoda Gaussa-Seidela
 #include <iostream>
 #include <iomanip>
 #include <cmath>
 using namespace std;

 int dim = 100;
 int iter = 50;

 int main() {
 double A[dim][dim];
 double x[dim];
 double e[dim];
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
 for(int i=0; i<iter; i++) {
 x[0] = (e[0]-A[0][1]*x[1]-A[0][4]*x[4])/4;
 x[1] = (e[1]-A[1][0]*x[0]-A[1][2]*x[2]-A[1][5]*x[5])/4;
 x[2] = (e[2]-A[2][1]*x[1]-A[2][3]*x[3]-A[2][6]*x[6])/4;
 x[3] = (e[3]-A[3][2]*x[2]-A[3][4]*x[4]-A[3][7]*x[7])/4;
 for(int j=4; j<dim-4; j++)
 x[j] = (e[j]-A[j][j-4]*x[j-4]-A[j][j-1]*x[j-1]-A[j][j+1]*x[j+1]-A[j
][j+4]*x[j+4])/4;
 x[dim-4] = (e[dim-4]-A[dim-4][dim-8]*x[dim-8]-A[dim-4][dim-5]*x[dim-
5]-A[dim-4][dim-3]*x[dim-3])/4;
 x[dim-3] = (e[dim-3]-A[dim-3][dim-7]*x[dim-7]-A[dim-3][dim-4]*x[dim-
4]-A[dim-3][dim-2]*x[dim-2])/4;
 x[dim-2] = (e[dim-2]-A[dim-2][dim-6]*x[dim-6]-A[dim-2][dim-3]*x[dim-
3]-A[dim-2][dim-1]*x[dim-1])/4;
 x[dim-1] = (e[dim-1]-A[dim-1][dim-5]*x[dim-5]-A[dim-1][dim-2]*x[dim-
2])/4;
 for(int j=0; j<dim; j++) nnorma[j] = x[j];
 double norm = 0;
 for(int j=0; j<dim; j++) norm = norm+(nnorma[j]-norma[j])*(nnorma[j
]-norma[j]);
 norm = sqrt(norm);
 for(int j=0; j<dim; j++) norma[j] = nnorma[j];
 if(i>=1) {cout << setprecision(12) << fixed << "||xk - x(k-1)|| = "
<< norm << endl; } }
 for(int i=0; i<dim; i++) cout << "x" << i << " = " << x[i] << endl;
 return 0;
 }
