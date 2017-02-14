package cz.cvut.fel.ida.reads.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class StatsUtilsTest {

    @Test
    public void testGetPearsonsCorrelationCoefficient() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(1.0, 2, 3, 4, 5),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(1.0, 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient2() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(1.0, 2, 3, 4, 1),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(1.0 / Math.sqrt(17), 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient3() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(2.0, 4, 6, 8, 10),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(1.0, 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient4() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(-1.0, -2, -3, -4, -5),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(-1.0, 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient5() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(4.0, 56, 47, 14, 1),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(-8.0 / Math.sqrt(717), 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient6() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(4864.0, 4564, 456, 14, 45),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(-3547.0 / 2.0 / Math.sqrt(3894703), 1e-10))
        );
    }

    @Test
    public void testGetPearsonsCorrelationCoefficient7() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(1.0, 1, 2, 1, 1),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(0.0, 1e-10))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPearsonsCorrelationCoefficient8() {
        assertThat(StatsUtils.getPearsonsCorrelationCoefficient(
                ArrayUtils.asArray(1.0, 2, 3, 4, 5, 6),
                ArrayUtils.asArray(1.0, 2, 3, 4, 5)),
                is(closeTo(0.0, 1e-10))
        );
    }

}
