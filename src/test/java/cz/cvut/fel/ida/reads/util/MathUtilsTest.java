package cz.cvut.fel.ida.reads.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Petr Ryšavý
 */
public class MathUtilsTest {

    @Test
    public void testMin_Int() {
        assertThat(MathUtils.min(1, 2, 3), is(equalTo(1)));
        assertThat(MathUtils.min(1, 3, 2), is(equalTo(1)));
        assertThat(MathUtils.min(2, 1, 3), is(equalTo(1)));
        assertThat(MathUtils.min(2, 3, 1), is(equalTo(1)));
        assertThat(MathUtils.min(3, 2, 1), is(equalTo(1)));
        assertThat(MathUtils.min(3, 1, 2), is(equalTo(1)));
    }

    @Test
    public void testMin_Double() {
        assertThat(MathUtils.min(1.0, 2.0, 3.0), is(equalTo(1.0)));
        assertThat(MathUtils.min(1.0, 3.0, 2.0), is(equalTo(1.0)));
        assertThat(MathUtils.min(2.0, 1.0, 3.0), is(equalTo(1.0)));
        assertThat(MathUtils.min(2.0, 3.0, 1.0), is(equalTo(1.0)));
        assertThat(MathUtils.min(3.0, 2.0, 1.0), is(equalTo(1.0)));
        assertThat(MathUtils.min(3.0, 1.0, 2.0), is(equalTo(1.0)));
    }

    @Test
    public void testAverage() {
        assertThat(MathUtils.average(0.0, 0.0), is(equalTo(0.0)));
        assertThat(MathUtils.average(1.0, 1.0), is(equalTo(1.0)));
        assertThat(MathUtils.average(1.0, 0.0), is(equalTo(0.5)));
        assertThat(MathUtils.average(0.0, 1.0), is(equalTo(0.5)));
        assertThat(MathUtils.average(0.5291677427, 0.1145972106), is(equalTo(0.32188247665)));
        assertThat(MathUtils.average(0.1145972106, 0.5291677427), is(equalTo(0.32188247665)));
    }

    @Test
    public void testPow() {
        assertThat(MathUtils.pow(0, 1), is(equalTo(0)));
        assertThat(MathUtils.pow(1, 3), is(equalTo(1)));
        assertThat(MathUtils.pow(5, 0), is(equalTo(1)));
        assertThat(MathUtils.pow(7, 1), is(equalTo(7)));
        assertThat(MathUtils.pow(2, 2), is(equalTo(4)));
        assertThat(MathUtils.pow(2, 3), is(equalTo(8)));
        assertThat(MathUtils.pow(2, 4), is(equalTo(16)));
        assertThat(MathUtils.pow(2, 5), is(equalTo(32)));
        assertThat(MathUtils.pow(7, 2), is(equalTo(49)));
        assertThat(MathUtils.pow(5, 3), is(equalTo(125)));
        assertThat(MathUtils.pow(-5, 3), is(equalTo(-125)));
        assertThat(MathUtils.pow(-2, 11), is(equalTo(-2048)));
        assertThat(MathUtils.pow(-2, 12), is(equalTo(4096)));
    }

}
