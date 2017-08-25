/* 
 * Copyright (C) 2017 Petr Ryšavý <petr.rysavy@fel.cvut.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.model.UnorientedObject;
import java.util.Iterator;

/**
 * Iterator that returns for a sequence also its complement, reverse and
 * reversed complement.
 *
 * @author Petr Ryšavý
 * @param <T> Type of the sequence.
 */
public class UnorientedIterator<T extends UnorientedObject<T>> extends AbstractIterator<T> {

    /** The wrapped iterator. */
    private final Iterator<T> wrapped;
    /** Current element to return. */
    private T current;
    /** Position - what shall we return of the current sequence now. */
    private int pos;

    /**
     * Creates a new unoriented iterator based on another iterator.
     * @param wrapped We decorate this iterator by enhancing it by complement,
     * reverse and reversed complement.
     */
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
