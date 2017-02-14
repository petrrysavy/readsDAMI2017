package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;

/**
 *
 * @author Petr Ryšavý
 */
public class SequenceSimilarityAsReadBagSimilarity extends AbstractMeasure<ReadsBag> {

    private final AbstractMeasure<Multiset<Sequence>> me;

    public SequenceSimilarityAsReadBagSimilarity(AbstractMeasure<Multiset<Sequence>> me) {
        this.me = me;
    }

    @Override
    public Double getSimilarity(ReadsBag a, ReadsBag b) {
        throw new RuntimeException();
    }

    @Override
    public Double getDistance(ReadsBag a, ReadsBag b) {
        return me.getDistance(a, b);
    }
}
