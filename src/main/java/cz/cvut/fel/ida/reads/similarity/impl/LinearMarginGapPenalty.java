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
