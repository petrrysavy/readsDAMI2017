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
package cz.cvut.fel.ida.reads.util;

/**
 * Utility class that is capable of calculating various statistics.
 *
 * @author Petr Ryšavý
 */
public final class StatsUtils {

    /** Do not let anybody to instantiate the class. */
    private StatsUtils() {
    }

    /**
     * Calculates the Pearson's correlation coefficient.
     * @param x One random variable.
     * @param y Second random variable.
     * @return Correlation between {@code x} and {@code y}.
     * @see https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
     */
    public static double getPearsonsCorrelationCoefficient(double[] x, double[] y) {
        if (x.length != y.length)
            throw new IllegalArgumentException("Pearson's correlation coefficient can be calculated only on random variables that are on the same domain.");

        final double xAverage = MathUtils.average(x);
        final double yAverage = MathUtils.average(y);

        double nominator = 0.0;
        double stdevX2 = 0.0;
        double stdevY2 = 0.0;

        for (int i = 0; i < x.length; i++) {
            final double xDiff = x[i] - xAverage;
            final double yDiff = y[i] - yAverage;

            nominator += xDiff * yDiff;
            stdevX2 += xDiff * xDiff;
            stdevY2 += yDiff * yDiff;
        }

        return nominator / Math.sqrt(stdevX2 * stdevY2);
    }
}
