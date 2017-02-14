package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.MathUtils;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
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
