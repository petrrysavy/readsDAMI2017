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
import java.util.Collection;

/**
 * This measure compares two collections by taking maximum of their sizes. This is
 * sometimes a handy upper bound.
 *
 * @author Petr Ryšavý
 * @param <T> Type of objects in collections.
 */
public class MaxSizeSimilarity<T extends Collection> extends AbstractMeasure<T> {

    @Override
    public Double getSimilarity(T a, T b) {
        throw new UnsupportedOperationException("not normalized");
    }

    @Override
    public Double getDistance(T a, T b) {
        return new Double(Math.max(a.size(), b.size()));
    }
    
}
