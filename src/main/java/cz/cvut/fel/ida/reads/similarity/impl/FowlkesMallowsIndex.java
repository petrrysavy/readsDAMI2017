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

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.MathUtils;
import java.util.List;
import java.util.Set;

/**
 * Fowlkes-Mallows index is used for comparing two clusterings.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects in the clustering.
 * @see http://wildfire.stat.ucla.edu/pdflibrary/fowlkes.pdf
 */
public class FowlkesMallowsIndex<T> extends AbstractMeasure<List<Set<T>>> {

    @Override
    public Double getSimilarity(List<Set<T>> aClusters, List<Set<T>> bClusters) {
        assert (aClusters.size() == bClusters.size());

        final int clusters = aClusters.size();

        final int[][] m = new int[clusters][clusters];

        for (int i = 0; i < clusters; i++)
            for (int j = 0; j < clusters; j++)
                m[i][j] = CollectionUtils.intersectionSize(aClusters.get(i), bClusters.get(j));

        final int[] rowSums = MathUtils.rowSum(m);
        final int[] colSums = MathUtils.colSum(m);
        final int n = MathUtils.sum(rowSums);

        final int Tk = MathUtils.sumSquares(m) - n;
        final int Pk = MathUtils.sumSquares(colSums) - n;
        final int Qk = MathUtils.sumSquares(rowSums) - n;

        return ((double) Tk) / Math.sqrt(Pk * Qk);
    }
}
