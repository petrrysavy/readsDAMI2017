package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author petr
 */
public class FASTQReadBagLoaderTest {

    @Test
    public void testLoadBagOfReads() {
        final FASTQReadBagLoader loader = new FASTQReadBagLoader(Paths.get("src", "test", "java", "data", "test.fq"));
        final ReadsBag bag = loader.loadBagOfReads();
        assertThat(bag.size(), is(equalTo(5)));
        assertThat(bag, is(equalTo(ReadsBag.fromString(
                "AAAAAAAAGCAGGCTAGGCTAAGCTATGATGTTCCTTAGATTAGGTGTATTAAATCCATTTTCAACTTACAATATTTTCAACTTACGATGAGTTTATCAG",
                "TCGGCTCACTGCAACCTCTGCCTCCTGGGTTCAAGTGATTCTCCTGCCTCAGCCTCCCGAATAGCTAGGACTACAAGCGCCTGCTACCACGCCCAGCTAA",
                "AGCAGTCCTGCCTCAGCCTCCGGAGTAGCTGGGACCACAGGTTCATGCCACCATGGCCAGCCAACTTTTGCATGTTTTGTAGAGATGGGGTCTCACAGTG",
                "ACTGGCGTTCACCCCTCAGACACACAGGTGGCAGCAAAGTTTTATTGTAAAATAAGAGATCGATATAAAAATGGGATATAAAAAGGGAGAAGGAGGGGAA",
                "GATTACGAGACTAATACACACTAATACTCTGAGGTGCTCAGTAAACATATTTGCATGGGGTGTGGCCACCATCTTGATTTGAATTCCCGTTGTCCCAGCC"
        ))));
    }

}
