package cz.cvut.fel.ida.reads.util;

/**
 *
 * @author petr
 */
public final class TicTac {

    private long timestamp;

    public TicTac() {
        tic();
    }

    public void tic() {
        timestamp = System.currentTimeMillis();
    }

    public long toc() {
        return System.currentTimeMillis() - timestamp;
    }

    public long toctic() {
        final long retval = toc();
        tic();
        return retval;
    }
}
