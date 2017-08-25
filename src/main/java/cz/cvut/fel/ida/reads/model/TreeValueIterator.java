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

import cz.cvut.fel.ida.reads.util.AbstractIterator;

/**
 * Iterator over values stored in tree.
 *
 * @author Petr Ryšavý
 * @param <T> Types of values stored in tree.
 */
public class TreeValueIterator<T> extends AbstractIterator<T> {

    /** We will iterate over nodes and then return their values. */
    private final TreeNodeIterator<T> nodeIterator;

    /**
     * Creates the new iterator.
     * @param node The starting point.
     */
    public TreeValueIterator(TreeNode<T> node) {
        this.nodeIterator = new TreeNodeIterator<>(node);
    }

    @Override
    public boolean hasNext() {
        return nodeIterator.hasNext();
    }

    @Override
    public T next() {
        return nodeIterator.next().value;
    }
}
