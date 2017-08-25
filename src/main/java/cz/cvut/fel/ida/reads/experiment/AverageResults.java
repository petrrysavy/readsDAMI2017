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

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for calculating average of the results. This is useful when we
 * need to calculate result for multiple experiments at once.
 *
 * @author petr
 */
public class AverageResults {

    /** List of parameters to be averaged. */
    private final String[] paramsList;

    /**
     * Creates a new instance of the class.
     * @param paramsList List of parameters that will be averaged over the list
     * of results.
     */
    public AverageResults(String[] paramsList) {
        this.paramsList = paramsList;
    }

    /**
     * Calculates an average results for several result file. Each list in the
     * {@code list} collection represents one result of an experiment with
     * several methods. Then elements in those lists are particular runs of
     * particular methods. The methods in the list must be in the same order.
     * @param list List of experiment results.
     * @return A lsit of results that are averages for all experiments for each
     * particular method.
     */
    public List<Result> averageResultFiles(Collection<List<Result>> list) {
        final List<List<Result>> byMethod = CollectionUtils.zip(list);
        List<Result> averaged = new ArrayList<>(byMethod.size());
        byMethod.stream().forEach((oneMethod) -> {
            averaged.add(averageResults(oneMethod));
        });
        return averaged;
    }

    /**
     * Takes a list of results and calculates the average. It is meant to
     * average results for one method for several settings.
     * @param list List of results.
     * @return An average result.
     */
    public Result averageResults(List<Result> list) {
        final Result result = new Result(list.get(0).getMethodName());
        result.put(Result.OUT_OF, list.size());
        result.put(Result.FINISHED, 0);
        for (String param : paramsList)
            result.put(param, 0.0);

        for (Result r : list) {
            assert (r.getMethodName().equals(result.getMethodName()));

            if (r.get(Result.FINISHED).intValue() == 1) {
                result.put(Result.FINISHED, result.get(Result.FINISHED).intValue() + 1);
                for (String param : paramsList) {
                    double value = r.get(param).doubleValue();
                    if (Double.isNaN(value))
                        if (param.equals(Result.DISTANCE_MATRIX_CORRELATION))
                            value = 0.0;
                        else
                            Logger.getLogger(AverageResults.class.getName()).log(Level.INFO, "NaN in : " + param);
                    result.put(param, result.get(param).doubleValue() + value);
                }
            }
        }

        final int finished = result.get(Result.FINISHED).intValue();
        for (String param : paramsList)
            result.put(param, result.get(param).doubleValue() / finished);
        return result;
    }
}
