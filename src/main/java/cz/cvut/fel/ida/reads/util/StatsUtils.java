package cz.cvut.fel.ida.reads.util;

/**
 *
 * @author Petr Ryšavý
 */
public final class StatsUtils {

    private StatsUtils() {
    }

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
