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
package cz.cvut.fel.ida.reads.util;

import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author petr
 */
@RunWith(Parameterized.class)
public class IOUtilsTest_stripKnownExtension {

    private final String filename;
    private final String expected;

    public IOUtilsTest_stripKnownExtension(String filename, String expected) {
        this.filename = filename;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"abcd.fa", "abcd"},
            {"abcd.fasta", "abcd"},
            {"abcd.fq", "abcd"},
            {"abcd.fastq", "abcd"},
            {"abcd.abc", "abcd.abc"},});
    }

    @Test
    public void testStripKnownExtension() {
        assertThat(IOUtils.stripKnownExtension(filename), is(equalTo(expected)));
    }

}
