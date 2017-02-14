package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.MathUtils;
import cz.cvut.fel.ida.reads.util.Utils;

/**
 *
 * @author Petr Ryšavý
 */
public class TripletsEmbedding implements EmbeddingFunction<Sequence, int[]> {

    private static final int WORD_LEN = 3;
    private static final int EMBEDDING_DIM = MathUtils.pow(Utils.NUCLEOTIDES_COUNT, WORD_LEN);

    @Override
    public int[] project(Sequence key) {
        final int[] embedding = new int[EMBEDDING_DIM];
        final char[] seq = key.getSequence();
        for (int i = 0; i <= seq.length - WORD_LEN; i++)
            ++embedding[calculateIndex(seq, i)];
        return embedding;
    }

    private int calculateIndex(char[] arr, int offset) {
        return Utils.toInteger(arr[offset]) * Utils.NUCLEOTIDES_COUNT_SQ
                + Utils.toInteger(arr[offset + 1]) * Utils.NUCLEOTIDES_COUNT
                + Utils.toInteger(arr[offset + 2]);
    }
}
