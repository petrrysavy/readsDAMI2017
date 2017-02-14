package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author petr
 */
public class LoadResults {

    private String[] headers;
    private final ExperimentSettings settings;
    private final Set<Integer> rowIndices;

    public LoadResults(ExperimentSettings settings, Set<Integer> rowIndices) {
        this.rowIndices = rowIndices;
        this.settings = settings;
    }

    public Map<Pair<Integer, Double>, List<Result>> loadResults() throws IOException {
        loadHeader(getResultFile(settings.getReadLength().get(0), settings.getCoverage().get(0)));

        final Map<Pair<Integer, Double>, List<Result>> map
                = new HashMap<>(settings.getReadLength().size() * settings.getCoverage().size());
        for (int readLength : settings.getReadLength())
            for (double coverage : settings.getCoverage())
                map.put(new Pair<>(readLength, coverage), loadResult(getResultFile(readLength, coverage)));
        return map;
    }

    public String[] getHeaders() {
        return headers;
    }

    private Path getResultFile(int readLength, double coverage) {
        return settings.getBagsFolder(readLength, coverage).resolve("result").resolve("result.dat");
    }

    private void loadHeader(Path resultFile) throws IOException {
        BufferedReader br = Files.newBufferedReader(resultFile);
        final StringTokenizer st = new StringTokenizer(br.readLine());
        final List<String> headersL = new ArrayList<>(128);
        st.nextToken(); // method
        st.nextToken(); // finished
        // now the dynamic columns
        while (st.hasMoreTokens())
            headersL.add(st.nextToken());
        headers = new String[headersL.size()];
        headersL.toArray(headers);
    }

    private List<Result> loadResult(Path resultFile) throws IOException {
        List<Result> results = new ArrayList<>(rowIndices.size());
        final List<String> lines = Files.readAllLines(resultFile);
        for (int i = 1; i < lines.size(); i++)
            if (rowIndices.contains(i)) {
                StringTokenizer st = new StringTokenizer(lines.get(i));
                Result r = new Result(st.nextToken());
                r.put(Result.FINISHED, Integer.parseInt(st.nextToken()));
                for (String s : headers)
                    r.put(s, Double.parseDouble(st.nextToken()));
                results.add(r);
            }
        return results;
    }
}
