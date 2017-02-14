package cz.cvut.fel.ida.reads.test;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.Utils;
import java.util.Random;

/**
 *
 * @author Petr Ryšavý
 */
public class RandomGenerators {

    private RandomGenerators() {
    }

    public static ReadsBag generateReadBag(int bagSize, int readLength, long seed) {
        final ReadsBag bag = new ReadsBag(bagSize);
        final Random rand = new Random(seed);
        for (int i = 0; i < bagSize; i++) {
            // generate new string
            final char[] read = new char[readLength];
            for (int j = 0; j < readLength; j++)
                read[j] = Utils.NUCLEOTIDES[rand.nextInt(Utils.NUCLEOTIDES.length)];
            bag.add(new Sequence(read, "Random read " + i));
        }
        return bag;
    }

    public static Sequence generateRandomSequence(int length, Random random) {
        final char[] read = new char[length];
        for (int j = 0; j < length; j++)
            read[j] = Utils.NUCLEOTIDES[random.nextInt(Utils.NUCLEOTIDES.length)];
        return new Sequence(read, "Random read");
    }

}
