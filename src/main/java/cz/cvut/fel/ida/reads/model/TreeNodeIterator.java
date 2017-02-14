
package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.AbstractIterator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class TreeNodeIterator<T> extends AbstractIterator<TreeNode<T>>
{
    private final Deque<TreeNode<T>> deque;

    public TreeNodeIterator(TreeNode<T> node)
	{
        this.deque = new LinkedList<>();
        deque.add(node);
	}

    @Override
    public boolean hasNext()
    {
        return !deque.isEmpty();
    }

    @Override
    public TreeNode<T> next()
    {
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
