package cz.cvut.fel.ida.reads.similarity;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public abstract class AbstractMeasure<T> implements DistanceCalculator<T, Double>, SimilarityCalculator<T, Double> {

    @Override
    public Double getDistance(T a, T b) {
        if (isZeroOneNormalized())
            return 1.0 - getSimilarity(a, b);
        else
            throw new UnsupportedOperationException("Method getDistance() is not implemented.");
    }

    @Override
    public Double getSimilarity(T a, T b) {
        if (isZeroOneNormalized())
            return 1.0 - getDistance(a, b);
        else
            throw new UnsupportedOperationException("Method getSimilarity() is not implemented.");
    }
}
