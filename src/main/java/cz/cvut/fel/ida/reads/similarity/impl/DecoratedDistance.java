package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 *
 * @author Petr Ryšavý
 */
public abstract class DecoratedDistance<T> extends AbstractMeasure<T> {

    private final DistanceCalculator<T, ? extends Number> innerDistance;

    public DecoratedDistance(DistanceCalculator<T, ? extends Number> innerDistance) {
        this.innerDistance = innerDistance;
    }

    @Override
    public Double getDistance(T a, T b) {
        return innerDistance.getDistance(a, b).doubleValue();
    }

    @Override
    public boolean isZeroOneNormalized() {
        return innerDistance.isZeroOneNormalized();
    }

    @Override
    public boolean isSymmetric() {
        return innerDistance.isSymmetric();
    }

}
