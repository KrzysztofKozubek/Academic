
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.*;

public class LU {

    public double[][] matrix;

    public int row;
    public int column;

    public int pivoting;
    public int[] pivot;

    public static int indexK;
    public static int indexJ;

    public LU(Matrix paramMatrix) {

        this.matrix = paramMatrix.getArrayCopy();
        this.row = paramMatrix.getRowDimension();
        this.column = paramMatrix.getColumnDimension();
        this.pivot = new int[row];

        repleacePivot(pivot, row);
        this.pivoting = 1;

        double[] vector = new double[row];
        initialization(vector);
    }


    public void initialization(double[] vector) {

        for (int j = 0; j < column; j++) {
            for (int k = 0; k < row; k++)
                vector[k] = matrix[k][j];

            double d = determination(vector, j);

            indexK = j;
            for (int i1 = j + 1; i1 < this.row; i1++)
                if (abs(vector[i1]) > abs(vector[indexK])) indexK = i1;


            if (indexK != j) {
                for (indexJ = 0; indexJ < this.column; indexJ++) {
                    d = this.matrix[indexK][indexJ];
                    this.matrix[indexK][indexJ] = this.matrix[j][indexJ];
                    this.matrix[j][indexJ] = d;
                }

                indexJ = this.pivot[indexK];
                this.pivot[indexK] = this.pivot[j];
                this.pivot[j] = indexJ;
                this.pivoting = (-this.pivoting);
            }


            if (((j < this.row ? 1 : 0) & (this.matrix[j][j] != 0.0D ? 1 : 0)) != 0)
                for (indexJ = j + 1; indexJ < this.row; indexJ++)
                    this.matrix[indexJ][j] /= this.matrix[j][j];
        }
    }

    public double determination(double[] vector, int j) {

        double result = 0;
        for (indexK = 0; indexK < row; indexK++) {
            double[] arrayOfDouble1 = matrix[indexK];


            indexJ = min(indexK, j);
            result = 0.0D;
            for (int i2 = 0; i2 < indexJ; i2++)
                result += arrayOfDouble1[i2] * vector[i2];

            int tmp = indexK;
            double[] tmp_array = vector;
            double tmp2 = (tmp_array[tmp] - result);
            tmp_array[tmp] = tmp2;
            arrayOfDouble1[j] = tmp2;
        }

        return result;
    }

    public static void repleacePivot(int[] pivot, int row) {

        for (int i = 0; i < row; i++) pivot[i] = i;
    }

    public Matrix getL() {

        Matrix result = new Matrix(row, column);
        double[][] arrayOfDouble = result.getArray();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i > j) {
                    arrayOfDouble[i][j] = matrix[i][j];
                } else if (i == j) {
                    arrayOfDouble[i][j] = 1.0D;
                } else {
                    arrayOfDouble[i][j] = 0.0D;
                }
            }
        }
        return result;
    }

    public Matrix getU() {

        Matrix result = new Matrix(column, column);
        double[][] arrayOfDouble = result.getArray();

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                if (i <= j) {
                    arrayOfDouble[i][j] = matrix[i][j];
                } else {
                    arrayOfDouble[i][j] = 0.0D;
                }
            }
        }
        return result;
    }

    public int[] getPivot() {

        int[] result = new int[row];

        for (int i = 0; i < row; i++) {
            result[i] = pivot[i];
        }
        return result;
    }

    public static double multiplicationIndex(double[][] matrix1, double[][] matrix2, int x, int y) {

        int size = matrix1.length;
        double result = 0;
        for (int i = 0; i < size; i++) {

            result += matrix1[x][i] * matrix2[i][y];
        }

        return result;
    }

    public static double[][] multiplication(double[][] matrix1, double[][] matrix2) {

        int size = matrix1.length;
        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                result[i][j] = multiplicationIndex(matrix1, matrix2, i, j);
            }
        }
        return result;
    }

    public static void correctPUL(double[][] matrixP, double[][] matrixL, double[][] matrixU) {

        //double[][] result = multiplication(matrixL, matrixU);
        //result = multiplication(matrixP, result);

        Matrix.showMatrix((multiplication(
                multiplication(matrixP, matrixL), matrixU)), "Check question:");
    }

    public static double[][] preaperDate() {

        boolean correct = true;
        double[][] result;
        int size = 0;
        Scanner input;

        do {
            input = new Scanner(System.in);
            try {
                System.out.print("Please get size matrix: ");
                size = input.nextInt();
            } catch (InputMismatchException ex) {
                correct = false;
            }
        } while (!correct);

        result = new double[size][size];


        correct = false;
        while (!correct) {

            input = new Scanner(System.in);
            correct = true;
            int how = (int) Math.pow(size, 2);
            for (int i = 0; i < size && correct == true; i++) {
                for (int j = 0; j < size && correct == true; j++) {

                    try {
                        System.out.print("Please get value matrix " + how + " yet: ");
                        result[i][j] = input.nextDouble();
                    } catch (InputMismatchException ex) {
                        correct = false;
                    }

                    how--;
                }
            }
        }

        return result;
    }

    public static String LUDecomposition(double[][] array) {

        Matrix A = new Matrix(array);
        LU lu = new LU(A);

        Matrix L = lu.getL();
        Matrix U = lu.getU();
        int[] p = lu.getPivot();

        double[][] printL = L.getArray();
        double[][] printU = U.getArray();

        Matrix.showPivot(p);
        L.showMatrix("Matrix L");
        U.showMatrix("Matrix U");

        //correctPUL(Matrix.getPivot(p, U.determinationU()), printL, printL);

        return "END";
    }
}
