package cz.cvut.fel.ida.reads.similarity;

/**
 * Jde o Java rozhraní, které
 *
 * @author Petr Ryšavý
 */
public interface Measure {

    public default boolean isSymmetric() {
        return true;
    }

    public default boolean isZeroOneNormalized() {
        return false;
    }
}
