import java.sql.Time;
import java.util.Random;

public class proj1 {
    public static void main(String[] args) {

        int n;
        int Times = 20;
        long TimeForC = 0;
        long TimeForDC = 0;
        long TimeForS = 0;
        long StartTimeC, EndTimeC;
        long StartTimeDC, EndTimeDC;
        long StartTimeS, EndTimeS;
        int[][] arr,arr1;


        for (int i = 1; i > 0; i++){
            n =(int)Math.pow(2,i);

            arr = FillMatrix(n);
            arr1 = FillMatrix(n);

            for(int j = 1; j < Times; j++) {

                StartTimeC = System.nanoTime();
                ClassicalMatrixMultiplication(arr, arr1, n);
                EndTimeC = System.nanoTime();
                TimeForC += EndTimeC - StartTimeC;

                StartTimeDC = System.nanoTime();
                DivideAndConquerMatrixMultiplication(arr, arr1, n);
                EndTimeDC = System.nanoTime();
                TimeForDC += EndTimeDC - StartTimeDC;

                StartTimeS = System.nanoTime();
                StrassenMatrixMultiplication(arr, arr1, n);
                EndTimeS = System.nanoTime();
                TimeForS += EndTimeS - StartTimeS;

            }

            TimeForC = TimeForC / Times;
            TimeForDC = TimeForDC / Times;
            TimeForS = TimeForS / Times;

            System.out.println();
            System.out.println("For n=" + n + ": \n Classic Matrix Multiplication time is: " + TimeForC + " nanoseconds.\n Divide and Conquer Matrix Multiplication time is: " + TimeForDC + " nanoseconds.\n Strassen's Matrix Multiplication time is: " + TimeForS + " nanoseconds.\n");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
        }


    }


    public static  int[][] FillMatrix( int n){

        Random random = new Random();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                matrix[i][j] = random.nextInt(1000);
            }
        }

        return matrix;

    }

    public static int[][] ClassicalMatrixMultiplication(int[][] a, int[][] b, int n){
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    matrix[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return matrix;

    }

    public static void NewMatrix(int[][] OldMatrix, int[][] newMatrix, int a, int b) {

        int c = b;

        for (int i = 0; i < OldMatrix.length; i++) {
            for (int j = 0; j < OldMatrix.length; j++) {
                newMatrix[a][c++] = OldMatrix[i][j];
            }
            c = b;
            a++;
        }
    }

    public static int[][] AddMatrix(int[][] a, int[][] b, int n) {

        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = a[i][j] + b[i][j];
            }
        }
        return matrix;
    }

    private static void DeconstructMatrix(int[][] OldMatrix, int[][] NewMatrix, int a, int b) {

        int c = b;

        for (int i = 0; i < NewMatrix.length; i++) {
            for (int j = 0; j < NewMatrix.length; j++) {
                NewMatrix[i][j] = OldMatrix[a][c++];
            }

            c = b;
            a++;
        }
    }

    public static int[][] DivideAndConquerMatrixMultiplication(int[][] a, int[][] b, int n){
        int[][] matrix = new int[n][n];

        if (n == 1){
            matrix[0][0] = a[0][0] * b[0][0];

        }else {

            int[][] a11 = new int[n / 2][n / 2];
            int[][] a12 = new int[n / 2][n / 2];
            int[][] a21 = new int[n / 2][n / 2];
            int[][] a22 = new int[n / 2][n / 2];
            int[][] b11 = new int[n / 2][n / 2];
            int[][] b12 = new int[n / 2][n / 2];
            int[][] b21 = new int[n / 2][n / 2];
            int[][] b22 = new int[n / 2][n / 2];

            DeconstructMatrix(a, a11, 0, 0);
            DeconstructMatrix(a, a12, 0, n / 2);
            DeconstructMatrix(a, a21, n / 2, 0);
            DeconstructMatrix(a, a22, n / 2, n / 2);
            DeconstructMatrix(b, b11, 0, 0);
            DeconstructMatrix(b, b12, 0, n / 2);
            DeconstructMatrix(b, b21, n / 2, 0);
            DeconstructMatrix(b, b22, n / 2, n / 2);

            int[][] matrix11 = AddMatrix(DivideAndConquerMatrixMultiplication(a11, b11, n / 2), DivideAndConquerMatrixMultiplication(a12, b21, n / 2), n / 2);
            int[][] matrix12 = AddMatrix(DivideAndConquerMatrixMultiplication(a11, b12, n / 2), DivideAndConquerMatrixMultiplication(a12, b22, n / 2), n / 2);
            int[][] matrix21 = AddMatrix(DivideAndConquerMatrixMultiplication(a21, b11, n / 2), DivideAndConquerMatrixMultiplication(a22, b21, n / 2), n / 2);
            int[][] matrix22 = AddMatrix(DivideAndConquerMatrixMultiplication(a21, b12, n / 2), DivideAndConquerMatrixMultiplication(a22, b22, n / 2), n / 2);

            NewMatrix(matrix11, matrix, 0, 0);
            NewMatrix(matrix12, matrix, 0, n / 2);
            NewMatrix(matrix21, matrix, n / 2, 0);
            NewMatrix(matrix22, matrix, n / 2, n / 2);
        }


    return matrix;
    }

    private static int[][] SubtractMatrix(int[][] a, int[][] b, int n) {

        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = a[i][j] - b[i][j];
            }
        }
        return matrix;
    }

    public static int[][] StrassenMatrixMultiplication(int[][] a, int[][] b, int n){
        int[][] matrix = new int[n][n];
        NewStrassenMatrix(a,b,matrix,n);

        return matrix;

    }

    public static void NewStrassenMatrix(int[][] a, int[][] b, int[][] c, int n){
        if (n == 2){
            c[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
            c[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
            c[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
            c[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        }else {
            int[][] a11 = new int[n / 2][n / 2];
            int[][] a12 = new int[n / 2][n / 2];
            int[][] a21 = new int[n / 2][n / 2];
            int[][] a22 = new int[n / 2][n / 2];
            int[][] b11 = new int[n / 2][n / 2];
            int[][] b12 = new int[n / 2][n / 2];
            int[][] b21 = new int[n / 2][n / 2];
            int[][] b22 = new int[n / 2][n / 2];

            int[][] p1 = new int[n / 2][n / 2];
            int[][] p2 = new int[n / 2][n / 2];
            int[][] p3 = new int[n / 2][n / 2];
            int[][] p4 = new int[n / 2][n / 2];
            int[][] p5 = new int[n / 2][n / 2];
            int[][] p6 = new int[n / 2][n / 2];
            int[][] p7 = new int[n / 2][n / 2];

            DeconstructMatrix(a, a11, 0, 0);
            DeconstructMatrix(a, a12, 0, n / 2);
            DeconstructMatrix(a, a21, n / 2, 0);
            DeconstructMatrix(a, a22, n / 2, n / 2);
            DeconstructMatrix(b, b11, 0, 0);
            DeconstructMatrix(b, b12, 0, n / 2);
            DeconstructMatrix(b, b21, n / 2, 0);
            DeconstructMatrix(b, b22, n / 2, n / 2);

            NewStrassenMatrix(AddMatrix(a11, a22, n / 2), AddMatrix(b11, b22, n / 2), p1, n / 2);
            NewStrassenMatrix(AddMatrix(a21, a22, n / 2), b11, p2, n / 2);
            NewStrassenMatrix(a11, SubtractMatrix(b12, b22, n / 2), p3, n / 2);
            NewStrassenMatrix(a22, SubtractMatrix(b21, b11, n / 2), p4, n / 2);
            NewStrassenMatrix(AddMatrix(a11, a12, n / 2), b22, p5, n / 2);
            NewStrassenMatrix(SubtractMatrix(a21, a11, n / 2), AddMatrix(b11, b12, n / 2), p6, n / 2);
            NewStrassenMatrix(SubtractMatrix(a12, a22, n / 2), AddMatrix(b21, b22, n / 2), p7, n / 2);

            int[][] c11 = AddMatrix(SubtractMatrix(AddMatrix(p1, p4, p1.length), p5, p5.length), p7, p7.length);
            int[][] c12 = AddMatrix(p3, p5, p3.length);
            int[][] c21 = AddMatrix(p2, p4, p2.length);
            int[][] c22 = AddMatrix(SubtractMatrix(AddMatrix(p1, p3, p1.length), p2, p2.length), p6, p6.length);

            NewMatrix(c11, c, 0, 0);
            NewMatrix(c12, c, 0, n / 2);
            NewMatrix(c21, c, n / 2, 0);
            NewMatrix(c22, c, n / 2, n / 2);

        }


    }




}
