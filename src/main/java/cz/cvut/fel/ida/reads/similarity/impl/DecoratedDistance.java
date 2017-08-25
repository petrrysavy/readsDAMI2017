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
