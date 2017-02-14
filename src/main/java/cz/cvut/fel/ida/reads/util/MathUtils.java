package cz.cvut.fel.ida.reads.util;

/**
 *
 * @author Petr Ryšavý
 */
public final class MathUtils {

    private MathUtils() {

    }

    public static int max(int... arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }

    public static int min(int... arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < min)
                min = arr[i];
        return min;
    }

    public static int min(int a, int b, int c) {
        if (a <= b && a <= c)
            return a;
        return b <= c ? b : c;
    }

    public static double min(double... arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < min)
                min = arr[i];
        return min;
    }

    public static int maxIndex(int... arr) {
        if (arr.length == 0)
            return -1;

        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > arr[maxIndex])
                maxIndex = i;
        return maxIndex;
    }

    public static double min(double a, double b, double c) {
        if (a <= b && a <= c)
            return a;
        return b <= c ? b : c;
    }
    
    public static double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    public static int sum(int[] vec) {
        int sum = 0;
        for (int i : vec)
            sum += i;
        return sum;
    }

    public static double sum(double[] vec) {
        double sum = 0;
        for (double i : vec)
            sum += i;
        return sum;
    }

    public static int[] addTo(int[] target, int[] toAdd) {
        for (int i = 0; i < target.length; i++)
            target[i] += toAdd[i];
        return target;
    }

    public static int sumSquares(int[] vec) {
        int sum = 0;
        for (int i : vec)
            sum += i * i;
        return sum;
    }

    public static int sumSquares(int[][] matrix) {
        int sum = 0;
        for (int[] vec : matrix)
            sum += sumSquares(vec);
        return sum;
    }

    public static int[] rowMinIndex(double[][] matrix) {
        final int[] min = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length == 0)
                min[i] = -1;
            else {
                int minIndex = 0;
                for (int j = 1; j < matrix[i].length; j++)
                    if (matrix[i][j] < matrix[i][minIndex])
                        minIndex = j;
                min[i] = minIndex;
            }
        }
        return min;
    }

    public static int[] rowMaxIndex(double[][] matrix) {
        final int[] min = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length == 0)
                min[i] = -1;
            else {
                int minIndex = 0;
                for (int j = 1; j < matrix[i].length; j++)
                    if (matrix[i][j] > matrix[i][minIndex])
                        minIndex = j;
                min[i] = minIndex;
            }
        }
        return min;
    }

    public static int[] colMinIndex(double[][] matrix) {
        final int[] min = new int[matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix.length == 0)
                min[j] = Integer.MAX_VALUE;
            else {
                int minIndex = 0;
                for (int i = 1; i < matrix.length; i++)
                    if (matrix[i][j] < matrix[minIndex][j])
                        minIndex = i;
                min[j] = minIndex;
            }
        }
        return min;
    }

    public static int[] colMaxIndex(double[][] matrix) {
        final int[] min = new int[matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix.length == 0)
                min[j] = Integer.MAX_VALUE;
            else {
                int minIndex = 0;
                for (int i = 1; i < matrix.length; i++)
                    if (matrix[i][j] > matrix[minIndex][j])
                        minIndex = i;
                min[j] = minIndex;
            }
        }
        return min;
    }

    public static int[] rowSum(int[][] matrix) {
        final int[] sum = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++)
            sum[i] = sum(matrix[i]);
        return sum;
    }

    public static int[] colSum(int[][] matrix) {
        final int[] sum = new int[matrix[0].length];
        for (int i = 0; i < sum.length; i++)
            addTo(sum, matrix[i]);
        return sum;
    }

    public static double average(double a, double b) {
        return (a + b) / 2.0;
    }

    public static double average(double... array) {
        return sum(array) / array.length;
    }

    public static int pow(int a, int b) {
        assert (b >= 0);

        int result = 1;
        for (int i = 0; i < b; i++)
            result *= a;
        return result;
    }
}
