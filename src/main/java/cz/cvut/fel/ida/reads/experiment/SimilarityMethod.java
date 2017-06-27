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
