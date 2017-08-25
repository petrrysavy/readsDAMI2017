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

import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;

/**
 * Base class for distances based on Monge-Elkan distance.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects that are compared in multisets.
 */
public abstract class AbstractMongeElkan<T> extends AbstractMeasure<Multiset<T>> {

    /** The distance used for comparing objects within mltisets. */
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
