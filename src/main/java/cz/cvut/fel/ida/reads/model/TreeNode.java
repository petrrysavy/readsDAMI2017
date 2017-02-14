package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 */
public class TreeNode<T> implements Iterable<T>
{
    public final TreeNode<T> left;
    public final TreeNode<T> right;
    public final T value;

    public TreeNode(T value) {
        this(null, null, value);
    }

    public TreeNode(TreeNode<T> left, TreeNode<T> right, T value)
    {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new TreeValueIterator<>(this);
    }

    public int depth()
    {
        return 1 + Math.max(left == null ? -1 : left.depth(), right == null ? -1 : right.depth());
    }

    public boolean isLeaf()
    {
        return left == null && right == null;
    }

    public Set<T> getLeavesSet()
    {
        Set<T> leaves = new HashSet<>();
        for (TreeNode<T> t : new TreeNodeIterator<>(this))
            if (t.isLeaf())
                leaves.add(t.value);
        return leaves;
    }

    public List<Set<T>> clusterizeAtLevel(int k)
    {
        final Set<TreeNode<T>> cut = cutAtLevel(k);
        final List<Set<T>> clusters = new ArrayList<>();
        for (TreeNode<T> t : cut)
            clusters.add(t.getLeavesSet());
        return clusters;
    }

    public Set<TreeNode<T>> cutAtLevel(int k)
    {
        final Set<TreeNode<T>> set = new HashSet<>();
        cutAtLevel(k, set);
        return set;
    }

    private void cutAtLevel(int k, Set<TreeNode<T>> acc)
    {
        if (k == 1)
        {
            acc.add(this);
            return;
        }
        if (left != null)
            left.cutAtLevel(k - 1, acc);
        if (right != null)
            right.cutAtLevel(k - 1, acc);
    }

    public List<TreeNode<T>> tracePath(T value, Comparator<T> comparator)
    {
        // we found the target
        if (comparator == null ? Objects.equals(value, this.value) : comparator.compare(value, this.value) == 0)
            return CollectionUtils.asList(this);

        List<TreeNode<T>> path = null;
        if (left != null)
            path = left.tracePath(value, comparator);
        if (path == null && right != null)
            path = right.tracePath(value, comparator);
        if (path != null)
            path.add(this);

        return path;
    }
}
