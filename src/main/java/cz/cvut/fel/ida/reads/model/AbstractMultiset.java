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

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of multiset.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects stored in the multiset.
 */
public abstract class AbstractMultiset<T> extends AbstractCollection<T> implements Multiset<T> {

    /**
     * {@inheritDoc }
     *
     * @return A HashSet that contains all of the objects in this multiset
     *         without duplicates.
     */
    @Override
    public Set<T> toSet() {
        return new HashSet<>(this);
    }

}
