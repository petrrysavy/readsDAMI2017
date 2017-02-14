package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.Sequence;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.junit.Test;

/**
 *
 * @author Petr Ryšavý
 */
public class ReadBagGeneratorTest {

    @Test
    public void testLoadZika() {
        Path p = Paths.get("src", "test", "java", "data", "LC002520");
        FASTASequenceLoader loader = new FASTASequenceLoader(p);
        Sequence s = loader.loadSequence();
        ReadBagGenerator rbg = new ReadBagGenerator(150, 30, s, true, false, false, new Random(42));
        System.err.println(rbg.loadBagOfReads());
    }
}
