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
package cz.cvut.fel.ida.reads.similarity;

/**
 * Measure can calculate similarity and/or distance. It has some properties.
 *
 * @author Petr Ryšavý
 */
public interface Measure {

    /**
     * Tests whether this measure is symmetric. The measure is symmetric if for
     * all a, b hodls that dist(a,b)=dist(b,a).
     * @return Is this measure symmetric.
     */
    public default boolean isSymmetric() {
        return true;
    }

    /**
     * Tests whether this measure is normalized to values between zero and one.
     * That means that for all a : dist(a) is between 0 and 1.
     * @return Is this measure normalized to unit interval.
     */
    public default boolean isZeroOneNormalized() {
        return false;
    }
}
