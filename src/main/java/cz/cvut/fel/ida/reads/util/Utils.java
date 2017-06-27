package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.io.ReadBagGenerator;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic utility class.
 *
 * @author Petr Ryšavý
 */
public final class Utils {

    /** Those symbols represent nucleotides. */
    public static final char[] NUCLEOTIDES = new char[]{'A', 'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_A = new char[]{'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_T = new char[]{'A', 'C', 'G'};
    public static final char[] NUCLEOTIDES_NOT_C = new char[]{'A', 'T', 'G'};
    public static final char[] NUCLEOTIDES_NOT_G = new char[]{'A', 'T', 'C'};
    public static final char[] NUCLEOTIDES_A_FIRST = new char[]{'A', 'T', 'C', 'G'};
    public static final char[] NUCLEOTIDES_T_FIRST = new char[]{'T', 'A', 'C', 'G'};
    public static final char[] NUCLEOTIDES_C_FIRST = new char[]{'C', 'A', 'T', 'G'};
    public static final char[] NUCLEOTIDES_G_FIRST = new char[]{'G', 'C', 'A', 'T'};
    /** Count of the nucleotides. */
    public static final int NUCLEOTIDES_COUNT = 4;
    /** Square of the nucleotide count. */
    public static final int NUCLEOTIDES_COUNT_SQ = 16;

    /** Do not let anybody to instantiate the class. */
    private Utils() {
    }

    /**
     * Generates a set of reads for each sequence based on i.i.d. assumption and
     * uniform distribution assumption.
     * @param sequences List of sequences to be sampled.
     * @param coverage Target coverage.
     * @param readLength Target read length.
     * @param cyclic Should we assume cyclic DNA molecule.
     * @return A list of read bags sampled from the sequences.
     */
    public static ReadsBag[] generateReadBags(Sequence[] sequences, double coverage, int readLength, boolean cyclic) {
        return generateReadBags(sequences, coverage, readLength, cyclic, false, false, new Random());
    }

    /**
     * Generates a set of reads for each sequence based on i.i.d. assumption and
     * uniform distribution assumption.
     * @param sequences List of sequences to be sampled.
     * @param coverage Target coverage.
     * @param readLength Target read length.
     * @param cyclic Should we assume cyclic DNA molecule.
     * @param shouldReverse Do we know direction?
     * @param shouldComplement Do we know whether the read is complement or not?
     * @param rnd Pseudorandom number generator.
     * @return A list of read bags sampled from the sequences.
     */
    public static ReadsBag[] generateReadBags(Sequence[] sequences, double coverage, int readLength, boolean cyclic, boolean shouldReverse, boolean shouldComplement, Random rnd) {
        ReadsBag[] bags = new ReadsBag[sequences.length];
        for (int i = 0; i < sequences.length; i++)
            bags[i] = new ReadBagGenerator((int) (sequences[i].length() * coverage / readLength), readLength, sequences[i], cyclic, shouldReverse, shouldComplement, rnd).loadBagOfReads();
        return bags;
    }

    /**
     * For a DNA sequence returns its complement.
     * @param sequence The sequence, for example {@code ATCCG}.
     * @return The complement, for example {@code TAGGC}.
     */
    public static char[] complementaryCopy(char[] sequence) {
        final char[] copy = Arrays.copyOf(sequence, sequence.length);
        makeComplementary(copy);
        return copy;
    }

    /**
     * Changes a DNA sequence into its complement.
     * @param sequence The sequence, for example.
     */
    public static void makeComplementary(char[] sequence) {
        for (int i = 0; i < sequence.length; i++)
            sequence[i] = complement(sequence[i]);
    }

    /**
     * Returns a complement of the nucleotide. The pairs are A/T and C/G.
     * @param ch A nucleotide.
     * @return Its complementary nucleotide.
     */
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

    /**
     * This method returns an integer index for a nucleotide.
     * @param ch The nucleotide.
     * @return Unique integer characterizing the nucleotide.
     */
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

    /**
     * Checks that the sequence is in FASTA format and makes sure that only
     * A/T/C/G are present. If not, the unknown value is randomly generated.
     * @param sequence The original sequence.
     * @return The same sequence with only A/T/C/G and for example N replaced
     * with a random nucleotide.
     */
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

    /**
     * For an unknown symbol generates a nucleotide based on random decision.
     * @param ch Char to replace with a nucleotide.
     * @param r The random number generator.
     * @return One of A/T/C/G.
     */
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

    /**
     * Prints a message and exists.
     * @param message The message to show.
     */
    public static void dieWithMessage(String message) {
        System.err.println(message);
        System.exit(0);
    }

    /**
     * Joins for all threads.
     * @param threads Thread to make sure that all of them have ended.
     * @throws InterruptedException
     */
    public static void joinAll(Thread[] threads) throws InterruptedException {
        for (Thread t : threads)
            t.join();
    }

    /**
     * Gets list of nucleotides.
     * @param ch The nucleotide that should be first in the list.
     * @return List of all nucleotides with {@code ch} first.
     */
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
