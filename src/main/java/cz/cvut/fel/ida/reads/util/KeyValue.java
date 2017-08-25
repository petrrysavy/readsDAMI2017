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

/**
 * Tuple that stores a value together with double key.
 *
 * @author Petr Ryšavý
 */
public class KeyValue<T> implements Comparable<KeyValue<T>> {

    /** The key. */
    private final double key;
    /** The value assigned to this key. */
    private final T value;

    /**
     * Creates a new tuple with key and value.
     * @param key Key.
     * @param value Value.
     */
    public KeyValue(double key, T value) {
        this.key = key;
        this.value = value;
    }

    public double getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int compareTo(KeyValue<T> o) {
        return Double.compare(this.key, o.key);
    }

}
