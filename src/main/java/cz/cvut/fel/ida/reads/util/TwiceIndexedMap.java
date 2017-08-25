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

import java.util.HashMap;

/**
 * A map with two keys of the same type.
 *
 * @author Petr Ryšavý
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class TwiceIndexedMap<K, V> extends HashMap<PairSet<K>, V> {

    /**
     * Puts a new value to the map.
     * @param key1 The first key.
     * @param key2 The second key.
     * @param value The value.
     * @return The original value.
     */
    public V put(K key1, K key2, V value) {
        return super.put(new PairSet<>(key1, key2), value);
    }

    /**
     * Gets the value based on two keys.
     * @param key1 The first key.
     * @param key2 The second key.
     * @return The value assigned to the key pair.
     */
    public V get(K key1, K key2) {
        return super.get(new PairSet<>(key1, key2));
    }

    /**
     * Removes the value based on two keys.
     * @param key1 The first key.
     * @param key2 The second key.
     * @return The value assigned to the key pair.
     */
    public V removeKey(K key1, K key2) {
        return super.remove(new PairSet<>(key1, key2));
    }
}
