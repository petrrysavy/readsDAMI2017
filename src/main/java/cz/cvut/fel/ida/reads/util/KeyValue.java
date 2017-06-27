package cz.cvut.fel.ida.reads.util;

/**
 * Tuple that stores a value together with double key.
 *
 * @author Petr Ryšavý
 */
public class KeyValue<T> implements Comparable<KeyValue<T>> {

    /** The key. */
    private final double key;
    /** The value assigned to this key. */
    private final T value;

    /**
     * Creates a new tuple with key and value.
     * @param key Key.
     * @param value Value.
     */
    public KeyValue(double key, T value) {
        this.key = key;
        this.value = value;
    }

    public double getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int compareTo(KeyValue<T> o) {
        return Double.compare(this.key, o.key);
    }

}
