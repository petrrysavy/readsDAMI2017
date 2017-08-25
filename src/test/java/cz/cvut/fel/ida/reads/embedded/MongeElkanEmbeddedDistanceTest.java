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
package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.impl.EditDistance;
import cz.cvut.fel.ida.reads.similarity.impl.ManhattanDistance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Petr Ryšavý
 */
public class MongeElkanEmbeddedDistanceTest {

    private MongeElkanEmbeddedDistance<EmbeddedSequence<int[]>, Sequence, int[]> distance;
    private EmbeddingFunction<Sequence, int[]> embedding;

    @Before
    public void bootstrap() {
        distance = new MongeElkanEmbeddedDistance<>(new ManhattanDistance(), new EditDistance(), 4);
        embedding = new TripletsEmbedding();
    }

    @Test
    public void testGetDistance() {
        final ReadsBag bag1 = ReadsBag.fromString("ATCA", "AAAA", "CATA");
        final ReadsBag bag2 = ReadsBag.fromString("TTTT", "ATAT");
        final EmbeddedReadsBag<int[]> bag1emb = new EmbeddedReadsBag<>(bag1, embedding);
        final EmbeddedReadsBag<int[]> bag2emb = new EmbeddedReadsBag<>(bag2, embedding);
        assertThat(distance.getDistance(bag1emb, bag2emb), is(closeTo(1.0, 1e-10)));
    }

    @Test
    public void testGetDistance2() {
        final ReadsBag bag1 = ReadsBag.fromString("ATAT");
        final ReadsBag bag2 = ReadsBag.fromString("TATA", "ATCT");
        final EmbeddedReadsBag<int[]> bag1emb = new EmbeddedReadsBag<>(bag1, embedding);
        final EmbeddedReadsBag<int[]> bag2emb = new EmbeddedReadsBag<>(bag2, embedding);
        assertThat(distance.getDistance(bag1emb, bag2emb), is(closeTo(0.0, 1e-10)));
    }

    @Test
    public void testGetDistance3() {
        final ReadsBag bag1 = ReadsBag.fromString("ACAC");
        final ReadsBag bag2 = ReadsBag.fromString("CACA", "ACGC");
        final EmbeddedReadsBag<int[]> bag1emb = new EmbeddedReadsBag<>(bag1, embedding);
        final EmbeddedReadsBag<int[]> bag2emb = new EmbeddedReadsBag<>(bag2, embedding);
        assertThat(distance.getDistance(bag1emb, bag2emb), is(closeTo(0.0, 1e-10)));
    }

}
