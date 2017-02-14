
package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.TreeNode;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class TreeNodeFowlkesMallowsIndex<T> extends AbstractMeasure<TreeNode<T>>
{
    private final FowlkesMallowsIndex<T> clusterIndex;
    private int k;

    public TreeNodeFowlkesMallowsIndex(FowlkesMallowsIndex<T> clusterIndex)
    {
        this.clusterIndex = clusterIndex;
    }

    public int getK()
    {
        return k;
    }

    public void setK(int k)
    {
        this.k = k;
    }

    @Override
    public Double getSimilarity(TreeNode<T> a, TreeNode<T> b)
    {
        final List<Set<T>> aClusters = a.clusterizeAtLevel(k);
        final List<Set<T>> bClusters = b.clusterizeAtLevel(k);

        assert (aClusters.size() == bClusters.size());

        return clusterIndex.getSimilarity(aClusters, bClusters);
    }
}
