package cz.cvut.fel.ida.reads.algo;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.Pair;
import cz.cvut.fel.ida.reads.util.TwiceIndexedMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Petr Ryšavý
 */
public abstract class AbstractHierarchicalClustering<T> {

    protected TwiceIndexedMap<Integer, Double> calculateDistanceMap(T[] values, double[][] distanceMatrix) {
        final TwiceIndexedMap<Integer, Double> distanceMap = new TwiceIndexedMap<>();
        for (int i = 0; i < values.length; i++)
            for (int j = i + 1; j < values.length; j++)
                distanceMap.put(i, j, distanceMatrix[i][j]);
        System.err.println("distances calculated");
        return distanceMap;
    }

    protected Map<Integer, HierarchicalTreeNode<T>> buildInitialClustering(T[] values) {
        final Map<Integer, HierarchicalTreeNode<T>> nodeList = new HashMap<>(values.length);
        for (int i = 0; i < values.length; i++)
            nodeList.put(i, new HierarchicalTreeNode<>(values[i]));
        return nodeList;
    }

    public Pair<HierarchicalTreeNode<T>, double[][]> buildTree(T[] values, DistanceCalculator<T, ?> dc) {
        if (!dc.isSymmetric())
            throw new IllegalArgumentException("Hierarchical clustering can be done only on symmetric measures.");

        double[][] distanceMatrix = dc.getDistanceMatrix(values, values);
        return new Pair<>(buildTree(values, distanceMatrix), distanceMatrix);
    }

    public abstract HierarchicalTreeNode<T> buildTree(T[] values, double[][] distanceMatrix);
}
