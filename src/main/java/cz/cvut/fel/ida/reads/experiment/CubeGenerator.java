package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class used for generating cubes of results based on various parameters.
 *
 * @author petr
 */
public class CubeGenerator {

    /** This will help us to calculate average. */
    private final AverageResults averager;
    /** The results to be averaged. Pair of indices are parameters: read length
     * and coverage. List of results contains values for each method. */
    private final Map<Pair<Integer, Double>, List<Result>> results;

    /**
     * Creates new generator of cubes.
     * @param paramsList List of parameters to be averaged.
     * @param results The results to be averaged. Pair of indices are
     * parameters: read length and coverage. List of results contains values for
     * each method.
     */
    public CubeGenerator(String[] paramsList, Map<Pair<Integer, Double>, List<Result>> results) {
        this.averager = new AverageResults(paramsList);
        this.results = results;
    }

    /**
     * Generates a cube by averaging over list of parameters.
     * @param admissibleReadLengths Read length list.
     * @param admissibleCoverages Coverage list.
     * @return Cube where only results for selected coverage and read length are
     * taken into account.
     */
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

    /**
     * Generates cube for one particular read length by averaging over coverage
     * values.
     * @param readLength Selected read length.
     * @param admissibleCoverages Coverage values to be averaged over.
     * @return Average result.
     */
    public List<Result> readLengthCube(Integer readLength, Collection<Double> admissibleCoverages) {
        return generateCube(CollectionUtils.asList(readLength), admissibleCoverages);
    }

    /**
     * Generates cube for one particular coverage by averaging over read
     * lengths.
     * @param coverage Selected coverage value.
     * @param admissibleReadLengths Read lengths that will be used.
     * @return Average result.
     */
    public List<Result> coverageCube(Double coverage, Collection<Integer> admissibleReadLengths) {
        return generateCube(admissibleReadLengths, CollectionUtils.asList(coverage));
    }
}
