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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of multiset that uses a hash map. The map stores objects as
 * keys and counts as values.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects stored in the multiset.
 */
public class HashMultiset<T> extends AbstractMultiset<T> implements Cloneable {

    /**
     * This map holds the state of the multiset.
     */
    private HashMap<T, Integer> map;

    /**
     * Size of this multiset.
     */
    private int size = 0;

    /**
     * Constructs an empty multiset.
     */
    public HashMultiset() {
        map = new HashMap<>();
    }

    /**
     * Constructs an empty multiset and fills it with all elements form the
     * collection.
     *
     * @param initialElements Elements to fill the collection with.
     */
    public HashMultiset(Collection<T> initialElements) {
        this(initialElements.size());
        addAll(initialElements);
    }

    /**
     * Constructs an empty multiset with selected initial capacity of the
     * backing hash map.
     *
     * @param initialCapacity The initial capacity.
     */
    public HashMultiset(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    /**
     * Constructs a new multiset with selected initial capapcity and load
     * factor.
     *
     * @param initialCapacity The initial capacity of the map.
     * @param loadFactor Load factor of the map. Growing the map does not depend
     * on duplicates.
     */
    public HashMultiset(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Creates an empty multiset and fills it with the elements from the
     * parameter list.
     *
     * @param elements Elements to put into the multiset.
     */
    public HashMultiset(T... elements) {
        this(elements.length);
        addAll(Arrays.asList(elements));
    }

    /**
     * Constructs a new iterator. The iterator iterates over all element
     * including the duplicates.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new MultisetIterator();
    }

    /**
     * {@inheritDoc }
     * Returns number of elements in this multiset including duplicates.
     *
     * @return The number of elements in the multiset.
     */
    @Override
    public int size() {
        return size;
    }

    public int uniqueSize() {
        return map.size();
    }

    /**
     * {@inheritDoc }
     * This implementation sets the size to zero and clears the backing hash
     * map.
     */
    @Override
    public void clear() {
        size = 0;
        map.clear();
    }

    /**
     * {@inheritDoc} This implementation removes a single occurence of the
     * object.
     */
    @Override
    @SuppressWarnings({"unchecked", "element-type-mismatch"})
    public boolean remove(Object o) {
        Integer count = count(o);
        if (count == 0)
            return false;

        size--;
        if (count > 1)
            map.put((T) o, count - 1);
        else
            map.remove(o);
        return true;
    }

    /**
     * {@inheritDoc} Adds a single instance of the object to the multiset.
     */
    @Override
    public boolean add(T e) {
        return add(e, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T e, int count) {
        if (count <= 0)
            throw new IllegalArgumentException("Illegal count: " + count);

        Integer currentCount = count(e);
        currentCount += count;
        size += count;
        map.put(e, currentCount);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<T> toSet() {
        return new HashSet<>(map.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMultiset<T> union(Multiset<T> other) {
        HashMultiset<T> copy = this.clone();
        for (T elem : other.toSet())
            copy.add(elem, other.count(elem));
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count(Object o) {
        @SuppressWarnings("element-type-mismatch")
        Integer count = map.get(o);
        if (count == null)
            return 0;
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * {@inheritDoc} Two multisets are equal iff they contain the same elements
     * and all the elements have the same counts in both collections.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final HashMultiset<?> other = (HashMultiset<?>) obj;
        // the sizes of the maps may be equal, but this may be still violated
        if (this.size != other.size)
            return false;
        return Objects.equals(this.map, other.map);
    }

    /**
     * {@inheritDoc }
     * Hash code of the backing hash map.
     */
    @Override
    public int hashCode() {
        return map.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    protected HashMultiset<T> clone() {
        try {
            @SuppressWarnings("unchecked")
            HashMultiset<T> copy = (HashMultiset<T>) super.clone();
            copy.size = size;
            copy.map = (HashMap<T, Integer>) map.clone();
            return copy;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * A multiset iterator that wraps iterator of the backing hash map.
     */
    private class MultisetIterator implements Iterator<T> {

        /**
         * Iterator of the hash map.
         */
        private final Iterator<Entry<T, Integer>> iterator = map.entrySet().iterator();
        /**
         * Current entry.
         */
        private Entry<T, Integer> current = null;
        /**
         * How many times we have returned an element of the current entry.
         */
        private int count = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext() || (current != null && count < current.getValue());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T next() {
            // this happens only after the first call.
            if (current == null)
                current = iterator.next();

            // we cannot return the current element anymore, look for the next entry in the map
            if (current.getValue() <= count) {
                current = iterator.next();
                count = 0;
            }

            // increase count and return key
            count++;
            return current.getKey();
        }
    }
}
