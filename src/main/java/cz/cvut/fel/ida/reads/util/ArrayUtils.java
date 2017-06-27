package cz.cvut.fel.ida.reads.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Utility class for manipulating arrays.
 * @author Petr Ryšavý
 */
public final class ArrayUtils {

    /** Do not let anybody to instantiate the class. */
    private ArrayUtils() {
    }

    /**
     * Converts list of arguments to an array.
     * @param <T> Type of values.
     * @param vals This will be in the array.
     * @return Array with the values from {@code vals}.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> T[] asArray2(T... vals) {
        return vals;
    }

    /**
     * Converts list of arguments to an array.
     * @param array This will be in the array.
     * @return Array with the values from {@code vals}.
     */
    public static double[] asArray(double... array) {
        return array;
    }

    /**
     * Converts list of arguments to an array.
     * @param array This will be in the array.
     * @return Array with the values from {@code vals}.
     */
    public static int[] asArray(int... array) {
        return array;
    }

    /**
     * Creates a generic type array.
     * @param <T> The type of the array.
     * @param clazz The type of the elements in the array.
     * @param length Length of the new array.
     * @return New array.
     */
    public static <T> T[] genericArray(Class<T> clazz, int length) {
        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clazz, length);
        return arr;
    }

    /**
     * Counts number of elements in a 2D array. Note that the subarrays do not
     * need to have the same length.
     * @param array2D An array.
     * @return The number of elements in {@code array2D}.
     */
    public static int numElements(double[][] array2D) {
        int num = 0;
        for (double[] array : array2D)
            num += array.length;
        return num;
    }

    /**
     * Converts a 2D array to a list by taking elements from each of the
     * subarrays and putting them in a row.
     * @param array2D Array to flattern.
     * @return All elements of the {@code array2D}, however in a 1D vector.
     */
    public static double[] flattern(double[][] array2D) {
        final double[] target = new double[numElements(array2D)];
        int index = 0;
        for (double[] array : array2D) {
            System.arraycopy(array, 0, target, index, array.length);
            index += array.length;
        }
        return target;
    }

    /**
     * Creates a new array that contains {@code n}-times {@code value}.
     * @param value Value to be in the array.
     * @param n The count.
     * @return Array that contains {@code n}-times value {@code value}.
     */
    public static double[] nTimes(double value, int n) {
        final double[] arr = new double[n];
        Arrays.fill(arr, value);
        return arr;
    }

    /**
     * Creates a copy of the array that contains values from the last character
     * to the first character.
     * @param arr Array to be reversed.
     * @return Copy of {@code arr} with elements from the end to the beginning.
     */
    public static char[] reversedCopy(char[] arr) {
        final char[] reversed = new char[arr.length];
        for (int i = 0; i < arr.length; i++)
            reversed[i] = arr[arr.length - i - 1];
        return reversed;
    }
}
