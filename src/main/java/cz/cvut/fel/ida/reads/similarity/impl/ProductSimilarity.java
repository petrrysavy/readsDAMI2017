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

/**
 * This distance is calculated as product of two simpler distances. It can be
 * understood as a fuzzy conjunction, namely the product conjunction.
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class ProductSimilarity<T> extends AbstractMeasure<T> {

    /**
     * The first distance.
     */
    private final AbstractMeasure<T> distance1;
    /**
     * The second distance.
     */
    private final AbstractMeasure<T> distance2;

    /**
     * Constructs new product distance.
     *
     * @param distance1 The first distance.
     * @param distance2 The second distance.
     */
    public ProductSimilarity(AbstractMeasure<T> distance1, AbstractMeasure<T> distance2) {
        this.distance1 = distance1;
        this.distance2 = distance2;
    }

    /**
     * {@inheritDoc }
     *
     * @return Product of the two distances.
     */
    @Override
    public Double getDistance(T a, T b) {
        return distance1.getDistance(a, b) * distance2.getDistance(a, b);
    }

    /**
     * {@inheritDoc }
     *
     * @return Product of the two similarities.
     */
    @Override
    public Double getSimilarity(T a, T b) {
        return distance1.getSimilarity(a, b) * distance2.getSimilarity(a, b);
    }

    /**
     * {@inheritDoc }
     *
     * @return Product is normalized if both of the distances are normalized.
     */
    @Override
    public boolean isZeroOneNormalized() {
        return distance1.isZeroOneNormalized() && distance2.isZeroOneNormalized();
    }

    /**
     * {@inheritDoc }
     *
     * @return Product is symmetric if both of the distances are symmetric.
     */
    @Override
    public boolean isSymmetric() {
        return distance1.isSymmetric() && distance2.isSymmetric();
    }
}
