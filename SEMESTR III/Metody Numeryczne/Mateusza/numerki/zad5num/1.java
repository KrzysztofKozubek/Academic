
class Matrix {
private int nrows;
private int ncols;
private double[][] data;
public Matrix(double[][] dat) {
this.data = dat;
this.nrows = dat.length;
this.ncols = dat[0].length;
}
public Matrix(int nrow, int ncol) {
this.nrows = nrow;
this.ncols = ncol;
data = new double[nrow][ncol];
}
public int getNrows() {
return nrows;
}
public void setNrows(int nrows) {
this.nrows = nrows;
}
public int getNcols() {
return ncols;
}
public void setNcols(int ncols) {
this.ncols = ncols;
}
public double[][] getValues() {
return data;
}
public void setValues(double[][] values) {
this.data = values;
}
public void setValueAt(int row, int col, double value) {
data[row][col] = value;
}
public double getValueAt(int row, int col) {
return data[row][col];
}
public boolean isSquare() {
return nrows == ncols;
}
public int size() {
if (isSquare())
return nrows;
return -1;
}
public Matrix multiplyByConstant(double constant) {
Matrix mat = new Matrix(nrows, ncols);
for (int i = 0; i < nrows; i++) {
for (int j = 0; j < ncols; j++) {
mat.setValueAt(i, j,  data[i][j] * constant);
}
}

return mat;
}
public Matrix insertColumnWithValue1() {
Matrix X_ = new Matrix(this.getNrows(), this.getNcols()+1);
for (int i=0;i<X_.getNrows();i++) {
for (int j=0;j<X_.getNcols();j++) {
if (j==0)
X_.setValueAt(i, j, 1.0);
else
X_.setValueAt(i, j, this.getValueAt(i, j-1));
}
}
return X_;
}
public void show(){
for(int i=0;i<data.length;i++){
System.out.println("");
for(int j=0;j<data[i].length;j++){
System.out.print("|"+data[i][j]+"|");}
}
System.out.print("\n");
}
}
class MatrixMathematics {
public MatrixMathematics(){}
public static Matrix transpose(Matrix matrix) {
Matrix transposedMatrix = new Matrix(matrix.getNcols(), matrix.getNrows());
for (int i=0;i<matrix.getNrows();i++) {
for (int j=0;j<matrix.getNcols();j++) {
transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
}
}
return transposedMatrix;
}
public static Matrix inverse(Matrix matrix) {
return (transpose(cofactor(matrix)).multiplyByConstant(1.0/determinant(matrix)));
}
public static double determinant(Matrix matrix) {
if (matrix.size()==2) {
return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - ( matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
}
double sum = 0.0;
for (int i=0; i<matrix.getNcols(); i++) {
sum += changeSign(i) * matrix.getValueAt(0, i) * determinant(createSubMatrix(matrix, 0, i));
}
return sum;
}
private static int changeSign(int i) {
if (i%2==0)
return 1;
return -1;
}
public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) {
Matrix mat = new Matrix(matrix.getNrows()-1, matrix.getNcols()-1);
int r = -1;
for (int i=0;i<matrix.getNrows();i++) {
if (i==excluding_row)
continue;
r++;
int c = -1;
for (int j=0;j<matrix.getNcols();j++) {
if (j==excluding_col)
continue;
mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
}
}
return mat;
}
public static Matrix cofactor(Matrix matrix)
{
Matrix mat = new Matrix(matrix.getNrows(), matrix.getNcols());
for (int i=0;i<matrix.getNrows();i++) {
for (int j=0; j<matrix.getNcols();j++) {
mat.setValueAt(i, j, changeSign(i) * changeSign(j) * determinant(createSubMatrix(matrix, i, j)));
}
}
return mat;
}
public static Matrix add(Matrix matrix1, Matrix matrix2) {
Matrix sumMatrix = new Matrix(matrix1.getNrows(), matrix1.getNcols());
for (int i=0; i<matrix1.getNrows();i++) {
for (int j=0;j<matrix1.getNcols();j++)
sumMatrix.setValueAt(i, j, matrix1.getValueAt(i, j) + matrix2.getValueAt(i,j));
}
return sumMatrix;
}
public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
return add(matrix1,matrix2.multiplyByConstant(-1));
}
public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
Matrix multipliedMatrix = new Matrix(matrix1.getNrows(), matrix2.getNcols());
for (int i=0;i<multipliedMatrix.getNrows();i++) {
for (int j=0;j<multipliedMatrix.getNcols();j++) {
double sum = 0.0;
for (int k=0;k<matrix1.getNcols();k++) {
sum += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
}
multipliedMatrix.setValueAt(i, j, sum);
}
}
return multipliedMatrix;
}
}
class Start {
public static void main (String[] args){
int x=6;
int y=6;

Matrix o = new Matrix(x,y);
double [][]A= {{19, 13, 10, 10, 13, -17},
{13, 13, 10, 10, -11, 13},
{10, 10, 10, -2, 10, 10},
{10, 10, -2, 10, 10, 10},
{13, -11, 10, 10, 13, 13},
{-17, 13, 10, 10, 13, 19}};
 
System.out.println("-----------------macierz A -----------------------------");
Matrix tab=new Matrix(A);
tab.show();

System.out.println("-----------------macierz A po przemnozeniu przez 1/12-----------------------------");
//System.out.println(tab.multiplyByConstant(1/12));



}
}