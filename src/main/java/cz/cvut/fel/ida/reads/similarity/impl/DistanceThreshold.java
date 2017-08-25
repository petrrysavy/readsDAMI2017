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

import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 * Decorator that thresholds the similarity. If dissimilarity is greater than a
 * threshold, we consider the values to be completely different.
 *
 * @author Petr Ryšavý
 * @param <T> The type of compared objects.
 */
public class DistanceThreshold<T> extends DecoratedDistance<T> {

    /** Threshold for comparing. */
    private final double threshold;
    /** If distance is greater than threshold, this will be returned. */
    private final double maxCost;

    /**
     * Thresholding distance.
     * @param innerDistance This distance is decorated.
     * @param threshold Threshold for comparing.
     * @param maxCost If distance is greater than threshold, this will be
     * returned.
     */
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
