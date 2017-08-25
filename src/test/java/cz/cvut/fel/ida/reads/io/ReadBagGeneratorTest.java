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
