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
