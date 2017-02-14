package cz.cvut.fel.ida.reads.util;

import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Petr Ryšavý
 */


public class ParseUtils {

    private ParseUtils() {};
    
    public static void parseAllToIntList(StringTokenizer st, List<Integer> list) {
        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()));
        }
    }

    public static void parseAllToDoubleList(StringTokenizer st, List<Double> list) {
        while (st.hasMoreTokens()) {
            list.add(Double.parseDouble(st.nextToken()));
        }
    }
    
    public static void parseAllToStringList(StringTokenizer st, List<String> list) {
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
    }
    
    public static boolean parseBoolean(String string) {
        if(string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1") || string.equalsIgnoreCase("yes"))
            return true;
        if(string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0") || string.equalsIgnoreCase("no"))
            return false;
        throw new NumberFormatException("Unknown boolean value : "+string);
    }
}
