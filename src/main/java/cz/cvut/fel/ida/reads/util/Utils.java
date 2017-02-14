package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.io.ReadBagGenerator;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Ryšavý
 */
public final class Utils {

    public static final char[] NUCLEOTIDES = new char[]{'A', 'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_A = new char[]{'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_T = new char[]{'A', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_C = new char[]{'A', 'T', 'G'};
    public static final char[] NUCLEOTIDES_NOT_G = new char[]{'A', 'T', 'C'};
    public static final char[] NUCLEOTIDES_A_FIRST = new char[] {'A', 'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_T_FIRST = new char[] {'T', 'A', 'C', 'G'};
    public static final char[] NUCLEOTIDES_C_FIRST = new char[] {'C', 'A', 'T', 'G'};
    public static final char[] NUCLEOTIDES_G_FIRST = new char[] {'G', 'C', 'A', 'T'};
    public static final int NUCLEOTIDES_COUNT = 4;
    public static final int NUCLEOTIDES_COUNT_SQ = 16;

    private Utils() {

    }

    public static ReadsBag[] generateReadBags(Sequence[] sequences, double coverage, int readLength, boolean cyclic) {
        return generateReadBags(sequences, coverage, readLength, cyclic, false, false, new Random());
    }

    public static ReadsBag[] generateReadBags(Sequence[] sequences, double coverage, int readLength, boolean cyclic, boolean shouldReverse, boolean shouldComplement, Random rnd) {
        ReadsBag[] bags = new ReadsBag[sequences.length];
        for (int i = 0; i < sequences.length; i++)
            bags[i] = new ReadBagGenerator((int) (sequences[i].length() * coverage / readLength), readLength, sequences[i], cyclic, shouldReverse, shouldComplement, rnd).loadBagOfReads();
        return bags;
    }

    public static char[] complementaryCopy(char[] sequence) {
        final char[] copy = Arrays.copyOf(sequence, sequence.length);
        makeComplementary(copy);
        return copy;
    }

    public static void makeComplementary(char[] sequence) {
        for (int i = 0; i < sequence.length; i++)
            sequence[i] = complement(sequence[i]);
    }

    public static char complement(char ch) {
        switch (ch) {
            case 'A':
                return 'T';
            case 'T':
                return 'A';
            case 'C':
                return 'G';
            case 'G':
                return 'C';
            default:
                throw new IllegalArgumentException("Unknown nucleotide : " + ch);
        }
    }

    public static int toInteger(char ch) {
        switch (ch) {
            case 'A':
                return 0;
            case 'T':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            default:
                throw new IllegalArgumentException("Unknown nucleotide : " + ch);
        }
    }

    public static char[] checkFASTASequence(char[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            switch (sequence[i]) {
                // do nothing on nucleotides
                case 'A':
                case 'T':
                case 'C':
                case 'G':
                    continue;
                default:
                    Logger.getLogger(Utils.class.getName()).log(Level.INFO, "found unknown nucleotide {0}", sequence[i]);
                    sequence[i] = derandomizeNucleotide(sequence[i], Settings.RANDOM);
                    Logger.getLogger(Utils.class.getName()).log(Level.INFO, "... replaced with {0}", sequence[i]);
            }
        }
        return sequence;
    }

    public static char derandomizeNucleotide(char ch, Random r) {
        switch (ch) {
            case 'U':
                return 'T';
            case 'R':
                return r.nextBoolean() ? 'A' : 'G';
            case 'Y':
                return r.nextBoolean() ? 'C' : 'T';
            case 'K':
                return r.nextBoolean() ? 'G' : 'T';
            case 'M':
                return r.nextBoolean() ? 'A' : 'C';
            case 'S':
                return r.nextBoolean() ? 'G' : 'C';
            case 'W':
                return r.nextBoolean() ? 'A' : 'T';
            case 'B':
                return NUCLEOTIDES_NOT_A[r.nextInt(NUCLEOTIDES_NOT_A.length)];
            case 'D':
                return NUCLEOTIDES_NOT_C[r.nextInt(NUCLEOTIDES_NOT_C.length)];
            case 'H':
                return NUCLEOTIDES_NOT_G[r.nextInt(NUCLEOTIDES_NOT_G.length)];
            case 'V':
                return NUCLEOTIDES_NOT_T[r.nextInt(NUCLEOTIDES_NOT_T.length)];
            case 'N':
                return NUCLEOTIDES[r.nextInt(NUCLEOTIDES.length)];
            default:
                throw new IllegalArgumentException("Unknown nucleotide : " + ch);
        }
    }

    public static void dieWithMessage(String message) {
        System.err.println(message);
        System.exit(0);
    }
    
    public static void joinAll(Thread[] threads) throws InterruptedException {
        for(Thread t : threads)
            t.join();
    }
    
    public static char[] listNucleotidesPreference(char ch) {
        switch (ch) {
            case 'A':
                return NUCLEOTIDES_A_FIRST;
            case 'T':
                return NUCLEOTIDES_T_FIRST;
            case 'C':
                return NUCLEOTIDES_C_FIRST;
            case 'G':
                return NUCLEOTIDES_G_FIRST;
            default:
                throw new IllegalArgumentException("Unknown nucleotide : " + ch);
        }

    }

}
