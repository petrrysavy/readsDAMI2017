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

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class ProductSimilarityTest {

    @Test
    public void testGetDistance() {
        assertThat(new ProductSimilarity<>(new ConstDistance(0.0), new ConstDistance(0.0)).getDistance(0, 0), is(equalTo(0.0)));
        assertThat(new ProductSimilarity<>(new ConstDistance(1.0), new ConstDistance(0.0)).getDistance(0, 0), is(equalTo(0.0)));
        assertThat(new ProductSimilarity<>(new ConstDistance(0.0), new ConstDistance(1.0)).getDistance(0, 0), is(equalTo(0.0)));
        assertThat(new ProductSimilarity<>(new ConstDistance(0.5), new ConstDistance(0.2)).getDistance(0, 0), is(closeTo(0.1, 1e-10)));
        assertThat(new ProductSimilarity<>(new ConstDistance(1.0), new ConstDistance(0.3)).getDistance(0, 0), is(closeTo(0.3, 1e-10)));
        assertThat(new ProductSimilarity<>(new ConstDistance(0.1), new ConstDistance(0.1)).getDistance(0, 0), is(closeTo(0.01, 1e-10)));
        assertThat(new ProductSimilarity<>(new ConstDistance(0.3), new ConstDistance(0.2)).getDistance(0, 0), is(closeTo(0.06, 1e-10)));
        assertThat(new ProductSimilarity<>(new ConstDistance(0.5520387506), new ConstDistance(0.7687072138)).getDistance(0, 0), is(closeTo(0.42435616988335907828, 1e-10)));
    }

    @Test
    public void testIsZeroOneNormalized() {
        assertThat(new ProductSimilarity<>(new ConstDistance(0.0), new ConstDistance(0.0)).isZeroOneNormalized(), is(false));
    }

    @Test
    public void testIsSymmetric() {
        assertThat(new ProductSimilarity<>(new ConstDistance(0.0), new ConstDistance(0.0)).isSymmetric(), is(true));
    }

    private final class ConstDistance extends AbstractMeasure<Integer> {

        private final double dist;

        ConstDistance(double dist) {
            this.dist = dist;
        }

        @Override
        public Double getDistance(Integer a, Integer b) {
            return dist;
        }
    }
}
