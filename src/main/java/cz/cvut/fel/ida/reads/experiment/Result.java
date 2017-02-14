package cz.cvut.fel.ida.reads.experiment;

import java.util.HashMap;

/**
 *
 * @author Petr Ryšavý
 */
public class Result extends HashMap<String, Number> {

    public static final String NUMBER_OF_DATA = "numdata";
    public static final String ASSEMBLY_TIME = "ms/assem";
    public static final String DISTANCE_MATRIX_TIME = "ms/matrix";
    public static final String UPGMA_TREE_BUILD_TIME = "ms/upgma";
    public static final String NJ_TREE_BUILD_TIME = "ms/nj";
    public static final String DISTANCE_MATRIX_CORRELATION = "correlation";
    public static final String UPGMA_FOWLKES_MALLOWS_INDEX = "FM/upgmak";
    public static final String NJ_FOWLKES_MALLOWS_INDEX = "FM/njk";
    public static final String FINISHED = "finished";
    public static final String OUT_OF = "outof";

    private final String methodName;

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
