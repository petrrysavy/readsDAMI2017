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
package cz.cvut.fel.ida.reads.algo;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.Pair;
import cz.cvut.fel.ida.reads.util.TwiceIndexedMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for hierarchical clustering algorithms.
 *
 * @author Petr Ryšavý
 * @param <T> The type of clustered objects.
 */
public abstract class AbstractHierarchicalClustering<T> {

    /**
     * Builds initial map that will store distances.
     *
     * @param distanceMatrix Matrix of distances. This must be a square matrix,
     * otherwise may cause errors.
     * @return Map that will map each pair of coordinates to the corresponding
     * distance from the matrix.
     */
    protected TwiceIndexedMap<Integer, Double> calculateDistanceMap(double[][] distanceMatrix) {
        final TwiceIndexedMap<Integer, Double> distanceMap = new TwiceIndexedMap<>();
        for (int i = 0; i < distanceMatrix.length; i++)
            for (int j = i + 1; j < distanceMatrix.length; j++)
                distanceMap.put(i, j, distanceMatrix[i][j]);
        return distanceMap;
    }

    /**
     * Initial clustering. Each value represents its own cluster.
     *
     * @param values Values to be clustered.
     * @return Map from index to tree node.
     */
    protected Map<Integer, HierarchicalTreeNode<T>> buildInitialClustering(T[] values) {
        final Map<Integer, HierarchicalTreeNode<T>> nodeList = new HashMap<>(values.length);
        for (int i = 0; i < values.length; i++)
            nodeList.put(i, new HierarchicalTreeNode<>(values[i]));
        return nodeList;
    }

    /**
     * Builds hierarchical clustering from list of values and distance function.
     *
     * @param values List of values to be clustered.
     * @param dc Distance function.
     * @return Pair of resulting tree and calculated distance matrix.
     */
    public Pair<HierarchicalTreeNode<T>, double[][]> buildTree(T[] values, DistanceCalculator<T, ?> dc) {
        if (!dc.isSymmetric())
            throw new IllegalArgumentException("Hierarchical clustering can be done only on symmetric measures.");

        double[][] distanceMatrix = dc.getDistanceMatrix(values);
        return new Pair<>(buildTree(values, distanceMatrix), distanceMatrix);
    }

    /**
     * Builds hierarchical clustering from list of values and distance matrix.
     *
     * @param values List of values to be clustered.
     * @param distanceMatrix Distance matrix.
     * @return Pair of resulting tree and calculated distance matrix.
     */
    public abstract HierarchicalTreeNode<T> buildTree(T[] values, double[][] distanceMatrix);
}
