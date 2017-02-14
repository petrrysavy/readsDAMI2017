package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;

/**
 *
 * @author Petr Ryšavý
 */
public class ManhattanDistance extends AbstractMeasure<int[]> {

    @Override
    public Double getDistance(int[] a, int[] b) {
        assert(a.length == b.length);
        
        int distance = 0;
        for(int i = 0; i < a.length; i++)
            distance += Math.abs(a[i] - b[i]);
        return new Double(distance);
    }
}
