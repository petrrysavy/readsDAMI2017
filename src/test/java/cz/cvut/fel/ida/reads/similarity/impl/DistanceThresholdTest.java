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
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;

/**
 *
 * @author Petr Ryšavý
 */
public class DistanceThresholdTest {

    private DistanceThreshold<Integer> threshold;

    @Before
    public void bootstrap() {
        threshold = new DistanceThreshold<>(new AbstractMeasure<Integer>() {
            @Override
            public boolean isZeroOneNormalized() {
                return true;
            }

            @Override
            public Double getSimilarity(Integer a, Integer b) {
                return Math.exp(-Math.abs((a - b) / 10.0));
            }
        }, 0.3, 1.0);
    }

    @Test
    public void testGetBorderGapPenaulty() {
        assertThat(threshold.getDistance(0, 0), is(closeTo(0.0, 1e-10)));
        assertThat(threshold.getDistance(0, 1), is(closeTo(0.09516258196404042684, 1e-10)));
        assertThat(threshold.getDistance(0, 2), is(closeTo(0.18126924692201814133, 1e-10)));
        assertThat(threshold.getDistance(0, 3), is(closeTo(0.25918177931828213393, 1e-10)));
        assertThat(threshold.getDistance(0, 4), is(closeTo(1.0, 1e-10)));
        assertThat(threshold.getDistance(0, 100), is(closeTo(1.0, 1e-10)));
    }

    @Test
    public void testSymmetric() {
        assertThat(threshold.isSymmetric(), is(true));
    }

    @Test
    public void testNormalizedFalse() {
        assertThat(threshold.isZeroOneNormalized(), is(true));
    }

}
