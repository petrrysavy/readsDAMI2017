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

import cz.cvut.fel.ida.reads.util.ArrayUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Petr Ryšavý
 */
public class ManhattanDistanceTest {

    private ManhattanDistance distance;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void bootstrap() {
        distance = new ManhattanDistance();
    }

    @Test
    public void testGetDistance() {
        assertThat(distance.getDistance(new int[]{}, new int[]{}), is(equalTo(0.0)));
        assertThat(distance.getDistance(ArrayUtils.asArray(10), ArrayUtils.asArray(10)), is(equalTo(0.0)));
        assertThat(distance.getDistance(ArrayUtils.asArray(10, 20, 30), ArrayUtils.asArray(10, 20, 30)), is(equalTo(0.0)));
        assertThat(distance.getDistance(ArrayUtils.asArray(1, 2, 3), ArrayUtils.asArray(3, 2, 1)), is(equalTo(4.0)));
        assertThat(distance.getDistance(ArrayUtils.asArray(1, 15, 7, 14, 2), ArrayUtils.asArray(2, 7, 15, 10, 1)), is(equalTo(22.0)));
        assertThat(distance.getDistance(ArrayUtils.asArray(1, 4, 5, 9, 11, 13, 14, 15, 19, 20), ArrayUtils.asArray(4, 5, 6, 10, 11, 12, 14, 17, 18, 20)), is(equalTo(10.0)));
    }

    @Test
    public void testIsSymmetric() {
        assertThat(distance.isSymmetric(), is(true));
    }

    @Test
    public void testIsNotNormalized() {
        assertThat(distance.isZeroOneNormalized(), is(false));
    }

    @Test
    public void testFailsDifferentLengths() {
        thrown.expect(AssertionError.class);
        distance.getDistance(ArrayUtils.asArray(1, 2, 3, 4), ArrayUtils.asArray(1, 2));
    }

}
