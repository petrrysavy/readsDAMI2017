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

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.similarity.MarginGapPenaulty;
import cz.cvut.fel.ida.reads.util.MathUtils;

/**
 * Edit distance calculation with different dealing with margin gaps.
 *
 * @author Petr Ryšavý
 */
public class MarginGapEditDistance implements DistanceCalculator<Sequence, Double> {

    /** This is how much we get if we align two same symbols. */
    private final double matchPremium;
    /** This is how much we pay for aligning different symbols. */
    private final double mismatchPenalty;
    /** This is what we pay for a gap. */
    private final double gapPenalty;
    /** This tells us how much we pay for gaps at margins. */
    private final MarginGapPenaulty marginGapPenalty;

    /**
     * Creates new edit distance.
     * @param matchPremium This is how much we get if we align two same symbols.
     * @param mismatchPenalty This is how much we pay for aligning different
     * symbols.
     * @param gapPenalty This is what we pay for a gap.
     * @param marginGapPenalty This tells us how much we pay for gaps at
     * margins.
     */
    public MarginGapEditDistance(double matchPremium, double mismatchPenalty, double gapPenalty, MarginGapPenaulty marginGapPenalty) {
        this.matchPremium = matchPremium;
        this.mismatchPenalty = mismatchPenalty;
        this.gapPenalty = gapPenalty;
        this.marginGapPenalty = marginGapPenalty;
    }

    @Override
    public Double getDistance(Sequence a, Sequence b) {
        // a goes over rows of the matrix, b over columns
        final char[] aSeq = a.getSequence();
        final char[] bSeq = b.getSequence();
        // create emty table, first string in rows, second to the columns
        double[] scoreMatrixCurrent = new double[bSeq.length + 1];
        double[] scoreMatrixLast = new double[bSeq.length + 1];
        double[] swap;
        // and get the penalty scores for the read lengths
        final double[] aMarginPenalty = marginGapPenalty.build(aSeq.length);
        final double[] bMarginPenalty = marginGapPenalty.build(bSeq.length);

        // initialize first row
        for (int j = 0; j < bSeq.length; j++)
            scoreMatrixLast[j + 1] = scoreMatrixLast[j] + bMarginPenalty[j];
        // fill the rest of the table
        for (int i = 0; i < aSeq.length - 1; i++) { // i goes over rows, i.e. the first word
            scoreMatrixCurrent[0] = scoreMatrixLast[0] + aMarginPenalty[i];
            for (int j = 0; j < bSeq.length - 1; j++)
                scoreMatrixCurrent[j + 1] = MathUtils.min(
                        // look to the left
                        scoreMatrixCurrent[j] + gapPenalty,
                        // look to the top
                        scoreMatrixLast[j + 1] + gapPenalty,
                        // try the diagonal
                        scoreMatrixLast[j] + (aSeq[i] == bSeq[j] ? -matchPremium : mismatchPenalty));
            scoreMatrixCurrent[bSeq.length] = MathUtils.min(
                    scoreMatrixCurrent[bSeq.length - 1] + gapPenalty,
                    scoreMatrixLast[bSeq.length] + aMarginPenalty[aSeq.length - i - 1],
                    scoreMatrixLast[bSeq.length - 1] + (aSeq[i] == bSeq[bSeq.length - 1] ? -matchPremium : mismatchPenalty));

            // swap the score matrix line
            swap = scoreMatrixCurrent;
            scoreMatrixCurrent = scoreMatrixLast;
            scoreMatrixLast = swap;
        }

        scoreMatrixCurrent[0] = scoreMatrixLast[0] + aMarginPenalty[aSeq.length - 1];
        // and the last row
        for (int j = 0; j < bSeq.length - 1; j++)
            scoreMatrixCurrent[j + 1] = MathUtils.min(
                    // look to the left
                    scoreMatrixCurrent[j] + bMarginPenalty[bSeq.length - j - 1],
                    // look to the top
                    scoreMatrixLast[j + 1] + gapPenalty,
                    // try the diagonal
                    scoreMatrixLast[j] + (aSeq[aSeq.length - 1] == bSeq[j] ? -matchPremium : mismatchPenalty));
        scoreMatrixCurrent[bSeq.length] = MathUtils.min(
                scoreMatrixCurrent[bSeq.length - 1] + bMarginPenalty[0],
                scoreMatrixLast[bSeq.length] + aMarginPenalty[0],
                scoreMatrixLast[bSeq.length - 1] + (aSeq[aSeq.length - 1] == bSeq[bSeq.length - 1] ? -matchPremium : mismatchPenalty));

        return scoreMatrixCurrent[bSeq.length];
    }
}
