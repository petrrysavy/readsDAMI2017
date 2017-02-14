package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petr
 */
public class AverageResults {

    private final String[] paramsList;

    public AverageResults(String[] paramsList) {
        this.paramsList = paramsList;
    }

    public List<Result> averageResultFiles(Collection<List<Result>> list) {
        final List<List<Result>> byMethod = CollectionUtils.zip(list);
        List<Result> averaged = new ArrayList<>(byMethod.size());
        byMethod.stream().forEach((oneMethod) -> {
            averaged.add(averageResults(oneMethod));
        });
        return averaged;
    }

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
