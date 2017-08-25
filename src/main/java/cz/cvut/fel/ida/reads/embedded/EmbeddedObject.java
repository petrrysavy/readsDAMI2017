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

/**
 * Interface that defines embedding of a object.
 * @author Petr Ryšavý
 * @param <K> The original key.
 * @param <V> The projected value.
 */
public class EmbeddedObject<K, V> {

    /** This is the original object. */
    private final K object;
    /** This is the value to that {@code object} is projected. */
    private final V projected;

    /**
     * Creates mew instance.
     * @param object Original object.
     * @param projected It's projection.
     */
    public EmbeddedObject(K object, V projected) {
        this.object = object;
        this.projected = projected;
    }

    /**
     * Gets the original object.
     * @return The original object.
     */
    public K getObject() {
        return object;
    }

    /**
     * The projected value.
     * @return The embedded value.
     */
    public V getProjected() {
        return projected;
    }
}
