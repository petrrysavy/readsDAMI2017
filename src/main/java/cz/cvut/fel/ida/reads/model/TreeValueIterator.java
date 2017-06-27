package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.AbstractIterator;

/**
 * Iterator over values stored in tree.
 *
 * @author Petr Ryšavý
 * @param <T> Types of values stored in tree.
 */
public class TreeValueIterator<T> extends AbstractIterator<T> {

    /** We will iterate over nodes and then return their values. */
    private final TreeNodeIterator<T> nodeIterator;

    /**
     * Creates the new iterator.
     * @param node The starting point.
     */
    public TreeValueIterator(TreeNode<T> node) {
        this.nodeIterator = new TreeNodeIterator<>(node);
    }

    @Override
    public boolean hasNext() {
        return nodeIterator.hasNext();
    }

    @Override
    public T next() {
        return nodeIterator.next().value;
    }
}
