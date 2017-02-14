package cz.cvut.fel.ida.reads.embedded;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.model.UnorientedObject;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class EmbeddedSequence<T> extends EmbeddedObject<Sequence, T> implements UnorientedObject<EmbeddedSequence<T>> {

    private EmbeddedSequence<T> reverse;
    private EmbeddedSequence<T> complement;

    private EmbeddedSequence(Sequence s, T projection) {
        super(s, projection);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public EmbeddedSequence(Sequence s, EmbeddingFunction<Sequence, T> function) {
        this(s, function.project(s));
        reverse = new EmbeddedSequence<>(s.reverse(), function.project(s.reverse()));
        complement = new EmbeddedSequence<>(s.complement(), function.project(s.complement()));
        EmbeddedSequence<T> reverseComplement = new EmbeddedSequence<>(s.complementCopy(), function.project(s.complementCopy()));

        // set the links properly - this is already set
        this.reverse.reverse = this;
        this.reverse.complement = reverseComplement;
        this.complement.reverse = reverseComplement;
        this.complement.complement = this;
        reverseComplement.reverse = this.complement;
        reverseComplement.complement = this.reverse;
    }

    @Override
    public EmbeddedSequence<T> reverse() {
        return reverse;
    }

    @Override
    public EmbeddedSequence<T> complement() {
        return complement;
    }
}
