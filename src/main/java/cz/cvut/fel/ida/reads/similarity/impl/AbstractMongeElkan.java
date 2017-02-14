package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public abstract class AbstractMongeElkan<T> extends AbstractMeasure<Multiset<T>> {

    protected final DistanceCalculator<T, ? extends Number> innerDistance;

    public AbstractMongeElkan(DistanceCalculator<T, ? extends Number> innerDistance) {
        this.innerDistance = innerDistance;
    }

    @Override
    public abstract Double getDistance(Multiset<T> a, Multiset<T> b);
    
    @Override
    public boolean isSymmetric() {
        return false;
    }

    @Override
    public boolean isZeroOneNormalized() {
        return innerDistance.isZeroOneNormalized();
    }
}
