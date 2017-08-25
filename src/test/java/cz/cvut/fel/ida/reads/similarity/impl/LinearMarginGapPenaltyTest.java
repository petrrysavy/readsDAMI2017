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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class LinearMarginGapPenaltyTest {

    private LinearMarginGapPenalty penaulty;

    @Test
    public void testGetMarginGapPenaulty() {
        penaulty = new LinearMarginGapPenalty(7.0 / 5.0, 7, 4);
        final double[] pen = penaulty.build(7);
        assertThat(pen[0], is(closeTo(0.0, 1e-10)));
        assertThat(pen[1], is(closeTo(0.0, 1e-10)));
        assertThat(pen[2], is(closeTo(1.0, 1e-10)));
        assertThat(pen[3], is(closeTo(2.0, 1e-10)));
        assertThat(pen[4], is(closeTo(3.0, 1e-10)));
        assertThat(pen[5], is(closeTo(4.0, 1e-10)));
        assertThat(pen[6], is(closeTo(4.0, 1e-10)));
    }

    @Test
    public void testGetMarginGapPenaulty2() {
        penaulty = new LinearMarginGapPenalty(1.0, 3, 2);
        final double[] pen = penaulty.build(3);
        assertThat(pen[0], is(closeTo(0.0, 1e-10)));
        assertThat(pen[1], is(closeTo(1.0, 1e-10)));
        assertThat(pen[2], is(closeTo(2.0, 1e-10)));
    }

    @Test
    public void testGetMarginGapPenalty2() {
        penaulty = new LinearMarginGapPenalty(3.0, 30, 2);
        final double[] pen = penaulty.build(30);
        for (int g = 0; g < 4; g++)
            assertThat(pen[g], is(equalTo(0.0)));
        assertThat(pen[4], is(closeTo(1.0 / 22.0, 1e-10)));
        assertThat(pen[13], is(closeTo(19.0 / 22.0, 1e-10)));
        for (int g = 26; g < 30; g++)
            assertThat(pen[g], is(equalTo(2.0)));
    }

}
