/*
 * Copyright (c) 2016 Petr Rysavy <rysavpe1@fel.cvut.cz>
 *
 * This file is part of the project readsIDA2016, which is available on 
 * <https://github.com/petrrysavy/readsIDA2016/>.
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
 * along with This program.  If not, see <http ://www.gnu.org/licenses/>.
 */
package cz.cvut.fel.ida.reads.model;

import java.util.Collection;
import java.util.Set;

/**
 * Interface that defines a multiset. Multiset is a combination of list and set.
 * Similarly to list a multiset can contain duplicates and similarly to a set it
 * does not store the order. Therefore it can be viewed as a set where each
 * object can be stored multiple times.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects stored in the multiset.
 */
public interface Multiset<T> extends Collection<T> {

    /**
     * Counts the number of occurences of object {@code o}.
     *
     * @param o The object to search.
     * @return Number of occcurences of {@code o} in this multiset.
     */
    public int count(Object o);

    /**
     * Adds an object arbitrary number of times to the multiset.
     *
     * @param object Object to add.
     * @param count  How many times should be object added.
     * @return {@code true} if the objects were added.
     */
    public boolean add(T object, int count);

    /**
     * Calculates union of two multisets. Union contains each object which is at
     * least once in this or the other multiset. The frequencies are calculated
     * as sums of the occurencies in the two original multisets. Therefore the
     * size of the resulting multiset is sum of the sizes of the two original
     * multisets.
     *
     * @param t Union with this multiset should be calculated.
     * @return A new multiset that contains all objects of this multiset and
     *         {@code t}.
     */
    public Multiset<T> union(Multiset<T> t);

    /**
     * Converts this multiset to a set, which means that it removes the
     * duplicates.
     *
     * @return Multiset converted to a set.
     */
    public Set<T> toSet();

    /** {@inheritDoc } */
    @Override
    public default boolean contains(Object o) {
        return count(o) != 0;
    }
}
