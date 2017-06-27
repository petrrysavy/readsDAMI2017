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
 * Tree node in a binary tree with assigned value.
 *
 * @author Petr Ryšavý
 * @param <T> Value stored in the vertices.
 */
public class TreeNode<T> implements Iterable<T> {

    /** Left child. */
    public final TreeNode<T> left;
    /** Right child. */
    public final TreeNode<T> right;
    /** The content of the node. */
    public final T value;

    /**
     * Creates a new instance of the node with no children.
     * @param value Value stored in the node.
     */
    public TreeNode(T value) {
        this(null, null, value);
    }

    /**
     * Creates a new instance of the node with selected children.
     * @param left Left child.
     * @param right Right child.
     * @param value The content of the node.
     */
    public TreeNode(TreeNode<T> left, TreeNode<T> right, T value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    /**
     * Iterates over all values in the tree.
     * @return Iterator that traverses all children recursively.
     */
    @Override
    public Iterator<T> iterator() {
        return new TreeValueIterator<>(this);
    }

    /**
     * Calculates the depth of the tree.
     * @return Maximum number of edges to reach a leaf.
     */
    public int depth() {
        return 1 + Math.max(left == null ? -1 : left.depth(), right == null ? -1 : right.depth());
    }

    /**
     * Is this node a leaf. This happens if left and right child are both null.
     * @return Is this node a leaf.
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Gets all leaves of this tree.
     * @return Finds recursively all leaves.
     */
    public Set<T> getLeavesSet() {
        Set<T> leaves = new HashSet<>();
        for (TreeNode<T> t : new TreeNodeIterator<>(this))
            if (t.isLeaf())
                leaves.add(t.value);
        return leaves;
    }

    /**
     * Goes down up to k edges and then finds all trees rooted at this level.
     * The values in the leaves of those trees are returned in each set.
     * @param k Number of edges to traverse.
     * @return List of values in leaves grouped by their root at level k.
     */
    public List<Set<T>> clusterizeAtLevel(int k) {
        final Set<TreeNode<T>> cut = cutAtLevel(k);
        final List<Set<T>> clusters = new ArrayList<>();
        for (TreeNode<T> t : cut)
            clusters.add(t.getLeavesSet());
        return clusters;
    }

    /**
     * Goes down up to k edges and then finds all nodes at this level.
     * @param k Number of edges to traverse.
     * @return List of nodes that have distance k from this node.
     */
    public Set<TreeNode<T>> cutAtLevel(int k) {
        final Set<TreeNode<T>> set = new HashSet<>();
        cutAtLevel(k, set);
        return set;
    }

    private void cutAtLevel(int k, Set<TreeNode<T>> acc) {
        if (k == 1) {
            acc.add(this);
            return;
        }
        if (left != null)
            left.cutAtLevel(k - 1, acc);
        if (right != null)
            right.cutAtLevel(k - 1, acc);
    }

    /**
     * Gets the path that leads to a child.
     * @param value Value to find.
     * @param comparator Comparator to compare values in the nodes.
     * @return Path to the value or {@code null} if not found.
     */
    public List<TreeNode<T>> tracePath(T value, Comparator<T> comparator) {
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
