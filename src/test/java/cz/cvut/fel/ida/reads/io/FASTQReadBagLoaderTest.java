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
