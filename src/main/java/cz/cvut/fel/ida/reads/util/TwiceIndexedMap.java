package cz.cvut.fel.ida.reads.util;

import java.util.HashMap;

/**
 *
 * @author Petr Ryšavý
 */
public class TwiceIndexedMap<K, V> extends HashMap<PairSet<K>, V> {

    public V put(K key1, K key2, V value) {
        return super.put(new PairSet<>(key1, key2), value);
    }

    public V get(K key1, K key2) {
        return super.get(new PairSet<>(key1, key2));
    }

    public V removeKey(K key1, K key2) {
        return super.remove(new PairSet<>(key1, key2));
    }
}
