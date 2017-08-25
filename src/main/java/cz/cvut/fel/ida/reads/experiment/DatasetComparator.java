/* 
 * Copyright (C) 2017 Petr Ryšavý <petr.rysavy@fel.cvut.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
