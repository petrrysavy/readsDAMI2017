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

import java.util.Objects;

/**
 * An ordered tuple of two values.
 *
 * @author Petr Ryšavý
 * @param <T> Type of the first value.
 * @param <U> Type of the second value.
 */
public class Pair<T, U> {

    /** The first value stored. */
    public final T value1;
    /** The second value stored. */
    public final U value2;

    /**
     * Creates a new tuple object.
     * @param value1 The first value stored.
     * @param value2 The second value stored.
     */
    public Pair(T value1, U value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * Gets the first stored value.
     * @return The first value stored.
     */
    public T getValue1() {
        return value1;
    }

    /**
     * Gets the second stored value.
     * @return The second value stored.
     */
    public U getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.value1);
        hash = 47 * hash + Objects.hashCode(this.value2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(this.value1, other.value1) && Objects.equals(this.value2, other.value2);
    }

    @Override
    public String toString() {
        return "Pair(" + value1 + ", " + value2 + ')';
    }
}
