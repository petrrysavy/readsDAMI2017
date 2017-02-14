package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.util.AllValuesSet;
import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author petr
 */
public class CubeGenerator {

    private final AverageResults averager;
    private final Map<Pair<Integer, Double>, List<Result>> results;

    public CubeGenerator(String[] paramsList, Map<Pair<Integer, Double>, List<Result>> results) {
        this.averager = new AverageResults(paramsList);
        this.results = results;
    }

    public List<Result> generateCube(Collection<Integer> admissibleReadLengths, Collection<Double> admissibleCoverages) {
        List<List<Result>> filtered = new ArrayList<>();
        results.entrySet().stream()
                .filter(experimentResult -> admissibleReadLengths.contains(experimentResult.getKey().getValue1()))
                .filter(experimentResult -> admissibleCoverages.contains(experimentResult.getKey().getValue2()))
                .forEach((experimentResult) -> {
                    filtered.add(experimentResult.getValue());
                });
        return averager.averageResultFiles(filtered);
    }

    public List<Result> readLengthCube(Integer readLength, Collection<Double> admissibleCoverages) {
        return generateCube(CollectionUtils.asList(readLength), admissibleCoverages);
    }

    public List<Result> coverageCube(Double coverage, Collection<Integer> admissibleReadLengths) {
        return generateCube(admissibleReadLengths, CollectionUtils.asList(coverage));
    }
}
