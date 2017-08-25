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
package cz.cvut.fel.ida.reads.experiment;

import java.util.HashMap;

/**
 * Simple result of the experiment. Uses a map that stores for each parameter a
 * number with the measurement.
 *
 * @author Petr Ryšavý
 */
public class Result extends HashMap<String, Number> {

    /** Number of clustered objects. */
    public static final String NUMBER_OF_DATA = "numdata";
    /** Time needed to assembly reads. */
    public static final String ASSEMBLY_TIME = "ms/assem";
    /** Time to calculate distance matrix. */
    public static final String DISTANCE_MATRIX_TIME = "ms/matrix";
    /** Time to run UPGMA on already calculated distance matrix. */
    public static final String UPGMA_TREE_BUILD_TIME = "ms/upgma";
    /** Time to run neighbor-joining on already calculated distance matrix. */
    public static final String NJ_TREE_BUILD_TIME = "ms/nj";
    /** Pearson's correlation coefficient of the distance matrix and the
     * reference one. */
    public static final String DISTANCE_MATRIX_CORRELATION = "correlation";
    /** Folwkes-Mallows index on UPGMA trees. */
    public static final String UPGMA_FOWLKES_MALLOWS_INDEX = "FM/upgmak";
    /** Folwkes-Mallows index on neighbor-joining trees. */
    public static final String NJ_FOWLKES_MALLOWS_INDEX = "FM/njk";
    /** Zero or one value that shows whether the algorithm finished. For
     * averaged results this shows number of occurences when this algorithm did
     * finish. */
    public static final String FINISHED = "finished";
    /** From how many trials did the algorithm succeed. Makes sense only for
     * averaged results. */
    public static final String OUT_OF = "outof";

    /** Name of the method. */
    private final String methodName;

    /** Constructs new result.
     * @param methodName Name of the method. */
    public Result(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return String.format("result [method=%s, finished=%d]", getMethodName(), get(FINISHED));
    }
}
