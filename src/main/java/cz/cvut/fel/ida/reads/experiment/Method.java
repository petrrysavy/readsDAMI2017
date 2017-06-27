package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A method compared in the experiments.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects that are being compared. For example
 * {@code ReadsBag} or {@code Sequence}.
 */
public interface Method<T> {

    /**
     * Calculates distance matrix between the objects.
     * @param array List of values that will be used for distance matrix
     * calculation.
     * @return Distance matrix between the objects.
     */
    public double[][] getDistanceMatrix(T[] array);

    /**
     * Builds the hierarchical clustering tree.
     * @param nodes Nodes to cluster.
     * @param distanceMatrix Distance matrix.
     * @param type Type of the tree - UPGMA or NJ.
     * @return The clustering.
     */
    public HierarchicalTreeNode<T> buildTree(T[] nodes, double[][] distanceMatrix, TreeType type);

    /**
     * What kind of input does the method use.
     * @return The type of data used for clustering.
     */
    public Class<T> getInputType();

    /**
     * Name of the method.
     * @return Name, i.e. a string identifier.
     */
    public String getName();

    /**
     * Loads selected files from the specified directory.
     * @param baseDir Location of the experiment.
     * @param fileList List of the files names to load.
     * @return The data to cluster.
     */
    public default T[] loadData(Path baseDir, List<String> fileList) {
        List<Path> files = new ArrayList<>(fileList.size());
        for (String file : fileList)
            files.add(resolve(baseDir, file));
        return loadData(files);
    }

    /**
     * Gets location of a particular file within an experiment directory.
     * @param baseDir The path where does the experiment happen.
     * @param file Name of the file.
     * @return By defualt {@code baseDir/file}.
     */
    public default Path resolve(Path baseDir, String file) {
        return baseDir.resolve(file);
    }

    /**
     * Loads specified data.
     * @param files Files to read.
     * @return Values that are read.
     */
    public T[] loadData(List<Path> files);

    /**
     * Validates that all files have been loaded properly. For example an
     * assembly algorithm may produce an empty file.
     * @param data Data to check.
     * @return {@code true} if data are ready for clustering.
     */
    public default boolean areDataCorrect(T[] data) {
        return true;
    }
}
