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
 * Manhattan distance on vectors of natural numbers.
 *
 * @author Petr Ryšavý
 */
public class ManhattanDistance extends AbstractMeasure<int[]> {

    @Override
    public Double getDistance(int[] a, int[] b) {
        assert (a.length == b.length);

        int distance = 0;
        for (int i = 0; i < a.length; i++)
            distance += Math.abs(a[i] - b[i]);
        return new Double(distance);
    }
}
