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

import cz.cvut.fel.ida.reads.algo.NeighborJoining;
import cz.cvut.fel.ida.reads.algo.UPGMA;
import static cz.cvut.fel.ida.reads.experiment.TreeType.NEIGHBOR_JOINING_TREE;
import static cz.cvut.fel.ida.reads.experiment.TreeType.UPGMA_TREE;
import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 * A method that uses distance calculator to build tree.
 *
 * @author Petr Ryšavý
 * @param <T> The type of clustered objects.
 */
public abstract class SimilarityMethod<T> implements Method<T> {

    /** The distance measure that calculates the distance matrix. */
    private final DistanceCalculator<T, ? extends Number> distance;
    /** Name of the method. */
    private final String name;

    /**
     * New method instance.
     * @param similarity The distance measure that calculates the distance
     * matrix.
     * @param name Name of the method.
     */
    public SimilarityMethod(DistanceCalculator<T, ? extends Number> similarity, String name) {
        this.distance = similarity;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double[][] getDistanceMatrix(T[] array) {
        return distance.getDistanceMatrix(array);
    }

    @Override
    public HierarchicalTreeNode<T> buildTree(T[] nodes, double[][] distanceMatrix, TreeType type) {
        switch (type) {
            case UPGMA_TREE:
                return new UPGMA<T>().buildTree(nodes, distanceMatrix);
            case NEIGHBOR_JOINING_TREE:
                return new NeighborJoining<T>().buildTree(nodes, distanceMatrix);
            default:
                throw new IllegalArgumentException("Should not happen");
        }
    }

}
