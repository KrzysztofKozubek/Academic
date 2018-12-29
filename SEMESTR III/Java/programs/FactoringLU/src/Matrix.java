
public class Matrix {

    private double[][] matrix;

    private int row;
    private int column;

    public Matrix(double[][] array) {

        this.row = array.length;
        this.column = array[0].length;
        this.matrix = array;
    }

    public Matrix(int paramInt1, int paramInt2) {

        this.row = paramInt1;
        this.column = paramInt2;
        this.matrix = new double[paramInt1][paramInt2];
    }

    public double[][] getArray() {
        return this.matrix;
    }

    public double[][] getArrayCopy() {

        double[][] arrayOfDouble = new double[this.row][this.column];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                arrayOfDouble[i][j] = this.matrix[i][j];
            }
        }
        return arrayOfDouble;
    }

    public double determinationU(){

        double result = 1;
        int size = matrix.length;
        for(int i =0;i < size;i++){
            result *= matrix[i][i];
        }
        return result;
    }

    public int getRowDimension() {

        return this.row;
    }

    public int getColumnDimension() {

        return this.column;
    }

    public void showMatrix(String communicat) {

        System.out.println("\n" + communicat);
        for (double[] va : matrix) {
            for (double var : va) {
                System.out.printf("%-7.2f ", var);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void showMatrix(double[][] matrix, String communicat) {

        new Matrix(matrix).showMatrix(communicat);
    }

    public static void showPivot(int[] pivot) {

        int size = pivot.length;

        System.out.println("\nMatrix P");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (pivot[i] == j)
                    System.out.print(" 1 ");
                else
                    System.out.print(" 0 ");
            }
            System.out.println();
        }
    }


    public void nothing(){
        System.out.println();
        //spr czy działa github :P
    }

    public static double[][] getPivot(int[] pivot, double determination) {

        int size = pivot.length;

        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (pivot[i] == j)
                    result[i][j] = 1/ determination;
                else
                    result[i][j] = 0;
            }
        }
        return result;
    }
}
