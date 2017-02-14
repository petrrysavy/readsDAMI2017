package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Jde o Java rozhraní, které
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public interface Method<T> {

    public double[][] getDistanceMatrix(T[] array);

    public HierarchicalTreeNode<T> buildTree(T[] nodes, double[][] distanceMatrix, TreeType type);

    public Class<T> getInputType();

    public String getName();

    public default T[] loadData(Path baseDir, List<String> fileList) {
        List<Path> files = new ArrayList<>(fileList.size());
        for (String file : fileList)
            files.add(resolve(baseDir, file));
        return loadData(files);
    }

    public default Path resolve(Path baseDir, String file) {
        return baseDir.resolve(file);
    }

    public T[] loadData(List<Path> files);

    public default boolean areDataCorrect(T[] data) {
        return true;
    }
}
