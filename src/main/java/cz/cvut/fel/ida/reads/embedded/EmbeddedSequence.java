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

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.model.UnorientedObject;

/**
 * Embedding of a sequence object.
 *
 * @author Petr Ryšavý
 * @param <T> The target embedding space.
 */
public class EmbeddedSequence<T> extends EmbeddedObject<Sequence, T> implements UnorientedObject<EmbeddedSequence<T>> {

    /** Reversed sequence. */
    private EmbeddedSequence<T> reverse;
    /** Complementary sequence. */
    private EmbeddedSequence<T> complement;

    /**
     * Creates new embedded sequence object.
     * @param s The original sequence.
     * @param projection It's projection.
     */
    private EmbeddedSequence(Sequence s, T projection) {
        super(s, projection);
    }

    /**
     * Creates new embedded sequence. Automatically calculates reverse and
     * complementary sequences.
     * @param s The original sequence.
     * @param function The function that defines the embedding.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public EmbeddedSequence(Sequence s, EmbeddingFunction<Sequence, T> function) {
        this(s, function.project(s));
        reverse = new EmbeddedSequence<>(s.reverse(), function.project(s.reverse()));
        complement = new EmbeddedSequence<>(s.complement(), function.project(s.complement()));
        EmbeddedSequence<T> reverseComplement = new EmbeddedSequence<>(s.complementCopy(), function.project(s.complementCopy()));

        // set the links properly - this is already set
        this.reverse.reverse = this;
        this.reverse.complement = reverseComplement;
        this.complement.reverse = reverseComplement;
        this.complement.complement = this;
        reverseComplement.reverse = this.complement;
        reverseComplement.complement = this.reverse;
    }

    @Override
    public EmbeddedSequence<T> reverse() {
        return reverse;
    }

    @Override
    public EmbeddedSequence<T> complement() {
        return complement;
    }
}
