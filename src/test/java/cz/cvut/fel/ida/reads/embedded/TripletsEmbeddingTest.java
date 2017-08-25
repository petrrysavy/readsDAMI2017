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
