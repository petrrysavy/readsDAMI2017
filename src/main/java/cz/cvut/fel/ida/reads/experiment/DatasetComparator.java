package cz.cvut.fel.ida.reads.experiment;

import java.util.Comparator;

/**
 * Comparator for datasets. Should be able to compare two datasets no matter
 * whether they are represented as sequences/reads bags or assembled contigs.
 *
 * @author Petr Ryšavý
 */
public class DatasetComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        final String s1 = RunExperiment.getDatasetName(o1);
        final String s2 = RunExperiment.getDatasetName(o2);
        return s1 == null ? (s2 == null ? 0 : -1) : (s2 == null ? 1 : s1.compareTo(s2));
    }
}
