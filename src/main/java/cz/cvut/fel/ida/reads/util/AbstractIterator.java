
package cz.cvut.fel.ida.reads.util;

import java.util.Iterator;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public abstract class AbstractIterator<T> implements Iterator<T>, Iterable<T>
{
    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
}
