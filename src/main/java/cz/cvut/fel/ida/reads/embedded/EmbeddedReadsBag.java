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
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A reads bag which is embedded in some other space. Reads are represented as
 * embedded objects instead of strings.
 *
 * @author Petr Ryšavý
 * @param <T> The target space of the embedding.
 */
public class EmbeddedReadsBag<T> extends AbstractCollection<Sequence> implements EmbeddedMultiset<EmbeddedSequence<T>, Sequence, T> {

    /** The original reads bag. */
    private final ReadsBag bag;
    /** List of embedded objects. */
    private final List<EmbeddedSequence<T>> list;

    /**
     * Creates new embedded readsbag based on function that calculates the
     * embedding.
     * @param bag Bag of sequences.
     * @param function The function that calculatees embedding.
     */
    public EmbeddedReadsBag(ReadsBag bag, EmbeddingFunction<Sequence, T> function) {
        this.bag = bag;
        // build the unmodifiable view of the reads bag that contains the embedding
        List<EmbeddedSequence<T>> tmp = new ArrayList<>(bag.uniqueSize());
        for (Sequence s : bag.toSet())
            tmp.add(new EmbeddedSequence<>(s, function));
        list = Collections.unmodifiableList(tmp);
    }

    @Override
    public Iterator<Sequence> iterator() {
        return bag.iterator();
    }

    @Override
    public Iterator<EmbeddedSequence<T>> embeddedIterator() {
        return list.iterator();
    }

    @Override
    public int size() {
        return bag.size();
    }

    @Override
    public int count(Object o) {
        return bag.count(o);
    }

    /**
     * Gets the original reads bag.
     * @return The original reads bag.
     */
    public ReadsBag getBag() {
        return bag;
    }

}
