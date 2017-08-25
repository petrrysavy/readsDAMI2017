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
package cz.cvut.fel.ida.reads.similarity;

import java.util.Arrays;
import java.util.List;

/**
 * A class that is capable of similarity calculation.
 *
 * @author Petr Ryšavý
 * @param <T> The type of compared objects.
 * @param <U> The numerical value of the similarity.
 */
public interface SimilarityCalculator<T, U extends Number> extends Measure {

    /**
     * Calculates the similarity.
     * @param a The first object.
     * @param b The second object.
     * @return Similarity of {@code a} and {@code b} (in this order).
     */
    public U getSimilarity(T a, T b);

    /**
     * Gets matrix of similarities between each pair of objects.
     * @param a List of first arguments.
     * @param b List of second arguments.
     * @return Matrix that contains similarity of i-th element from the first
     * list and j-th from the second.
     */
    public default double[][] getSimilarityMatrix(T[] a, T[] b) {
        return getSimilarityMatrix(Arrays.asList(a), Arrays.asList(b));
    }

    /**
     * Gets square matrix of similarities.
     * @param values Values to compare.
     * @return Similarity of i-th and j-th element of the list.
     */
    public default double[][] getSimilarityMatrix(T[] values) {
        final boolean isSymmetric = isSymmetric();

        final double[][] similarity = new double[values.length][values.length];
        for (int i = 0; i < similarity.length; i++)
            if (isSymmetric)
                for (int j = i + 1; j < values.length; j++)
                    similarity[j][i] = similarity[i][j] = getSimilarity(values[i], values[j]).doubleValue();
            else
                for (int j = 0; j < values.length; j++)
                    similarity[i][j] = getSimilarity(values[i], values[j]).doubleValue();
        return similarity;
    }

    /**
     * Gets matrix of similarities between each pair of objects.
     * @param aList List of first arguments.
     * @param bList List of second arguments.
     * @return Matrix that contains similarity of i-th element from the first
     * list and j-th from the second.
     */
    public default double[][] getSimilarityMatrix(List<T> aList, List<T> bList) {
        final boolean isSymmetric = isSymmetric() && aList.equals(bList);

        final double[][] similarity = new double[aList.size()][bList.size()];
        for (int i = 0; i < similarity.length; i++)
            if (isSymmetric)
                for (int j = i + 1; j < bList.size(); j++)
                    similarity[j][i] = similarity[i][j] = getSimilarity(aList.get(i), bList.get(j)).doubleValue();
            else
                for (int j = 0; j < bList.size(); j++)
                    similarity[i][j] = getSimilarity(aList.get(i), bList.get(j)).doubleValue();
        return similarity;
    }
}
