package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.embedded.EmbeddedMultiset;
import cz.cvut.fel.ida.reads.embedded.EmbeddedSequence;
import cz.cvut.fel.ida.reads.model.Multiset;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.impl.EmbeddedReadsBagDistanceAdapter;
import cz.cvut.fel.ida.reads.similarity.impl.MaxSizeSimilarity;
import cz.cvut.fel.ida.reads.similarity.impl.ProductSimilarity;
import cz.cvut.fel.ida.reads.similarity.impl.SequenceSimilarityAsReadBagSimilarity;

/**
 *
 * @author petr
 */
public class DistanceUtils {

    private DistanceUtils() {
    }

    public static AbstractMeasure<ReadsBag> prodWithMaxSize(AbstractMeasure<Multiset<Sequence>> innerDistance) {
        return new ProductSimilarity<>(adapt(innerDistance), new MaxSizeSimilarity<>());
    }

    public static <V> AbstractMeasure<ReadsBag> prodWithMaxSize2(AbstractMeasure<EmbeddedMultiset<EmbeddedSequence<V>, Sequence, V>> innerDistance) {
        return new ProductSimilarity<>(new EmbeddedReadsBagDistanceAdapter(innerDistance), new MaxSizeSimilarity<>());
    }

    public static AbstractMeasure<ReadsBag> adapt(AbstractMeasure<Multiset<Sequence>> innerDistance) {
        return new SequenceSimilarityAsReadBagSimilarity(innerDistance);
    }

}
