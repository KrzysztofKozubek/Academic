import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class FactoryLU {

    double[][] matrix = null;
    double[][] matrixL = null;
    double[][] matrixU = null;
    int size = 0;
    int m = 0;
    int[] piv;

    public FactoryLU(int size) {

        matrix = new double[size][size];
        matrixL = new double[size][size];
        matrixU = new double[size][size];
        this.size = size;

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.abs(random.nextInt() % 10);
                if (i == j) matrixL[i][j] = 1;
                else matrixL[i][j] = 0;
                matrixU[i][j] = 0;
            }
        }
    }

    public FactoryLU() {
        matrix = new double[1][1];
        matrixL = new double[1][1];
        matrixU = new double[1][1];
        this.size = 1;
    }

    public FactoryLU(int size, double... value) {

        if (Math.pow(size, 2) == value.length) {
            matrix = new double[size][size];
            matrixU = new double[size][size];
            matrixL = new double[size][size];
            int help = 0;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    matrix[i][j] = value[help];
                    help++;
                    matrixU[i][j] = 0;

                    if (i == j) matrixL[i][j] = 1;
                    else matrixL[i][j] = 0;
                }
            }
        } else {
            System.out.println("You give a mistake values.. Pleas give ones more.\nMember first number it's size matrix and member value gives rows");
        }
    }

    public FactoryLU(double[][] matrix) {

        this.matrix = matrix;
        matrixL = new double[matrix.length][matrix.length];
        matrixU = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            matrixL[i][i] = 1;
        }
    }


    public double[][] copyMatrix(double[][] matrix) {

        int size = matrix.length;
        double[][] result = new double[size][size];

        return copyMatrix(matrix, result);
    }

    public double[][] copyMatrix(double[][] matrix, double[][] toMatrix) {

        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                toMatrix[i][j] = matrix[i][j];
            }
        }
        return toMatrix;
    }

    public int factorial(int result) {

        if (result <= 1) return result;
        else return result * factorial(result - 1);
    }

    public void swapRows(double[][] matrix, int fromRow, int withRow) {

        int size = matrix.length;
        double help = 0;

        for (int i = 0; i < size; i++) {
            help = matrix[fromRow][i];
            matrix[fromRow][i] = matrix[withRow][i];
            matrix[withRow][i] = help;
        }
    }

    public void swapColumns(double[][] matrix, int fromColumn, int withColumn) {

        int size = matrix.length;
        double help = 0;

        for (int i = 0; i < size; i++) {
            help = matrix[i][fromColumn];
            matrix[i][fromColumn] = matrix[i][withColumn];
            matrix[i][withColumn] = help;
        }
    }

    public void showMatrix(double[][] matrix) {

        System.out.println();

        for (double[] va : matrix) {
            for (double var : va) {
                System.out.print(var + " ");
            }
            System.out.println();
        }
    }

    public void showResult(boolean result) {

        if (result) {
            System.out.print("MACIERZ");
            showMatrix(matrix);
            System.out.print("MACIERZ L");
            showMatrix(matrixL);
            System.out.print("MACIERZ U");
            showMatrix(matrixU);
        } else {
            System.out.println("Nie potrafię dobrze rozwiązać tej macierzy");
        }
    }

    public void permutation(double[][] matrix, int number) {

        int size = matrix.length;

        for (int i = number; i < size; i++) {
            swapRows(matrix, number, i);
            for (int j = number; j < size; j++) {

                swapColumns(matrix, number, j);
            }
        }
    }

    public double findValueU(double[][] matrix, double[][] matrixL, double[][] matrixU, int x, int y) {

        return (matrix[x][y] - multiplication(matrixL, matrixU, x, y));
    }

    public double findValueL(double[][] matrix, double[][] matrixL, double[][] matrixU, int x, int y) {

        return (matrix[x][y] - multiplication(matrixL, matrixU, x, y)) / matrixU[y][y];
    }

    public boolean factoringLU(double[][] matrix, double[][] matrixL, double[][] matrixU) {

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (i <= j)
                    matrixU[i][j] = findValueU(matrix, matrixL, matrixU, i, j);
            }

            for (int j = 0; j < size; j++) {

                if (matrixU[i][i] == 0) return false;
                if (i < j)
                    matrixL[j][i] = findValueL(matrix, matrixL, matrixU, j, i);

            }
        }

        return true;
    }

    public boolean equals(double[][] matrix, double[][] matrixL, double[][] matrixU) {

        int size = matrix.length;
        double[][] help = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ( Math.abs(Math.abs(matrix[i][j]) - Math.abs(multiplication(matrixL, matrixU, i, j))) > 1)
                    return false;
            }
        }

        return true;
    }

    public boolean factoringLU() {

        double[][] matrix = copyMatrix(this.matrix);
        double[][] matrixL = copyMatrix(this.matrixL);
        double[][] matrixU = copyMatrix(this.matrixU);

        int to = (int) Math.pow(factorial(size), 2);

        for (int end = 0; end < to; end++) {

            if (!factoringLU(matrix, matrixL, matrixU))
                permutation(matrix, end);
            else {
                copyMatrix(matrix, this.matrix);
                copyMatrix(matrixL, this.matrixL);
                copyMatrix(matrixU, this.matrixU);

                if (equals(matrix, matrixL, matrixU))
                    return true;
            }
        }

        return false;
    }

    public double multiplication(double[][] matrix, double[][] matrix2, int x, int y) {

        int size = matrix.length;
        double result = 0;

        for (int i = 0; i < size; i++) {

            result += matrix[x][i] * matrix2[i][y];
        }

        return result;
    }

    public double[][] multiplication(double[][] matrix, double[][] matrix2) {

        int size = matrix.length;
        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = multiplication(matrix, matrix2, i, j);
            }
        }

        return result;
    }

    public static void main(String[] args) {


        int size = 4;
        FactoryLU factory = new FactoryLU(size);



        System.out.print("Matrix A input:");
        factory.showMatrix(factory.matrix);
        System.out.println();

        factory.showResult(factory.factoringLU());

        System.out.println();
        System.out.print("Check result:");
        factory.showMatrix(factory.multiplication(factory.matrixL, factory.matrixU));
    }
}
