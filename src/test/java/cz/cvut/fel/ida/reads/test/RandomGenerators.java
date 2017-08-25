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
package cz.cvut.fel.ida.reads.test;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.Utils;
import java.util.Random;

/**
 *
 * @author Petr Ryšavý
 */
public class RandomGenerators {

    private RandomGenerators() {
    }

    public static ReadsBag generateReadBag(int bagSize, int readLength, long seed) {
        final ReadsBag bag = new ReadsBag(bagSize);
        final Random rand = new Random(seed);
        for (int i = 0; i < bagSize; i++) {
            // generate new string
            final char[] read = new char[readLength];
            for (int j = 0; j < readLength; j++)
                read[j] = Utils.NUCLEOTIDES[rand.nextInt(Utils.NUCLEOTIDES.length)];
            bag.add(new Sequence(read, "Random read " + i));
        }
        return bag;
    }

    public static Sequence generateRandomSequence(int length, Random random) {
        final char[] read = new char[length];
        for (int j = 0; j < length; j++)
            read[j] = Utils.NUCLEOTIDES[random.nextInt(Utils.NUCLEOTIDES.length)];
        return new Sequence(read, "Random read");
    }

}
