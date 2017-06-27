package cz.cvut.fel.ida.reads.algo;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.util.MathUtils;
import cz.cvut.fel.ida.reads.util.PairSet;
import cz.cvut.fel.ida.reads.util.TwiceIndexedMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The neighbor-joining clustering algorithm.
 *
 * @author Petr Ryšavý
 * @param <T> Type of clustered objects.
 */
public class NeighborJoining<T> extends AbstractHierarchicalClustering<T> {

    @Override
    public HierarchicalTreeNode<T> buildTree(T[] values, double[][] distanceMatrix) {
        final Map<Integer, HierarchicalTreeNode<T>> nodeList = buildInitialClustering(values);

        // distance from cluster i to all other clusters
        // i.e. sum_(all clusters C') D(C_i, C')
        final Map<Integer, Double> distFromAll = new HashMap<>();

        // calculate the cluster-cluster distances
        final TwiceIndexedMap<Integer, Double> distanceMap = calculateDistanceMap(distanceMatrix);

        // calculate the sums used for u(C) calculation
        for (int i = 0; i < values.length; i++) {
            double distance = 0;
            for (int j = 0; j < values.length; j++)
                if (i != j)
                    distance += distanceMap.get(i, j);
            distFromAll.put(i, distance);
        }

        int newIndex = nodeList.size();

        while (nodeList.size() > 2) {
            // find the closest clusters
            PairSet<Integer> bestPair = null;
            double minDistance = Double.POSITIVE_INFINITY;
            final int numClustersMin2 = nodeList.size() - 2;
            for (Entry<PairSet<Integer>, Double> pair : distanceMap.entrySet())
                if (pair.getValue() * numClustersMin2 - (distFromAll.get(pair.getKey().getValue1()) + distFromAll.get(pair.getKey().getValue2())) < minDistance) {
                    bestPair = pair.getKey();
                    minDistance = pair.getValue() * numClustersMin2 - (distFromAll.get(pair.getKey().getValue1()) + distFromAll.get(pair.getKey().getValue2()));
                }

            final Integer i = bestPair.getValue1();
            final Integer j = bestPair.getValue2();
            minDistance = distanceMap.get(bestPair); // do not forget to fix the minDistance value to D(i,j)

            // merge the two into the new cluster
            final double uC1minus_uC2 = (distFromAll.get(i) - distFromAll.get(j)) / numClustersMin2;
            HierarchicalTreeNode<T> newCluster = new HierarchicalTreeNode<>(nodeList.get(i), (minDistance + uC1minus_uC2) / 2.0, nodeList.get(j), (minDistance - uC1minus_uC2) / 2.0, null);

            nodeList.remove(i);
            nodeList.remove(j);

            for (Integer other : nodeList.keySet())
                // the pseudocode in the book is wrong on this line !!!!
                distanceMap.put(other, newIndex, (distanceMap.removeKey(other, i) + distanceMap.removeKey(other, j) - minDistance) / 2);

            final double averageDistFromAll = MathUtils.average(distFromAll.get(i), distFromAll.get(j));
            distFromAll.put(newIndex, averageDistFromAll - (numClustersMin2 + 2) * minDistance / 2.0);
            distFromAll.remove(i);
            distFromAll.remove(j);
            for (Integer other : nodeList.keySet())
                distFromAll.put(other, distFromAll.get(other) - minDistance - distanceMap.get(other, newIndex));

            distanceMap.remove(bestPair);
            nodeList.put(newIndex++, newCluster);
        }

        Iterator<Integer> lastTwoIterator = nodeList.keySet().iterator();
        final Integer i = lastTwoIterator.next();
        final Integer j = lastTwoIterator.next();
        final double distance = distanceMap.get(i, j) / 2;
        HierarchicalTreeNode<T> root = new HierarchicalTreeNode<>(nodeList.get(i), distance, nodeList.get(j), distance, null);

        return root;
    }
}
