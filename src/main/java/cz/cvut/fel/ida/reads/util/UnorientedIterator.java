package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.model.UnorientedObject;
import java.util.Iterator;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class UnorientedIterator<T extends UnorientedObject<T>> extends AbstractIterator<T> {
    private final Iterator<T> wrapped;
    private T current;
    private int pos;

    public UnorientedIterator(Iterator<T> wrapped) {
        this.wrapped = wrapped;
        pos = 4;
    }

    @Override
    public boolean hasNext() {
        return pos != 4 || wrapped.hasNext();
    }

    @Override
    public T next() {
        if (pos == 4) {
            current = wrapped.next();
            pos = 0;
        }

        T retval = null;
        switch (pos) {
            case 0:
                retval = current;
                break;
            case 1:
                retval = current.reverse();
                break;
            case 2:
                retval = current.complement();
                break;
            case 3:
                retval = current.reverseComplement();
                break;
        }
        pos++;
        return retval;
    }
}
