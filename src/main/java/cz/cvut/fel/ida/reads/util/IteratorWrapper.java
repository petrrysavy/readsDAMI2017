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

import java.util.Iterator;

/**
 * The wrapper that enables to use iterator in for loop directly. The code shows
 * how to do that.
 * <pre>
 * for(Type t : new IteratorWrapper<>(iterator))
 * </pre>
 *
 * @param <T> The type of the iterator.
 * @author Petr Ryšavý
 */
public class IteratorWrapper<T> implements Iterable<T> {

    /**
     * The stored refference to the iterator.
     */
    private final Iterator<T> iterator;

    /**
     * Creates new iterator wrapper.
     * @param iterator The iterator to wrap.
     */
    public IteratorWrapper(final Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterator;
    }
}
