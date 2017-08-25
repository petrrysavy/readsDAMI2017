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

import java.util.AbstractList;
import java.util.List;

/**
 * A list that wraps another one and pretends that the elements are in reversed
 * order. This functionaly holds only of reading.
 *
 * @author Petr Ryšavý
 * @param <T> Type of elements in the list.
 */
public class TransposedList<T> extends AbstractList<T> {

    /** The wrapped list. */
    private final List<T> list;

    /**
     * Creates a new instance of the list.
     * @param list The list that will be virtually reversed.
     */
    public TransposedList(List<T> list) {
        this.list = list;
    }

    @Override
    public T get(int index) {
        return list.get(list.size() - index - 1);
    }

    @Override
    public int size() {
        return list.size();
    }
}
