package cz.cvut.fel.ida.reads.util;

import java.util.List;

/**
 * Utility class for manipulating strings.
 *
 * @author Petr Ryšavý
 */
public final class StringUtils {

    /** Do not let anybody to instantiate the class. */
    private StringUtils() {
    }

    /**
     * Converts a list of trings to an array of characters.
     * @param stringList List of strings, for example {@code ["abc", "def"]}.
     * @return A char array, for example {@code [a, b, c, d, e, f]}.
     */
    public static char[] toCharArray(List<String> stringList) {
        char[] array = new char[countChars(stringList)];
        int destIndex = 0;
        for (String s : stringList) {
            System.arraycopy(s.toCharArray(), 0, array, destIndex, s.length());
            destIndex += s.length();
        }
        return array;
    }

    /**
     * Counts how many characters are there in a list of strings.
     * @param stringList List of strings, for example {@code ["abc", "def"]}.
     * @return Count of characters in the list, for example {@code 6}.
     */
    public static int countChars(List<String> stringList) {
        int length = 0;
        for (String s : stringList)
            length += s.length();
        return length;
    }

    /**
     * Crates a new string that is formed by {@code k} copies of the input
     * string.
     * @param st The pattern, for example {@code "ab"}.
     * @param k The count, for example {@code 3}.
     * @return {@code st} copies {@code k}-times. For example {@code "ababab"}.
     */
    public static String kCopies(String st, int k) {
        StringBuilder sb = new StringBuilder(st.length() * k);
        for (int i = 0; i < k; i++)
            sb.append(st);
        return sb.toString();
    }

    /**
     * Prints an array of strings to a single string, making sure that all
     * strings are aligned in {@code spaces} blocks.
     * @param arr Array of strings.
     * @param spaces Width of the column.
     * @return Array in single string, the output is similar to format
     * {@code %5s %5s %5s ...}.
     */
    public static String toStringSpaced(String[] arr, int spaces) {
        final String printf = "%" + spaces + "s ";
        StringBuilder sb = new StringBuilder(arr.length * 12 + 2);
        for (String st : arr)
            sb.append(String.format(printf, st));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
