package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class BestMatchDistance<T> extends AbstractMongeElkan<T>
{
    public BestMatchDistance(DistanceCalculator<T, ? extends Number> innerDistance) {
        super(innerDistance);
    }
    
    @Override
    public boolean isSymmetric() {
        return innerDistance.isSymmetric();
    }

    @Override
    public Double getDistance(Multiset<T> a, Multiset<T> b) {
        final Set<T> bSet = b.toSet();
        double bestMatch = Double.POSITIVE_INFINITY;
        for (T aElem : a.toSet()) {
            for (T bElem : bSet)
                bestMatch = Math.min(innerDistance.getDistance(aElem, bElem).doubleValue(), bestMatch);
        }
        return bestMatch;
    }

}
