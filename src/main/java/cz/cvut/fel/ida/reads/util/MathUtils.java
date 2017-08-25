/* 
 * Copyright (C) 2017 Petr Ryšavý <petr.rysavy@fel.cvut.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.fel.ida.reads.util;

/**
 * A utility class for mathematics functions.
 *
 * @author Petr Ryšavý
 */
public final class MathUtils {

    /** Do not let anybody to instantiate the class. */
    private MathUtils() {
    }

    /**
     * Finds maximum from an array.
     * @param arr Array of numbers.
     * @return The maximum.
     * @throws ArrayIndexOutOfBoundsException When array is empty.
     */
    public static int max(int... arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }

    /**
     * Finds minimum from an array.
     * @param arr Array of numbers.
     * @return The minimum.
     * @throws ArrayIndexOutOfBoundsException When array is empty.
     */
    public static int min(int... arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < min)
                min = arr[i];
        return min;
    }

    /**
     * Finds minimum of three values.
     * @param a First value.
     * @param b Second value.
     * @param c Third value.
     * @return The minimum of {@code a}, {@code b} and {@code c}.
     */
    public static int min(int a, int b, int c) {
        if (a <= b && a <= c)
            return a;
        return b <= c ? b : c;
    }

    /**
     * Finds minimum from an array.
     * @param arr Array of numbers.
     * @return The minimum.
     * @throws ArrayIndexOutOfBoundsException When array is empty.
     */
    public static double min(double... arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < min)
                min = arr[i];
        return min;
    }

    /**
     * Finds index of maximum element in an array.
     * @param arr Array of numbers.
     * @return The index of maximum.
     */
    public static int maxIndex(int... arr) {
        if (arr.length == 0)
            return -1;

        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > arr[maxIndex])
                maxIndex = i;
        return maxIndex;
    }

    /**
     * Finds minimum of three values.
     * @param a First value.
     * @param b Second value.
     * @param c Third value.
     * @return The minimum of {@code a}, {@code b} and {@code c}.
     */
    public static double min(double a, double b, double c) {
        if (a <= b && a <= c)
            return a;
        return b <= c ? b : c;
    }

    /**
     * Finds minimum of four values.
     * @param a First value.
     * @param b Second value.
     * @param c Third value.
     * @param d Fourth value.
     * @return The minimum of {@code a}, {@code b}, {@code c} and {@code d}.
     */
    public static double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    /**
     * Sums all numbers in an array. Does not check overflows/underflows.
     * @param vec Array of numbers.
     * @return Sum of elements.
     */
    public static int sum(int[] vec) {
        int sum = 0;
        for (int i : vec)
            sum += i;
        return sum;
    }

    /**
     * Sums all numbers in an array. Does not check overflows/underflows.
     * @param vec Array of numbers.
     * @return Sum of elements.
     */
    public static double sum(double[] vec) {
        double sum = 0;
        for (double i : vec)
            sum += i;
        return sum;
    }

    /**
     * Sums two arrays of numbers and stores the result in the first argument.
     * @param target The first summand and the place where we will store the
     * result.
     * @param toAdd The summand.
     * @return Pointer to {@code target}.
     */
    public static int[] addTo(int[] target, int[] toAdd) {
        for (int i = 0; i < target.length; i++)
            target[i] += toAdd[i];
        return target;
    }

    /**
     * Sums squares of all numbers in an array. Does not check
     * overflows/underflows.
     * @param vec Array of numbers.
     * @return Sum of squares of elements.
     */
    public static int sumSquares(int[] vec) {
        int sum = 0;
        for (int i : vec)
            sum += i * i;
        return sum;
    }

    /**
     * Sums squares of all numbers in an array. Does not check
     * overflows/underflows.
     * @param vec Array of numbers.
     * @return Sum of squares of elements.
     */
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
