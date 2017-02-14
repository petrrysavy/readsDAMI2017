package cz.cvut.fel.ida.reads.embedded;

import java.util.Iterator;

/**
 * Jde o Java rozhraní, které
 *
 * @author Petr Ryšavý
 * @param <T> Type of the embedded object that is used.
 * @param <K> The original space key.
 * @param <V> The projected key.
 */
public interface EmbeddedMultiset<T extends EmbeddedObject<K, V>, K, V> extends Iterable<K> {

    public int size();

    public int count(Object o);
	
	public Iterator<T> embeddedIterator();

}
