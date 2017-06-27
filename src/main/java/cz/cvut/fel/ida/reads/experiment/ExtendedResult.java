package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;

/**
 * Result class that stores some additional information about the experiment.
 * @author Petr Ryšavý
 */
public class ExtendedResult extends Result {

    /** The tree built by UPGMA algorithm. */
    private HierarchicalTreeNode upgmaTree;
    /** The tree build by Neighbor-joining algorithm. */
    private HierarchicalTreeNode njTree;
    /** Matrix of distances. */
    private double[] distances;

    /**
     * Constructs new result instance.
     * @param methodName Name of the method which is captured by this result.
     * @param upgmaTree The tree built by UPGMA algorithm.
     * @param njTree The tree build by Neighbor-joining algorithm.
     * @param distances Matrix of distances.
     */
    public ExtendedResult(String methodName, HierarchicalTreeNode upgmaTree, HierarchicalTreeNode njTree, double[] distances) {
        super(methodName);
        this.upgmaTree = upgmaTree;
        this.njTree = njTree;
        this.distances = distances;
    }

    public HierarchicalTreeNode getUpgmaTree() {
        return upgmaTree;
    }

    public void setUpgmaTree(HierarchicalTreeNode upgmaTree) {
        this.upgmaTree = upgmaTree;
    }

    public HierarchicalTreeNode getNjTree() {
        return njTree;
    }

    public void setNjTree(HierarchicalTreeNode njTree) {
        this.njTree = njTree;
    }

    public double[] getDistances() {
        return distances;
    }

    public void setDistances(double[] distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return String.format("%30s %10d %10d %10d %10d %10d %10d %12.10f  %s  %s",
                getMethodName(),
                get(Result.FINISHED).intValue(),
                get(Result.NUMBER_OF_DATA).intValue(),
                getOrDefault(Result.ASSEMBLY_TIME, -1).longValue(),
                getOrDefault(Result.DISTANCE_MATRIX_TIME, -1).longValue(),
                getOrDefault(Result.UPGMA_TREE_BUILD_TIME, -1).longValue(),
                getOrDefault(Result.NJ_TREE_BUILD_TIME, -1).longValue(),
                getOrDefault(Result.DISTANCE_MATRIX_CORRELATION, Double.NaN).doubleValue(),
                RunExperiment.printFMIndex(Result.UPGMA_FOWLKES_MALLOWS_INDEX, this),
                RunExperiment.printFMIndex(Result.NJ_FOWLKES_MALLOWS_INDEX, this)
        );
    }

}
