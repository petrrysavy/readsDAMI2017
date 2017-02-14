package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.ElementNotFoundException;
import cz.cvut.fel.ida.reads.util.KeyValue;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 */
public class HierarchicalTreeNode<T> extends TreeNode<T>
{
    public final double leftLength;
    public final double rightLength;

    public HierarchicalTreeNode(TreeNode<T> left, double leftLength, TreeNode<T> right, double rightLength, T value)
    {
        super(left, right, value);
        this.leftLength = leftLength;
        this.rightLength = rightLength;
    }

    public HierarchicalTreeNode(T value)
    {
        this(null, -1, null, -1, value);
    }

    @Override
    public Set<TreeNode<T>> cutAtLevel(int k)
    {
        final Set<TreeNode<T>> set = new HashSet<>();

        final PriorityQueue<KeyValue<HierarchicalTreeNode<T>>> queue = new PriorityQueue<>();
        queue.add(new KeyValue<>(0.0, this));

        while (queue.size() + set.size() != k)
        {
            KeyValue<HierarchicalTreeNode<T>> peak = queue.poll();
            HierarchicalTreeNode<T> node = peak.getValue();

            if (node.isLeaf())
                set.add(node);
            else
            {
                queue.add(new KeyValue<>(peak.getKey() + node.leftLength, (HierarchicalTreeNode<T>) node.left));
                queue.add(new KeyValue<>(peak.getKey() + node.rightLength, (HierarchicalTreeNode<T>) node.right));
            }
        }

        for (KeyValue<HierarchicalTreeNode<T>> kv : queue)
            set.add(kv.getValue());
        return set;
    }

    public HierarchicalTreeNode<T> getOtherNode(TreeNode<T> node)
    {
        if (Objects.equals(node, left))
            return (HierarchicalTreeNode<T>) right;
        else if (Objects.equals(node, right))
            return (HierarchicalTreeNode<T>) left;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }

    public double getEdgeLength(TreeNode<T> node)
    {
        if (Objects.equals(node, left))
            return leftLength;
        else if (Objects.equals(node, right))
            return rightLength;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }

    public double getOtherEdgeLength(TreeNode<T> node)
    {
        if (Objects.equals(node, left))
            return rightLength;
        else if (Objects.equals(node, right))
            return leftLength;
        throw new ElementNotFoundException("Cannot find element " + node + " in tree " + this);
    }
}
