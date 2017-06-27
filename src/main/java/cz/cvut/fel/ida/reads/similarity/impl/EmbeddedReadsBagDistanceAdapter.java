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
