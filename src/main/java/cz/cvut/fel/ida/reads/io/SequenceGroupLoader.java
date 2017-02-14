package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.Sequence;

/**
 *
 * @author Petr Ryšavý
 */
public interface SequenceGroupLoader
{
    public Sequence[] loadSequences();
}
