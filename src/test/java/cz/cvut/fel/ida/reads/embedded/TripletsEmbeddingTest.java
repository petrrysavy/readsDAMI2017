package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.ArrayUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Petr Ryšavý
 */
public class TripletsEmbeddingTest {

    @Test
    public void testProject() {
        TripletsEmbedding function = new TripletsEmbedding();
        int[] expected = new int[64];//instance.project(key);
        for (int i : ArrayUtils.asArray(4, 16, 6, 24, 34, 10, 41, 39, 29, 53, 20, 18))
            expected[i] = 1;
        for (int i : ArrayUtils.asArray(1, 25, 38))
            expected[i] = 2;
        assertThat(function.project(Sequence.fromString("AATAATCACCTCTCTGTTAC")), is(equalTo(expected)));
    }

}
