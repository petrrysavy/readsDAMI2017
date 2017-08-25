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

import cz.cvut.fel.ida.reads.similarity.MarginGapPenaulty;

/**
 * This method implements linear border gap penaulty. The gap penaulty is zero
 * for any x from interval [0, a], then linearly increases on interval [a, b]
 * and is maximal on interval [b, 1]. The x value represents distance of x from
 * the beginning of the word. In this case a = 1.0 - b.
 *
 * @author Petr Ryšavý
 */
public class LinearMarginGapPenalty implements MarginGapPenaulty {

    private final double oneOver2coverage;
    private final double maximalGapPenalty;
    private final double[][] penalties;

    public LinearMarginGapPenalty(double coverage, int maximalReadLength, double maximalGapPenalty) {
        this.oneOver2coverage = 0.5 / coverage;
        this.maximalGapPenalty = maximalGapPenalty;
        this.penalties = new double[maximalReadLength + 1][];
    }

    @Override
    public double[] build(int readLength) {
        if (penalties[readLength] == null) {
            final double[] penalty = new double[readLength + 1];
            final double oneOverReadLength = 1.0 / readLength;
            final double a = oneOver2coverage - oneOverReadLength;
            final double b = 1.0 - a;
            final double mgpOverbMinusa = maximalGapPenalty / (b - a);

            double x = 0.5 * oneOverReadLength;
            int gapIndex = 0;
            for (; x <= a; gapIndex++, x += oneOverReadLength)
                penalty[gapIndex] = 0.0;
            for (; x < b; gapIndex++, x += oneOverReadLength)
                penalty[gapIndex] = mgpOverbMinusa * (x - a);
            for (; gapIndex < penalty.length; gapIndex++)
                penalty[gapIndex] = maximalGapPenalty;
            penalties[readLength] = penalty;
        }
        return penalties[readLength];
    }
}
