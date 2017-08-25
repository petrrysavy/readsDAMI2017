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
package cz.cvut.fel.ida.reads.model;

/**
 * Definition of a sequence that does not have orientation. In DNA sequence we
 * do not know strand or orientation from 5' end to 3' end.
 *
 * @author Petr Ryšavý
 * @param <T> Type of the class. Should be the subclass itself.
 */
public interface UnorientedObject<T extends UnorientedObject<T>> {

    /**
     * Gets the reverse. Reverse contains symbols from the end to the beginning.
     * @return The reverse.
     */
    public T reverse();

    /**
     * Gets the complement. Complement has A and T nucleotides switched.
     * Similarly Gs and Cs are switched.
     * @return The complementary sequence.
     */
    public T complement();

    /**
     * Combination of reverse and complement. This is how read looks like if it
     * is on the second strand.
     * @return Reversed complement.
     */
    public default T reverseComplement() {
        return reverse().complement();
    }

}
