package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.AbstractIterator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Iterator over all children of the root node in a tree.
 *
 * @author Petr Ryšavý
 * @param <T> Type of values stored in the tree.
 */
public class TreeNodeIterator<T> extends AbstractIterator<TreeNode<T>> {

    /** Queue that holds not-yet-explored nodes. */
    private final Deque<TreeNode<T>> deque;

    /**
     * Creates new iterator.
     * @param node The starting point. This will be the first element to return.
     */
    public TreeNodeIterator(TreeNode<T> node) {
        this.deque = new LinkedList<>();
        deque.add(node);
    }

    @Override
    public boolean hasNext() {
        return !deque.isEmpty();
    }

    @Override
    public TreeNode<T> next() {
        if (deque.isEmpty())
            throw new NoSuchElementException("Iterated over all elements.");

        TreeNode<T> t = deque.remove();
        if (t.left != null)
            deque.add(t.left);
        if (t.right != null)
            deque.add(t.right);

        return t;
    }
}
