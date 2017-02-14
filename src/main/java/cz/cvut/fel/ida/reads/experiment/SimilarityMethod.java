package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.algo.NeighborJoining;
import cz.cvut.fel.ida.reads.algo.UPGMA;
import static cz.cvut.fel.ida.reads.experiment.TreeType.NEIGHBOR_JOINING_TREE;
import static cz.cvut.fel.ida.reads.experiment.TreeType.UPGMA_TREE;
import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public abstract class SimilarityMethod<T> implements Method<T> {

    private final DistanceCalculator<T, ? extends Number> similarity;
    private final String name;

    public SimilarityMethod(DistanceCalculator<T, ? extends Number> similarity, String name) {
        this.similarity = similarity;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double[][] getDistanceMatrix(T[] array) {
        return similarity.getDistanceMatrix(array, array);
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
