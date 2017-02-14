package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 *
 * @author Petr Ryšavý
 */
public class FASTAReadBagLoaderTest {

    @Test
    public void testLoadBagOfReads() {
        Path p = Paths.get("src", "test", "java", "data", "AF389119");
        FASTAReadBagLoader loader = new FASTAReadBagLoader(p);
        ReadsBag bag = loader.loadBagOfReads();
        Assert.assertThat(bag.size(), is(equalTo(155)));
        Assert.assertThat(bag, Matchers.hasItem(new Sequence("ATCACTCACTGAGTGACATCAAAATCATGG".toCharArray(), ">ENA|AF389119|AF389119.1 Influenza A virus (A/Puerto Rico/8/34/Mount Sinai(H1N1)) segment 5, complete sequence. [cyclic subsequence 19 of length 30]")));
        Assert.assertThat(bag, not(Matchers.hasItem(new Sequence("ATCACTCACTGAGTGACATCAAAATCATGA".toCharArray(), ">ENA|AF389119|AF389119.1 Influenza A virus (A/Puerto Rico/8/34/Mount Sinai(H1N1)) segment 5, complete sequence. [cyclic subsequence 19 of length 30]"))));
    }

}
