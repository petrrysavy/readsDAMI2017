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
package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.MathUtils;
import cz.cvut.fel.ida.reads.util.Utils;

/**
 * Embedding of sequences to metric space R**64. For each k-mer of length 3 in
 * the sequence we calculate number of its occurences and then we store those
 * numbers in a vector.
 *
 * @author Petr Ryšavý
 */
public class TripletsEmbedding implements EmbeddingFunction<Sequence, int[]> {

    /** Length of the k-mers. By default 3. */
    private static final int WORD_LEN = 3;
    /** Dimension of the target space. By default 64. */
    private static final int EMBEDDING_DIM = MathUtils.pow(Utils.NUCLEOTIDES_COUNT, WORD_LEN);

    /**
     * Calculates occurences of all 3-mers in the sequence. {@inheritDoc }
     */
    @Override
    public int[] project(Sequence key) {
        final int[] embedding = new int[EMBEDDING_DIM];
        final char[] seq = key.getSequence();
        for (int i = 0; i <= seq.length - WORD_LEN; i++)
            ++embedding[calculateIndex(seq, i)];
        return embedding;
    }

    /** Gives index to a coordinate in embedded space. Each k-mer is given its
     * own coordinate.
     * @param arr The sequence.
     * @param offset Location of the first nucleotide of the k-mer.
     * @return first nucleotide * 16 + second * 4 + third.
     */
    private int calculateIndex(char[] arr, int offset) {
        return Utils.toInteger(arr[offset]) * Utils.NUCLEOTIDES_COUNT_SQ
                + Utils.toInteger(arr[offset + 1]) * Utils.NUCLEOTIDES_COUNT
                + Utils.toInteger(arr[offset + 2]);
    }
}

