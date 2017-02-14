package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.AbstractIterator;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class TreeValueIterator<T> extends AbstractIterator<T> {

    private final TreeNodeIterator<T> nodeIterator;

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
