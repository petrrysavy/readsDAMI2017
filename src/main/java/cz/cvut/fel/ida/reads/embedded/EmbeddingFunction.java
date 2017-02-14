package cz.cvut.fel.ida.reads.embedded;

/**
 *
 * @author Petr Ryšavý
 * @param <K>
 * @param <V>
 */
public interface EmbeddingFunction<K, V> {
    public V project(K key);
}
