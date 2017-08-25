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
package cz.cvut.fel.ida.reads.similarity;

/**
 * Measure is a distance and similarity calculator.
 *
 * @author Petr Ryšavý
 * @param <T> Type of compared objects.
 */
public abstract class AbstractMeasure<T> implements DistanceCalculator<T, Double>, SimilarityCalculator<T, Double> {

    @Override
    public Double getDistance(T a, T b) {
        if (isZeroOneNormalized())
            return 1.0 - getSimilarity(a, b);
        else
            throw new UnsupportedOperationException("Method getDistance() is not implemented.");
    }

    @Override
    public Double getSimilarity(T a, T b) {
        if (isZeroOneNormalized())
            return 1.0 - getDistance(a, b);
        else
            throw new UnsupportedOperationException("Method getSimilarity() is not implemented.");
    }
}
