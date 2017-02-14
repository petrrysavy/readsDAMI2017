package cz.cvut.fel.ida.reads.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Petr Ryšavý
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> T[] asArray2(T... vals) {
        return vals;
    }

    public static double[] asArray(double... array) {
        return array;
    }

    public static int[] asArray(int... array) {
        return array;
    }

    public static <T> T[] genericArray(Class<T> clazz, int length) {
        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clazz, length);
        return arr;
    }

    public static int numElements(double[][] array2D) {
        int num = 0;
        for (double[] array : array2D)
            num += array.length;
        return num;
    }

    public static double[] flattern(double[][] array2D) {
        final double[] target = new double[numElements(array2D)];
        int index = 0;
        for (double[] array : array2D) {
            System.arraycopy(array, 0, target, index, array.length);
            index += array.length;
        }
        return target;
    }

    public static double[] nTimes(double value, int n) {
        final double[] arr = new double[n];
        Arrays.fill(arr, value);
        return arr;
    }

    public static char[] reversedCopy(char[] arr) {
        final char[] reversed = new char[arr.length];
        for (int i = 0; i < arr.length; i++)
            reversed[i] = arr[arr.length - i - 1];
        return reversed;
    }
    
    public static double[] ensureLength(double[] arr, int length) {
        if(arr.length > length)
            return arr;
        return new double[length];
    }
}
