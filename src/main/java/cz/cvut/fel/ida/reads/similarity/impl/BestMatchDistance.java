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
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.util.Set;

/**
 * A simple class that compares two multisets by taking distance of two most
 * similar objects in the sets.
 *
 * @author Petr Ryšavý
 * @param <T> The type of objects in the multisets.
 */
public class BestMatchDistance<T> extends AbstractMongeElkan<T> {

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
