package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import java.nio.file.Path;
import java.util.List;

/**
 * Method that takes as argument another method and downsamples the number of
 * reads.
 *
 * @author Petr Ryšavý
 */
public class SamplingMethod implements Method<ReadsBag> {

    /** A method that is decorated by the sampling. */
    private final Method<ReadsBag> decoratedMethod;
    /** The sampling ratio. */
    private final double ratio;

    /** Creates a new method that enhances another reads bag method by sampling.
     * @param decoratedMethod A method that is decorated by the sampling.
     * @param ratio The sampling ratio. */
    public SamplingMethod(Method<ReadsBag> decoratedMethod, double ratio) {
        this.decoratedMethod = decoratedMethod;
        this.ratio = ratio;
    }

    @Override
    public double[][] getDistanceMatrix(ReadsBag[] array) {
        return decoratedMethod.getDistanceMatrix(array);
    }

    @Override
    public HierarchicalTreeNode<ReadsBag> buildTree(ReadsBag[] nodes, double[][] distanceMatrix, TreeType type) {
        return decoratedMethod.buildTree(nodes, distanceMatrix, type);
    }

    @Override
    public Class<ReadsBag> getInputType() {
        return ReadsBag.class;
    }

    @Override
    public String getName() {
        return "s" + decoratedMethod.getName();
    }

    @Override
    public ReadsBag[] loadData(List<Path> files) {
        final ReadsBag[] bags = decoratedMethod.loadData(files);
        final ReadsBag[] sampled = new ReadsBag[bags.length];
        for (int i = 0; i < bags.length; i++)
            sampled[i] = bags[i].sample(ratio);
        return sampled;
    }
}
