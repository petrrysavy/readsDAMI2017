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
 * Class used for loading results from output text files.
 *
 * @author Petr Ryšavý
 */
public class LoadResults {

    /** List of headers noting measurement type, i.e. time/correlation, etc. */
    private String[] headers;
    /** Settings with experiment description. */
    private final ExperimentSettings settings;
    /** Indices for rows that should be considered. Not all results are useful. */
    private final Set<Integer> rowIndices;

    /**
     * Creates new object responsible for loading results.
     * @param settings Configuration of the experiment.
     * @param rowIndices Rows that should be used, i.e. methods that are
     * interresting for us. */
    public LoadResults(ExperimentSettings settings, Set<Integer> rowIndices) {
        this.rowIndices = rowIndices;
        this.settings = settings;
    }

    /**
     * Loads all results based on the experiment settings.
     * @return Map from read length and coverage to results of various methods.
     * @throws IOException When reading fails.
     */
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

    /**
     * Gets location of the file with result for selected coverage and read
     * length.
     * @param readLength Read length.
     * @param coverage Coverage.
     * @return Location of the result.
     */
    private Path getResultFile(int readLength, double coverage) {
        return settings.getBagsFolder(readLength, coverage).resolve("result").resolve("result.dat");
    }

    /**
     * Reads the header line with measurement types like time, correlation, etc.
     * @param resultFile Location of the file.
     * @throws IOException When reading fails.
     */
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

    /**
     * Reads the whole result of an experiment.
     * @param resultFile File with the result.
     * @return Parsed results.
     * @throws IOException When I/O fail happens.
     */
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
