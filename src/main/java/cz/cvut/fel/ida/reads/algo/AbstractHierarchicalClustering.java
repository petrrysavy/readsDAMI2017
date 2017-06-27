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
