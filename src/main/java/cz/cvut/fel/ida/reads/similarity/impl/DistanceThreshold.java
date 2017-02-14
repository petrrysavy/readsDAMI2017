package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 * Decorator that thresholds the similarity. If dissimilarity is greater than a
 * threshold, we consider the values to be completely different.
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class DistanceThreshold<T> extends DecoratedDistance<T> {

    private final double threshold;
    private final double maxCost;

    public DistanceThreshold(DistanceCalculator<T, ? extends Number> innerDistance, double threshold, double maxCost) {
        super(innerDistance);
        this.threshold = threshold;
        this.maxCost = maxCost;
    }

    @Override
    public Double getDistance(T a, T b) {
        final double distance = super.getDistance(a, b);
        return distance >= threshold ? maxCost : distance;
    }
}
