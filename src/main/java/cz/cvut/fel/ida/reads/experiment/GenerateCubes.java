package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.Pair;
import cz.cvut.fel.ida.reads.util.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A main class used for generating cubes and averaging of results for multiple
 * experiments based on a set of parameters.
 *
 * @author Petr Ryšavý
 */
public class GenerateCubes {

    public static void main(String[] args) throws IOException {
        ExperimentSettings settings = ExperimentSettings.readFromSystemIn();

        for (int i = 0; i < 4; i++) {

            // check that the list is not too short
            if (2 * i >= settings.getReadLength().size() || 2 * i >= settings.getCoverage().size())
                break;

            Path cubesPath = settings.targetFolder.resolve("cubes" + i);
            Files.createDirectories(cubesPath);

            final List<Integer> rlAdmissible = CollectionUtils.filterOutExtremes(settings.getReadLength(), i);
            final List<Double> coverageAdmissible = CollectionUtils.filterOutExtremes(settings.getCoverage(), i);

            // just for now that as set, TODO
            LoadResults lr = new LoadResults(settings, CollectionUtils.asSet(1, 2, 3, 4, 5, 6, 7, 13, 14, 15, 16, 17));
            Map<Pair<Integer, Double>, List<Result>> results = lr.loadResults();
            String[] headers = lr.getHeaders();
            CubeGenerator cg = new CubeGenerator(headers, results);

            // first generate the full cube
            Files.write(
                    cubesPath.resolve("averages.dat"),
                    printResults(cg.generateCube(rlAdmissible, coverageAdmissible), headers),
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            );

            // now calculate the cubes for any readLength
            List<List<Result>> cubes = new ArrayList<>();
            for (Integer rl : settings.getReadLength()) {
                final List<Result> cube = cg.readLengthCube(rl, coverageAdmissible);
                // write the cube
                Files.write(
                        cubesPath.resolve("cube_rl_" + rl + ".dat"),
                        printResults(cube, headers),
                        StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
                );
                cubes.add(cube);
            }
            printCubesByParam(headers, settings.getReadLength(), cubes, cubesPath, "readLength");

            // now calculate the cubes for any coverage
            cubes.clear();
            for (Double coverage : settings.getCoverage()) {
                final List<Result> cube = cg.coverageCube(coverage, rlAdmissible);
                // write the cube
                Files.write(
                        cubesPath.resolve("cube_cov_" + (coverage.toString().replace('.', '-')) + ".dat"),
                        printResults(cube, headers),
                        StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
                );
                cubes.add(cube);
            }
            printCubesByParam(headers, settings.getCoverage(), cubes, cubesPath, "coverage");
        }
    }

    /**
     * Checks whether a parameter (for example run-time) should be long.
     * @param headerName Name of the parameter.
     * @return {@code true} if the parameter should be stored as natural number.
     */
    protected static boolean isLong(String headerName) {
        return headerName.equals(Result.NUMBER_OF_DATA) || headerName.equals(Result.FINISHED) || headerName.equals(Result.OUT_OF);
    }

    private static List<String> printResults(List<Result> results, String[] headers) {
        List<String> stringList = new ArrayList<>(results.size() + 1);
        stringList.add(String.format("%30s %17s %17s %s",
                "method",
                Result.FINISHED,
                Result.OUT_OF,
                StringUtils.toStringSpaced(headers, 17)));
        for (Result r : results)
            stringList.add(printResult(r, headers));
        return stringList;
    }

    private static String printResult(Result r, String[] headers) {
        StringBuilder sb = new StringBuilder(headers.length * 12 + 58);
        sb.append(String.format("%30s ", r.getMethodName()));
        sb.append(String.format("%17d ", r.get(Result.FINISHED).longValue()));
        sb.append(String.format("%17d ", r.get(Result.OUT_OF).longValue()));
        for (String st : headers)
            sb.append(String.format(isLong(st) ? "%17d " : "%17.10f ", isLong(st) ? r.get(st).longValue() : r.get(st)));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static void printCubesByParam(String[] headers, List<? extends Number> list, List<List<Result>> resultList, Path cubesDir, String type) throws IOException {
        printCubesByParam(Result.FINISHED, list, resultList, cubesDir, type);
        printCubesByParam(Result.OUT_OF, list, resultList, cubesDir, type);
        for (String header : headers)
            printCubesByParam(header, list, resultList, cubesDir, type);
    }

    private static void printCubesByParam(String header, List<? extends Number> list, List<List<Result>> resultList, Path cubesDir, String type) throws IOException {
        assert (list.size() == resultList.size());

        List<String> lines = new ArrayList<>();

        // the header line
        List<Result> first = resultList.get(0);
        StringBuilder sb = new StringBuilder(first.size() * 30 + 58);
        sb.append(String.format("%20s ", type));
        for (Result r : first)
            sb.append(String.format("%20s ", r.getMethodName()));
        sb.deleteCharAt(sb.length() - 1);
        lines.add(sb.toString());

        // now for each selection of readLength or coverage
        for (int i = 0; i < resultList.size(); i++) {
            final List<Result> result = resultList.get(i);
            final Number param = list.get(i);

            sb.delete(0, Integer.MAX_VALUE);
            sb.append(String.format(type.equals("coverage") ? "%3s%17.10f " : "%8s%12d", "", param));
            for (Result r : result)
                sb.append(String.format(isLong(header) ? "%8s%12d " : "%3s%17.10f ", "", isLong(header) ? r.get(header).longValue() : r.get(header)));
            sb.deleteCharAt(sb.length() - 1);
            lines.add(sb.toString());
        }

        Files.write(
                cubesDir.resolve("series_" + type + "_" + header.replace('/', '-') + ".dat"),
                lines,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}
