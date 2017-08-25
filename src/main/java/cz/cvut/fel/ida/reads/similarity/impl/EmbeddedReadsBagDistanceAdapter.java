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

import cz.cvut.fel.ida.reads.embedded.EmbeddedMultiset;
import cz.cvut.fel.ida.reads.embedded.EmbeddedReadsBag;
import cz.cvut.fel.ida.reads.embedded.EmbeddedSequence;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;

/**
 * This is just an adapter class that is used if we need to use a measure
 * between embedded multisets where distance of embedded reads bags would be
 * expected.
 *
 * @author Petr Ryšavý
 * @param <T> The target space of the embedding.
 */
public class EmbeddedReadsBagDistanceAdapter<T> extends AbstractMeasure<EmbeddedReadsBag<T>> {

    private final AbstractMeasure<EmbeddedMultiset<EmbeddedSequence<T>, Sequence, T>> me;

    public EmbeddedReadsBagDistanceAdapter(AbstractMeasure<EmbeddedMultiset<EmbeddedSequence<T>, Sequence, T>> me) {
        this.me = me;
    }

    @Override
    public Double getSimilarity(EmbeddedReadsBag<T> a, EmbeddedReadsBag<T> b) {
        throw new RuntimeException();
    }

    @Override
    public Double getDistance(EmbeddedReadsBag<T> a, EmbeddedReadsBag<T> b) {
        return me.getDistance(a, b);
    }
}
