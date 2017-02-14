package cz.cvut.fel.ida.reads.embedded;

/**
 *
 * @author Petr Ryšavý
 * @param <K>
 * @param <V>
 */
public class EmbeddedObject<K, V> {
    private final K object;
    private final V projected;

    public EmbeddedObject(K object, V projected) {
        this.object = object;
        this.projected = projected;
    }

    public K getObject() {
        return object;
    }

    public V getProjected() {
        return projected;
    }
}
