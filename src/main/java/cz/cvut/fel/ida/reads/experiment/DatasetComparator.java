package cz.cvut.fel.ida.reads.experiment;

import java.util.Comparator;

/**
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
