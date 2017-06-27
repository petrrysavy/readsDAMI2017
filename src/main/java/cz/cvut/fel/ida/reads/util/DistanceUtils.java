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
 * Utility class that allows manipulation with distance functions.
 *
 * @author Petr Ryšavý
 */
public class DistanceUtils {

    /** Do not let anybody to instantiate the class. */
    private DistanceUtils() {
    }

    /**
     * Returns another measure that is just product of the argument and a
     * distance that returns maximum of the sizes of multisets.
     * @param innerDistance Distance to be multiplied with the maximum size.
     * @return New distance, scaled by the factor as stated above.
     */
    public static AbstractMeasure<ReadsBag> prodWithMaxSize(AbstractMeasure<Multiset<Sequence>> innerDistance) {
        return new ProductSimilarity<>(adapt(innerDistance), new MaxSizeSimilarity<>());
    }

    /**
     * Returns another measure that is just product of the argument and a
     * distance that returns maximum of the sizes of multisets. Version for the
     * embedded multisets.
     * @param <V> Target space of the embedding.
     * @param innerDistance Distance to be multiplied with the maximum size.
     * @return New distance, scaled by the factor as stated above.
     */
    public static <V> AbstractMeasure<ReadsBag> prodWithMaxSize2(AbstractMeasure<EmbeddedMultiset<EmbeddedSequence<V>, Sequence, V>> innerDistance) {
        return new ProductSimilarity<>(new EmbeddedReadsBagDistanceAdapter(innerDistance), new MaxSizeSimilarity<>());
    }

    /**
     * Adapts distance on multisets of sequences to distance on reads bag.
     * @param innerDistance Distance to adapt.
     * @return The same measure, only on reads bags.
     */
    public static AbstractMeasure<ReadsBag> adapt(AbstractMeasure<Multiset<Sequence>> innerDistance) {
        return new SequenceSimilarityAsReadBagSimilarity(innerDistance);
    }

}
