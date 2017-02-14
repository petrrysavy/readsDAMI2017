package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class EmbeddedReadsBag<T> extends AbstractCollection<Sequence> implements EmbeddedMultiset<EmbeddedSequence<T>, Sequence, T> {

    private final ReadsBag bag;
    private final List<EmbeddedSequence<T>> list;

    public EmbeddedReadsBag(ReadsBag bag, EmbeddingFunction<Sequence, T> function) {
        this.bag = bag;
        // build the unmodifiable view of the reads bag that contains the embedding
        List<EmbeddedSequence<T>> tmp = new ArrayList<>(bag.uniqueSize());
        for (Sequence s : bag.toSet())
            tmp.add(new EmbeddedSequence<>(s, function));
        list = Collections.unmodifiableList(tmp);
    }

    @Override
    public Iterator<Sequence> iterator() {
        return bag.iterator();
    }

    @Override
    public Iterator<EmbeddedSequence<T>> embeddedIterator() {
        return list.iterator();
    }

    @Override
    public int size() {
        return bag.size();
    }

    @Override
    public int count(Object o) {
        return bag.count(o);
    }

    public ReadsBag getBag() {
        return bag;
    }

}
