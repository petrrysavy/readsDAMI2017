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
