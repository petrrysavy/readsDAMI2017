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
package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.test.RandomGenerators;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Petr Ryšavý
 */
public class SequenceTest {

    private Sequence sequence;

    @Before
    public void bootstrap() {
        this.sequence = new Sequence("ATCGCCTTAG".toCharArray(), "A test sequence");
    }

    @Test
    public void testGetSequence() {
        assertThat(new String(sequence.getSequence()), is(equalTo("ATCGCCTTAG")));
    }

    @Test
    public void testGetDescription() {
        assertThat(sequence.getDescription(), is(equalTo("A test sequence")));
    }

    @Test
    public void testLength() {
        assertThat(sequence.length(), is(equalTo(10)));
    }

    @Test
    public void testCharAt() {
        assertThat(sequence.charAt(0), is(equalTo('A')));
        assertThat(sequence.charAt(1), is(equalTo('T')));
        assertThat(sequence.charAt(2), is(equalTo('C')));
        assertThat(sequence.charAt(3), is(equalTo('G')));
        assertThat(sequence.charAt(4), is(equalTo('C')));
        assertThat(sequence.charAt(5), is(equalTo('C')));
        assertThat(sequence.charAt(6), is(equalTo('T')));
        assertThat(sequence.charAt(7), is(equalTo('T')));
        assertThat(sequence.charAt(8), is(equalTo('A')));
        assertThat(sequence.charAt(9), is(equalTo('G')));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCharAtOutOfBounds1() {
        sequence.charAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCharAtOutOfBounds2() {
        sequence.charAt(10);
    }

    @Test
    public void testSubSequence() {
        assertThat(sequence.subSequence(0, 10), is(equalTo(sequence)));
        assertThat(sequence.subSequence(0, 10), is(equalTo(Sequence.fromString("ATCGCCTTAG"))));
        assertThat(sequence.subSequence(0, 0), is(equalTo(Sequence.fromString(""))));
        assertThat(sequence.subSequence(0, 1), is(equalTo(Sequence.fromString("A"))));
        assertThat(sequence.subSequence(0, 5), is(equalTo(Sequence.fromString("ATCGC"))));
        assertThat(sequence.subSequence(0, 9), is(equalTo(Sequence.fromString("ATCGCCTTA"))));
        assertThat(sequence.subSequence(9, 10), is(equalTo(Sequence.fromString("G"))));
        assertThat(sequence.subSequence(5, 10), is(equalTo(Sequence.fromString("CTTAG"))));
        assertThat(sequence.subSequence(1, 10), is(equalTo(Sequence.fromString("TCGCCTTAG"))));
        assertThat(sequence.subSequence(2, 3), is(equalTo(Sequence.fromString("C"))));
        assertThat(sequence.subSequence(2, 7), is(equalTo(Sequence.fromString("CGCCT"))));
        assertThat(sequence.subSequence(4, 8), is(equalTo(Sequence.fromString("CCTT"))));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubSequenceOutOfBounds1() {
        sequence.subSequence(-1, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubSequenceOutOfBounds2() {
        sequence.subSequence(0, 11);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubSequenceOutOfBounds3() {
        sequence.subSequence(0, 25);
    }

    @Test
    public void testFromString() {
        assertThat(Sequence.fromString(""), is(equalTo(new Sequence(new char[]{}, null))));
        assertThat(Sequence.fromString("A"), is(equalTo(new Sequence(new char[]{'A'}, null))));
        assertThat(Sequence.fromString("AT"), is(equalTo(new Sequence(new char[]{'A',
            'T'}, null))));
        assertThat(Sequence.fromString("ATC"), is(equalTo(new Sequence(new char[]{'A',
            'T', 'C'}, null))));
        assertThat(Sequence.fromString("ATCG"), is(equalTo(new Sequence(new char[]{
            'A', 'T', 'C', 'G'}, null))));
    }

    @Test
    public void testToString() {
        assertThat(sequence.toString(), anyOf(equalTo("ATCGCCTTAG"), equalTo("A test sequence")));
    }

    @Test
    public void testReverse() {
        assertThat(sequence.reverse(), is(equalTo(Sequence.fromString("GATTCCGCTA"))));
    }

    @Test
    public void testComplement() {
        assertThat(sequence.complement(), is(equalTo(Sequence.fromString("TAGCGGAATC"))));
    }

    @Test
    public void testReverseComplement() {
        assertThat(sequence.reverseComplement(), is(equalTo(Sequence.fromString("CTAAGGCGAT"))));
    }

    @Test
    public void testRandomSequenceReverseAndComplement() {
        final Random r = new Random(25);
        for (int i = 0; i < 100; i++) {
            final Sequence s = RandomGenerators.generateRandomSequence(r.nextInt(1000), r);
            for (int j = 0; j < 25; j++) {
                switch (r.nextInt(3)) {
                    case 0:
                        assertThat(s.reverse(), is(equalTo(s.reverseCopy())));
                        break;
                    case 1:
                        assertThat(s.complement(), is(equalTo(s.complementCopy())));
                        break;
                    case 2:
                        assertThat(s.reverseComplement(), is(equalTo(s.reverseComplementCopy())));
                        break;
                }
            }
        }
    }
}
