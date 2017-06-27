package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 * Base class that is used if one intends to decorate a different distance. For
 * example we may want to scale, shift or ...
 *
 * @author Petr Ryšavý
 * @param <T> The type of compared objects.
 */
public abstract class DecoratedDistance<T> extends AbstractMeasure<T> {

    /** The decorated distance. */
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
