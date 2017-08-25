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

import cz.cvut.fel.ida.reads.model.HashMultiset;
import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

/**
 *
 * @author Petr Ryšavý
 */
public class SymmetricMongeElkanDistanceTest {

    /**
     * Test of getSimilarity method, of class SymmetricMongeElkanSimple.
     */
    @Test
    public void testGetSimilarity() {
        SymmetricMongeElkanDistance<Double> instance = new SymmetricMongeElkanDistance<>(new DistanceCalculator<Double, Double>() {
            @Override
            public boolean isZeroOneNormalized() {
                return true;
            }

            @Override
            public Double getDistance(Double a, Double b) {
                return 1.0 - Math.exp(-Math.abs(a - b));
            }
        });
        Multiset<Double> a = new HashMultiset<>(1.0, 2.0, 3.0, 2.0, 5.0);
        Multiset<Double> b = new HashMultiset<>(1.0, 1.0, 2.5);
        assertThat(instance.getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(0.72458947439495214386, 1e-11)));
    }

    @Test
    public void testSimilarity4() {
        // TRIANGLE INEQUALITY IS BROKEN
        SymmetricMongeElkanDistance<Sequence> similarity = new SymmetricMongeElkanDistance<>(new EditDistance(0, 1, 1));
        final ReadsBag a = ReadsBag.fromString("ATC", "ATC", "GGG");
        final ReadsBag b = ReadsBag.fromString("ATA", "GGG");
        final ReadsBag c = ReadsBag.fromString("CTA", "GGG");
        assertThat(similarity.getDistance(a, b), is(closeTo(7.0 / 12.0, 1e-10)));
        assertThat(similarity.getDistance(b, c), is(closeTo(0.5, 1e-10)));
        assertThat(similarity.getDistance(a, c), is(closeTo(14.0 / 12.0, 1e-10)));
        assertThat(similarity.getDistance(a, b) + similarity.getDistance(b, c), is(lessThan(similarity.getDistance(a, c))));
    }

    @Test
    public void testIsSymmetric() {
        SymmetricMongeElkanDistance<Sequence> similarity = new SymmetricMongeElkanDistance<>(new EditDistance(0, 1, 1));
        assertThat(similarity.isSymmetric(), is(true));
    }
}
