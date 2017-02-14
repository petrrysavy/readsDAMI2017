package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import java.util.Collection;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class MaxSizeSimilarity<T extends Collection> extends AbstractMeasure<T> {

    @Override
    public Double getSimilarity(T a, T b) {
        throw new UnsupportedOperationException("not normalized");
    }

    @Override
    public Double getDistance(T a, T b) {
        return new Double(Math.max(a.size(), b.size()));
    }
    
}
