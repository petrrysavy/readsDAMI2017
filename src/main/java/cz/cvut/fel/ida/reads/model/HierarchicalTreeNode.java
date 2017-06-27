package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.ElementNotFoundException;
import cz.cvut.fel.ida.reads.util.KeyValue;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of the tree representing hierarchical clustering. This class
 * add edges lengths to the tree representation.
 *
 * @author Petr Ryšavý
 * @param <T> Type of objects stored within tree vertices.
 */
public class HierarchicalTreeNode<T> extends TreeNode<T> {

    /** Length of the left edge. */
    public final double leftLength;
    /** Length of the right edge. */
    public final double rightLength;

    /**
     * Constructs new node instance.
     * @param left Left child.
     * @param leftLength Length of the edge that connects this node to the left
     * child.
     * @param right Right child.
     * @param rightLength Length of the edge that connects this node to the
     * right child.
     * @param value Value stored within this node.
     */
    public HierarchicalTreeNode(TreeNode<T> left, double leftLength, TreeNode<T> right, double rightLength, T value) {
        super(left, right, value);
        this.leftLength = leftLength;
        this.rightLength = rightLength;
    }

    /**
     * Constructs a tree node with no children.
     * @param value Value stored within this node.
     */
    public HierarchicalTreeNode(T value) {
        this(null, -1, null, -1, value);
    }

    /**
     * We split the tree to obtain k roots. This is done iteratively. First
     * there is only root for k=1. Then we replace root with its two childs. If
     * k is higher we expand the shallower leaf and so on.
     * @param k Number of final nodes in the list.
     * @return k independent subtrees.
     */
    @Override
    public Set<TreeNode<T>> cutAtLevel(int k) {
        final Set<TreeNode<T>> set = new HashSet<>();

        final PriorityQueue<KeyValue<HierarchicalTreeNode<T>>> queue = new PriorityQueue<>();
        queue.add(new KeyValue<>(0.0, this));

        while (queue.size() + set.size() != k) {
            KeyValue<HierarchicalTreeNode<T>> peak = queue.poll();
            HierarchicalTreeNode<T> node = peak.getValue();

            if (node.isLeaf())
                set.add(node);
            else {
                queue.add(new KeyValue<>(peak.getKey() + node.leftLength, (HierarchicalTreeNode<T>) node.left));
                queue.add(new KeyValue<>(peak.getKey() + node.rightLength, (HierarchicalTreeNode<T>) node.right));
            }
        }

        for (KeyValue<HierarchicalTreeNode<T>> kv : queue)
            set.add(kv.getValue());
        return set;
    }

    /**
     * Gets the other child.
     * @param node One of the children.
     * @return The other children.
     * @throws ElementNotFoundException When {@code node} is not a child of this
     * node.
     */
    public HierarchicalTreeNode<T> getOtherNode(TreeNode<T> node) {
        if (Objects.equals(node, left))
            return (HierarchicalTreeNode<T>) right;
        else if (Objects.equals(node, right))
            return (HierarchicalTreeNode<T>) left;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }

    /**
     * Gets length of the edge that connects {@code node} to this node.
     * @param node Node to find.
     * @return Length of edge that connects {@code node} and this node.
     * @throws ElementNotFoundException When {@code node} is not child of this
     * node.
     */
    public double getEdgeLength(TreeNode<T> node) {
        if (Objects.equals(node, left))
            return leftLength;
        else if (Objects.equals(node, right))
            return rightLength;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }

    /**
     * Gets length of the edge that connects the child, which is not equal to
     * {@code node}, to this node.
     * @param node Node to find.
     * @return Length of edge that connects the other child and this node.
     * @throws ElementNotFoundException When {@code node} is not child of this
     * node.
     */
    public double getOtherEdgeLength(TreeNode<T> node) {
        if (Objects.equals(node, left))
            return rightLength;
        else if (Objects.equals(node, right))
            return leftLength;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }
}
