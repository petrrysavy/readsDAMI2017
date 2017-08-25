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
import cz.cvut.fel.ida.reads.util.MathUtils;

/**
 * Standard edit distance calculation.
 *
 * @author Petr Ryšavý
 */
public class EditDistance implements DistanceCalculator<Sequence, Integer> {

    /** This is how much we get if we align two same symbols. */
    private final int matchPremium;
    /** This is how much we pay for aligning different symbols. */
    private final int mismatchPenalty;
    /** This is what we pay for a gap. */
    private final int gapPenalty;

    /**
     * Creates new edit distance.
     * @param matchPremium This is how much we get if we align two same symbols.
     * @param mismatchPenalty This is how much we pay for aligning different
     * symbols.
     * @param gapPenalty This is what we pay for a gap.
     */
    public EditDistance(int matchPremium, int mismatchPenalty, int gapPenalty) {
        this.matchPremium = matchPremium;
        this.mismatchPenalty = mismatchPenalty;
        this.gapPenalty = gapPenalty;
    }

    /** Creates new Levenshtein distance. */
    public EditDistance() {
        this(0, 1, 1);
    }

    @Override
    public Integer getDistance(Sequence a, Sequence b) {
        final char[] aSeq = a.getSequence();
        final char[] bSeq = b.getSequence();
        // create emty table, first string in rows, second to the columns
        int[] scoreMatrixCurrent = new int[bSeq.length + 1];
        int[] scoreMatrixLast = new int[bSeq.length + 1];
        int[] swap;

        // initialize first row
        for (int j = 0; j < bSeq.length; j++)
            scoreMatrixLast[j + 1] = scoreMatrixLast[j] + gapPenalty;
        // fill the rest of the table
        for (int i = 0; i < aSeq.length; i++) { // i goes over rows, i.e. the first word
            scoreMatrixCurrent[0] = scoreMatrixLast[0] + gapPenalty;
            for (int j = 0; j < bSeq.length; j++)
                scoreMatrixCurrent[j + 1] = MathUtils.min(
                        // look to the left
                        scoreMatrixCurrent[j] + gapPenalty,
                        // look to the top
                        scoreMatrixLast[j + 1] + gapPenalty,
                        // try the diagonal
                        scoreMatrixLast[j] + (aSeq[i] == bSeq[j] ? -matchPremium : mismatchPenalty));

            // swap the score matrix line
            swap = scoreMatrixCurrent;
            scoreMatrixCurrent = scoreMatrixLast;
            scoreMatrixLast = swap;
        }

        return scoreMatrixLast[bSeq.length];
    }
}
