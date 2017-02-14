package cz.cvut.fel.ida.reads.model;

/**
 * Jde o Java rozhraní, které
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public interface UnorientedObject<T extends UnorientedObject<T>> {

    public T reverse();

    public T complement();

    public default T reverseComplement() {
        return reverse().complement();
    }

}
