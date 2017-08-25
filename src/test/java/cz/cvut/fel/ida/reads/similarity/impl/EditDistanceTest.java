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
package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Sequence;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class EditDistanceTest {
    private EditDistance ed;

    @Before
    public void bootstrap() {
        ed = new EditDistance(0, 1, 1);
    }

    @Test
    public void testDist1() {
        assertThat(3, is(equalTo(ed.getDistance(new Sequence("kitten".toCharArray(), ""), new Sequence("sitting".toCharArray(), "")))));
    }

    @Test
    public void testDist2() {
        assertThat(1, is(equalTo(ed.getDistance(new Sequence("Hello".toCharArray(), ""), new Sequence("Jello".toCharArray(), "")))));
    }

    @Test
    public void testDist3() {
        assertThat(3, is(equalTo(ed.getDistance(new Sequence("good".toCharArray(), ""), new Sequence("goodbye".toCharArray(), "")))));
    }

    @Test
    public void testDist4() {
        assertThat(0, is(equalTo(ed.getDistance(new Sequence("goodbye".toCharArray(), ""), new Sequence("goodbye".toCharArray(), "")))));
    }

    @Test
    public void testDist5() {
        assertThat(4, is(equalTo(ed.getDistance(Sequence.fromString("ATCGCTGCAA"), Sequence.fromString("CTCCTCCA")))));
    }

    @Test
    public void testDist6() {
        assertThat(1, is(equalTo(ed.getDistance(Sequence.fromString("TCG"), Sequence.fromString("TCC")))));
    }

    @Test
    public void testDist7() {
        assertThat(1, is(equalTo(ed.getDistance(Sequence.fromString("GCA"), Sequence.fromString("CCA")))));
    }

    @Test
    public void testDist8() {
        assertThat(2, is(equalTo(ed.getDistance(Sequence.fromString("TCG"), Sequence.fromString("CCA")))));
    }

    @Test
    public void testDist9() {
        assertThat(2, is(equalTo(ed.getDistance(Sequence.fromString("GCA"), Sequence.fromString("TCC")))));
    }

    @Test
    public void testSymmetric() {
        assertThat(ed.isSymmetric(), is(true));
    }

    @Test
    public void testNormalizedFalse() {
        assertThat(ed.isZeroOneNormalized(), is(false));
    }
}
