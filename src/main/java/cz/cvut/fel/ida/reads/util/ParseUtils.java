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

import java.util.List;
import java.util.StringTokenizer;

/**
 * Utility class for parsing input.
 *
 * @author Petr Ryšavý
 */
public class ParseUtils {

    /** Do not let anybody to instantiate the class. */
    private ParseUtils() {
    }

    /**
     * Captures a list of integers from a string tokenizer, assuming that all
     * tokens are integers.
     * @param st Tokenizer with the input.
     * @param list List, where the result should be added.
     */
    public static void parseAllToIntList(StringTokenizer st, List<Integer> list) {
        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()));
        }
    }

    /**
     * Captures a list of doubles from a string tokenizer, assuming that all
     * tokens are doubles.
     * @param st Tokenizer with the input.
     * @param list List, where the result should be added.
     */
    public static void parseAllToDoubleList(StringTokenizer st, List<Double> list) {
        while (st.hasMoreTokens()) {
            list.add(Double.parseDouble(st.nextToken()));
        }
    }

    /**
     * Captures strings from a string tokenizer.
     * @param st Tokenizer with the input.
     * @param list List, where the result should be added.
     */
    public static void parseAllToStringList(StringTokenizer st, List<String> list) {
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
    }

    /**
     * Parses a boolean value.
     * @param string Boolean value, {@code true} and {@code 1} and {@code yes}
     * are interpreted as true value, opposite as false.
     * @return Parsed boolean value.
     * @throws NumberFormatException When {@code string} is not in form
     * {@code true/false}, {@code 1/0} or {@code yes/no}.
     */
    public static boolean parseBoolean(String string) {
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1") || string.equalsIgnoreCase("yes"))
            return true;
        if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0") || string.equalsIgnoreCase("no"))
            return false;
        throw new NumberFormatException("Unknown boolean value : " + string);
    }
}
