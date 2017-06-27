package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.ArrayUtils;
import cz.cvut.fel.ida.reads.util.MathUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * A symmetric Monge-Elkan distance. This is calculated by taking average of
 * both directions.
 *
 * @author Petr Ryšavý
 * @param <T> Type of objects in the multisets.
 */
public class SymmetricMongeElkanDistance<T> extends AbstractMongeElkan<T> {

    public SymmetricMongeElkanDistance(DistanceCalculator<T, ? extends Number> innerDistance) {
        super(innerDistance);
    }

    @Override
    public Double getDistance(Multiset<T> a, Multiset<T> b) {
        final List<T> aList = new ArrayList<>(a.toSet());
        final List<T> bList = new ArrayList<>(b.toSet());

        final double[] rowMin = ArrayUtils.nTimes(Double.MAX_VALUE, aList.size());
        final double[] colMin = ArrayUtils.nTimes(Double.MAX_VALUE, bList.size());

        for (int i = 0; i < rowMin.length; i++)
            for (int j = 0; j < colMin.length; j++) {
                final double dist = innerDistance.getDistance(aList.get(i), bList.get(j)).doubleValue();
                rowMin[i] = Math.min(rowMin[i], dist);
                colMin[j] = Math.min(colMin[j], dist);
            }

        double similarityA = 0.0;
        for (int i = 0; i < rowMin.length; i++)
            similarityA += rowMin[i] * a.count(aList.get(i));
        double similarityB = 0.0;
        for (int j = 0; j < colMin.length; j++)
            similarityB += colMin[j] * b.count(bList.get(j));

        return MathUtils.average(similarityA / a.size(), similarityB / b.size());
    }

    @Override
    public boolean isSymmetric() {
        return true;
    }
}
