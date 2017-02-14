package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.MathUtils;

/**
 *
 * @author Petr Ryšavý
 */
public class EditDistance implements DistanceCalculator<Sequence, Integer>
{
    private final int matchPremium;
    private final int mismatchPenalty;
    private final int gapPenalty;

    public EditDistance(int matchPremium, int mismatchPenalty, int gapPenalty)
    {
        this.matchPremium = matchPremium;
        this.mismatchPenalty = mismatchPenalty;
        this.gapPenalty = gapPenalty;
    }

    public EditDistance() {
        this(0, 1, 1);
    }

    @Override
    public Integer getDistance(Sequence a, Sequence b)
    {
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
        for (int i = 0; i < aSeq.length; i++)
        { // i goes over rows, i.e. the first word
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
