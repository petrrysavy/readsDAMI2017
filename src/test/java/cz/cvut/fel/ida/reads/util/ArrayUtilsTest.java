package cz.cvut.fel.ida.reads.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class ArrayUtilsTest {

    @Test
    public void testNTimes() {
        assertThat(ArrayUtils.nTimes(0.0, 0), is(equalTo(new double[]{})));
        assertThat(ArrayUtils.nTimes(0.0, 1), is(equalTo(new double[]{0.0})));
        assertThat(ArrayUtils.nTimes(0.0, 3), is(equalTo(new double[]{0.0, 0.0, 0.0})));
        assertThat(ArrayUtils.nTimes(Double.MAX_VALUE, 3), is(equalTo(new double[]{
            Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE})));
        assertThat(ArrayUtils.nTimes(0.8519018499, 3), is(equalTo(new double[]{
            0.8519018499, 0.8519018499, 0.8519018499})));
    }

    @Test
    public void testReversedCopy() {
        final char[] in = new char[]{'A', 'B', 'C', 'D', 'E'};
        final char[] out = ArrayUtils.reversedCopy(in);
        // check the output
        assertThat(out, is(equalTo(new char[]{'E', 'D', 'C', 'B', 'A'})));
        // and also check that the original was not modified
        assertThat(in, is(equalTo(new char[]{'A', 'B', 'C', 'D', 'E'})));
    }

}
