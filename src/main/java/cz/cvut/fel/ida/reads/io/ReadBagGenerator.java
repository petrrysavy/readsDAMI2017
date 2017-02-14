package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.util.Random;

public class ReadBagGenerator implements ReadBagLoader {
    private final int numReads;
    private final int readLength;
    private final Random rnd;
    private final Sequence sequence;
    private final boolean cyclic;
    private final boolean shouldReverse;
    private final boolean shouldComplement;

    public ReadBagGenerator(int numReads, int readLength, Sequence sequence, boolean cyclic, boolean shouldReverse, boolean shouldComplement, Random rnd) {
        if (readLength >= sequence.length()) {
            throw new IllegalArgumentException("Too long read length");
        }
        this.numReads = numReads;
        this.readLength = readLength;
        this.sequence = sequence;
        this.cyclic = cyclic;
        this.shouldReverse = shouldReverse;
        this.shouldComplement = shouldComplement;
        this.rnd = rnd;
    }

    @Override
    public ReadsBag loadBagOfReads() {
        final ReadsBag bag = new ReadsBag(numReads, sequence.getDescription());
        for (int i = 0; i < numReads; i++) {
            // generate the read
            Sequence read;
            if (cyclic)
                read = sequence.cyclicSubsequence(rnd.nextInt(sequence.length()), readLength);
            else
                read = sequence.subSequence2(rnd.nextInt(sequence.length() - readLength), readLength);

            // reverse the read if required to do so
            if (shouldReverse && rnd.nextBoolean())
                read = read.reverse();

            if (shouldComplement && rnd.nextBoolean())
                read = read.complement();

            // and finally add the read to the bag
            bag.add(read);
        }

        return bag;
    }
}
