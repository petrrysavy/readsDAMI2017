package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.UnorientedObject;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.IteratorWrapper;
import cz.cvut.fel.ida.reads.util.UnorientedIterator;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 * @param <K>
 * @param <V>
 */
public class MongeElkanEmbeddedDistance<T extends EmbeddedObject<K, V> & UnorientedObject<T>, K, V> extends AbstractMeasure<EmbeddedMultiset<T, K, V>> {
    
    private final DistanceCalculator<V, ? extends Number> embeddedDistance;
    private final DistanceCalculator<K, ? extends Number> innerDistance;
    private final int maxEmbeddedDistance;
    
    public MongeElkanEmbeddedDistance(DistanceCalculator<V, ? extends Number> embeddedDistance,
            DistanceCalculator<K, ? extends Number> innerDistance,
            int maxEmbeddedDistance) {
        this.embeddedDistance = embeddedDistance;
        this.innerDistance = innerDistance;
        this.maxEmbeddedDistance = maxEmbeddedDistance;
    }
    
    @Override
    public Double getDistance(EmbeddedMultiset<T, K, V> a, EmbeddedMultiset<T, K, V> b) {
        double embeddedMin;
        double distance = 0.0;

        // here we will store the sequences by the difference from the currently searched one
        List<List<T>> byDiffs = CollectionUtils.nLists(maxEmbeddedDistance + 1);
        
        for (T aElem : new IteratorWrapper<>(a.embeddedIterator())) {
            // initialize to find the least distant read in the other read set
            embeddedMin = Double.POSITIVE_INFINITY;
            
            CollectionUtils.clear(byDiffs);

            // iterate over the second read set to find the closest one
            for (T bElem : new UnorientedIterator<>(b.embeddedIterator())) {
                final double embeddedDist = embeddedDistance.getDistance(aElem.getProjected(), bElem.getProjected()).doubleValue();

                embeddedMin = Math.min(embeddedDist, embeddedMin);

                // TODO fix-me tohle je jen pro manhattanskou vzdálenost - co kdybych používal v budoucnu reálnou
                byDiffs.get(new Double(embeddedDist).intValue()).add(bElem);
            }
            
            // and finaly find the minimum from the candidate list
            double bestMatch = Double.POSITIVE_INFINITY;
//            for (T bElem : optimalInEmbedded)
            final int start = new Double(embeddedMin).intValue();
            assert (!byDiffs.get(start).isEmpty());
            for (int i = start; i < Math.min(start + 3, byDiffs.size()); i++)
                for (T bElem : byDiffs.get(i))
                    bestMatch = Math.min(innerDistance.getDistance(aElem.getObject(), bElem.getObject()).doubleValue(), bestMatch);
            distance += bestMatch * a.count(aElem.getObject());
        }
        return distance / a.size();
    }
    
}
