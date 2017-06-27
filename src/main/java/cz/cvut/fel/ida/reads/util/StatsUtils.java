package cz.cvut.fel.ida.reads.util;

/**
 * Utility class that is capable of calculating various statistics.
 *
 * @author Petr Ryšavý
 */
public final class StatsUtils {

    /** Do not let anybody to instantiate the class. */
    private StatsUtils() {
    }

    /**
     * Calculates the Pearson's correlation coefficient.
     * @param x One random variable.
     * @param y Second random variable.
     * @return Correlation between {@code x} and {@code y}.
     * @see https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
     */
    public static double getPearsonsCorrelationCoefficient(double[] x, double[] y) {
        if (x.length != y.length)
            throw new IllegalArgumentException("Pearson's correlation coefficient can be calculated only on random variables that are on the same domain.");

        final double xAverage = MathUtils.average(x);
        final double yAverage = MathUtils.average(y);

        double nominator = 0.0;
        double stdevX2 = 0.0;
        double stdevY2 = 0.0;

        for (int i = 0; i < x.length; i++) {
            final double xDiff = x[i] - xAverage;
            final double yDiff = y[i] - yAverage;

            nominator += xDiff * yDiff;
            stdevX2 += xDiff * xDiff;
            stdevY2 += yDiff * yDiff;
        }

        return nominator / Math.sqrt(stdevX2 * stdevY2);
    }
}
