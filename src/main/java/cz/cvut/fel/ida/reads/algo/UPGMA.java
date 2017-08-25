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
import cz.cvut.fel.ida.reads.util.PairSet;
import cz.cvut.fel.ida.reads.util.TwiceIndexedMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The UPGMA hierarchical clustering algorithm.
 *
 * @author Petr Ryšavý
 * @param <T> Type of clustered objects.
 */
public class UPGMA<T> extends AbstractHierarchicalClustering<T> {

    @Override
    public HierarchicalTreeNode<T> buildTree(T[] values, double[][] distanceMatrix) {
        Map<Integer, HierarchicalTreeNode<T>> nodeList = buildInitialClustering(values);
        final double[] h = new double[2 * values.length - 1];
        final int[] size = new int[2 * values.length - 1];
        Arrays.fill(size, 0, values.length, 1);

        // calculate the cluster-cluster distances
        final TwiceIndexedMap<Integer, Double> distanceMap = calculateDistanceMap(distanceMatrix);

        int newIndex = nodeList.size();

        while (nodeList.size() > 1) {
            // find the closest clusters
            PairSet<Integer> bestPair = null;
            double minDistance = Double.POSITIVE_INFINITY;
            for (Entry<PairSet<Integer>, Double> pair : distanceMap.entrySet())
                if (pair.getValue() < minDistance) {
                    bestPair = pair.getKey();
                    minDistance = pair.getValue();
                }

            final Integer i = bestPair.getValue1();
            final Integer j = bestPair.getValue2();

            // merge the two into the new cluster
            h[newIndex] = minDistance / 2.0;
            HierarchicalTreeNode<T> newCluster = new HierarchicalTreeNode<>(nodeList.get(i), h[newIndex] - h[i], nodeList.get(j), h[newIndex] - h[j], null);
            size[newIndex] = size[i] + size[j];

            nodeList.remove(i);
            nodeList.remove(j);

            for (Integer other : nodeList.keySet())
                distanceMap.put(other, newIndex, (size[i] * distanceMap.removeKey(other, i) + size[j] * distanceMap.removeKey(other, j)) / (size[newIndex]));

            distanceMap.remove(bestPair);
            nodeList.put(newIndex++, newCluster);
        }

        return nodeList.values().iterator().next();
    }
}
