
package cz.cvut.fel.ida.reads.util;

/**
 *
 * @author Petr Ryšavý
 */
public class KeyValue<T> implements Comparable<KeyValue<T>>
{
    private final double key;
    private final T value;

    public KeyValue(double key, T value)
    {
        this.key = key;
        this.value = value;
    }

    public double getKey()
    {
        return key;
    }

    public T getValue()
    {
        return value;
    }

    @Override
    public int compareTo(KeyValue<T> o)
    {
        return Double.compare(this.key, o.key);
    }

}
