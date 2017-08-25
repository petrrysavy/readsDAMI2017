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
package cz.cvut.fel.ida.reads.embedded;

import java.util.Iterator;

/**
 * Interface that defines multiset that is embedded to some other space.
 * @author Petr Ryšavý
 * @param <T> Type of the embedded object that is used.
 * @param <K> The original space key.
 * @param <V> The projected key.
 */
public interface EmbeddedMultiset<T extends EmbeddedObject<K, V>, K, V> extends Iterable<K> {

    /** Returns the size of the multiset.
     * @return Size of the multiset. */
    public int size();

    /** The count of a particular object. Should be done in the original space.
     * @param o Object to count.
     * @return Count of {@code o} */
    public int count(Object o);

    /** Iterator of the embedded values.
     * @return Iterator in the embedded space. */
    public Iterator<T> embeddedIterator();

}
