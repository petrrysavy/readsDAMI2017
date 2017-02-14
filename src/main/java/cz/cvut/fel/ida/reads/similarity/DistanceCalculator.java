package cz.cvut.fel.ida.reads.similarity;

import java.util.Arrays;
import java.util.List;

/**
 * @author Petr Ryšavý
 * @param <T>
 * @param <U>
 */
public interface DistanceCalculator<T, U extends Number> extends Measure {

    public U getDistance(T a, T b);

    public default double[][] getDistanceMatrix(T[] a, T[] b) {
        return getDistanceMatrix(Arrays.asList(a), Arrays.asList(b));
    }

    public default double[][] getDistanceMatrix(T[] values) {
        final boolean isSymmetric = isSymmetric();

        final double[][] distance = new double[values.length][values.length];
        for (int i = 0; i < distance.length; i++)
            if (isSymmetric)
                for (int j = i + 1; j < values.length; j++)
                    distance[j][i] = distance[i][j] = getDistance(values[i], values[j]).doubleValue();
            else
                for (int j = 0; j < values.length; j++)
                    distance[i][j] = getDistance(values[i], values[j]).doubleValue();
        return distance;
    }

    public default double[][] getDistanceMatrix(List<T> aList, List<T> bList) {
        final boolean isSymmetric = isSymmetric() && aList.equals(bList);

        final double[][] distance = new double[aList.size()][bList.size()];
        for (int i = 0; i < distance.length; i++)
            if (isSymmetric)
                for (int j = i + 1; j < bList.size(); j++)
                    distance[j][i] = distance[i][j] = getDistance(aList.get(i), bList.get(j)).doubleValue();
            else
                for (int j = 0; j < bList.size(); j++)
                    distance[i][j] = getDistance(aList.get(i), bList.get(j)).doubleValue();
        return distance;
    }
}
