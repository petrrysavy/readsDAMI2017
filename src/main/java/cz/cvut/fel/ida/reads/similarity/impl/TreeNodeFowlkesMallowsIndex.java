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
package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.TreeNode;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import java.util.List;
import java.util.Set;

/**
 * Fowlkes-Mallows index defined on two hierarchical clusterings.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects in the clusterings.
 */
public class TreeNodeFowlkesMallowsIndex<T> extends AbstractMeasure<TreeNode<T>> {

    /** Index for calculating distance between cluster objects. */
    private final FowlkesMallowsIndex<T> clusterIndex;
    /** The level at which we cut the clusterings. */
    private int k;

    public TreeNodeFowlkesMallowsIndex() {
        this.clusterIndex = new FowlkesMallowsIndex<>();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public Double getSimilarity(TreeNode<T> a, TreeNode<T> b) {
        final List<Set<T>> aClusters = a.clusterizeAtLevel(k);
        final List<Set<T>> bClusters = b.clusterizeAtLevel(k);

        assert (aClusters.size() == bClusters.size());

        return clusterIndex.getSimilarity(aClusters, bClusters);
    }
}
