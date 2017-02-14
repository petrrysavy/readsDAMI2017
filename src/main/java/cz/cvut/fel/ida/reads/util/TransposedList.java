
package cz.cvut.fel.ida.reads.util;

import java.util.AbstractList;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class TransposedList<T> extends AbstractList<T>
{
    private final List<T> list;

    public TransposedList(List<T> list)
    {
        this.list = list;
    }

    @Override
    public T get(int index)
    {
        return list.get(list.size() - index - 1);
    }

    @Override
    public int size()
    {
        return list.size();
    }
}
