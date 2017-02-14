package cz.cvut.fel.ida.reads.util;

import java.util.List;

/**
 *
 * @author Petr Ryšavý
 */
public final class StringUtils {

    private StringUtils() {

    }

    public static char[] toCharArray(List<String> stringList) {
        char[] array = new char[countChars(stringList)];
        int destIndex = 0;
        for (String s : stringList) {
            System.arraycopy(s.toCharArray(), 0, array, destIndex, s.length());
            destIndex += s.length();
        }
        return array;
    }

    public static int countChars(List<String> stringList) {
        int length = 0;
        for (String s : stringList)
            length += s.length();
        return length;
    }

    public static String kCopies(String st, int k) {
        StringBuilder sb = new StringBuilder(st.length() * k);
        for (int i = 0; i < k; i++)
            sb.append(st);
        return sb.toString();
    }
    
    public static String toStringSpaced(String[] arr, int spaces) {
        final String printf = "%"+spaces+"s ";
        StringBuilder sb = new StringBuilder(arr.length * 12 + 2);
        for (String st : arr)
            sb.append(String.format(printf, st));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
