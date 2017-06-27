package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.embedded.MongeElkanEmbeddedDistance;
import cz.cvut.fel.ida.reads.embedded.TripletsEmbedding;
import cz.cvut.fel.ida.reads.io.FileType;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.similarity.MarginGapPenaulty;
import cz.cvut.fel.ida.reads.similarity.impl.BestMatchDistance;
import cz.cvut.fel.ida.reads.similarity.impl.ComplementDistance;
import cz.cvut.fel.ida.reads.similarity.impl.DistanceThreshold;
import cz.cvut.fel.ida.reads.similarity.impl.EditDistance;
import cz.cvut.fel.ida.reads.similarity.impl.LinearMarginGapPenalty;
import cz.cvut.fel.ida.reads.similarity.impl.ManhattanDistance;
import cz.cvut.fel.ida.reads.similarity.impl.MarginGapEditDistance;
import cz.cvut.fel.ida.reads.similarity.impl.MaxSizeSimilarity;
import cz.cvut.fel.ida.reads.similarity.impl.SymmetricMongeElkanDistance;
import cz.cvut.fel.ida.reads.similarity.impl.UnorientedComplementDistance;
import cz.cvut.fel.ida.reads.similarity.impl.UnorientedDistance;
import cz.cvut.fel.ida.reads.util.CommentsBufferedReader;
import cz.cvut.fel.ida.reads.util.DistanceUtils;
import cz.cvut.fel.ida.reads.util.ParseUtils;
import cz.cvut.fel.ida.reads.util.Utils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * The class that wraps settings loaded from the file. This file describes the
 * whole experiments in terms of used dataset, coverage etc.
 *
 * @author Petr Ryšavý
 */
public final class ExperimentSettings {

    /** Folder where reference files are stored. */
    public final Path referenceFolder;
    /** List of file names. */
    public final List<String> fileList;
    /** Coverage values that will be used in the experiment. */
    public final List<Double> coverageValues;
    /** Read length values that will be used. */
    public final List<Integer> readLengthValues;
    /** If {@code true}, then reads will be generated under assumption of cyclic
     * genome. */
    public final boolean cyclic;
    /** If {@code true} reads could be arbitrarily reversed. This means that no
     * we do not stick to 5' to 3' order. */
    public final boolean shouldReverse;
    /** If {@code true} then we assume that we do not know read strand. */
    public final boolean shouldComplement;
    /** Seed for random generators. */
    public final long seed;
    /** The random generator. */
    public final Random random;
    /** Folder with results, assembly files and read bags. */
    public final Path targetFolder;
    /** If {@code true} then assembly should be computed. */
    public final boolean runAssembly;
    /** List of assemblers. */
    public final List<String> assemblers;
    /** List of method names. This list is used for method generation. */
    public final List<String> methods;
    /** Timelimit for each method to finish. It is set in minutes. */
    public final int timelimit; // in minutes
    /** Outgroup sequence. Should be in the list of files. */
    public final String outgroup;
    /** Number of threads that will evaluate the particular methods. */
    public final int nThreads;
    /** Type of files used for storing read bags. FASTA or FASTQ. */
    private final FileType bagsFileType;
    /** Selected read length. If this is positive then the experiment should be
     * run only once with selected read length and coverage. */
    private int selectRL;
    /** Selected coverage. If this is positive then the experiment should be run
     * only once with selected read length and coverage. */
    private double selectCoverage;

    /**
     * Creates new instance of the class.
     * @param referenceFolder Folder where reference files are stored.
     * @param fileList List of file names.
     * @param coverageValues Coverage values that will be used in the
     * experiment.
     * @param readLengthValues Read length values that will be used.
     * @param cyclic If {@code true}, then reads will be generated under
     * assumption of cyclic genome.
     * @param shouldReverse If {@code true} reads could be arbitrarily reversed.
     * This means that no we do not stick to 5' to 3' order.
     * @param shouldComplement If {@code true} then we assume that we do not
     * know read strand.
     * @param seed Seed for random generators.
     * @param targetFolder Folder with results, assembly files and read bags.
     * @param runAssembly If {@code true} then assembly should be computed.
     * @param assemblers List of assemblers.
     * @param methods List of method names. This list is used for method
     * generation.
     * @param timelimit Timelimit for each method to finish. It is set in
     * minutes.
     * @param outgroup Outgroup sequence. Should be in the list of files.
     * @param nThreads Number of threads that will evaluate the particular
     * methods.
     * @param bagFileType Type of files used for storing read bags. FASTA or
     * FASTQ.
     * @param selectRL Selected read length. If this is positive then the
     * experiment should be run only once with selected read length and
     * coverage.
     * @param selectCoverage Selected coverage. If this is positive then the
     * experiment should be run only once with selected read length and
     * coverage.
     */
    public ExperimentSettings(Path referenceFolder,
            List<String> fileList,
            List<Double> coverageValues,
            List<Integer> readLengthValues,
            boolean cyclic,
            boolean shouldReverse,
            boolean shouldComplement,
            long seed,
            Path targetFolder,
            boolean runAssembly,
            List<String> assemblers,
            List<String> methods,
            int timelimit,
            String outgroup,
            int nThreads,
            FileType bagFileType,
            int selectRL,
            double selectCoverage
    ) {
        this.referenceFolder = referenceFolder;
        this.fileList = Collections.unmodifiableList(fileList);
        this.coverageValues = Collections.unmodifiableList(coverageValues);
        this.readLengthValues = Collections.unmodifiableList(readLengthValues);
        this.cyclic = cyclic;
        this.shouldReverse = shouldReverse;
        this.shouldComplement = shouldComplement;
        this.seed = seed;
        this.random = seed == -1 ? new Random() : new Random(seed);
        this.targetFolder = targetFolder;
        this.runAssembly = runAssembly;
        this.assemblers = Collections.unmodifiableList(assemblers);
        this.methods = Collections.unmodifiableList(methods);
        this.timelimit = timelimit;
        this.outgroup = outgroup;
        this.nThreads = nThreads;
        this.bagsFileType = bagFileType;
        select(selectRL, selectCoverage);
    }

    /** Loads settings from the system input.
     * @return The settings.
     * @throws IOException If reading fails. */
    public static ExperimentSettings readFromSystemIn() throws IOException {
        // initialize
        Path referenceFolder = Paths.get("");
        final List<String> fileList = new ArrayList<>();
        final List<Double> coverageValues = new ArrayList<>();
        final List<Integer> readLengthValues = new ArrayList<>();
        boolean cyclic = false;
        boolean shouldReverse = true;
        boolean shouldComplement = true;
        long seed = -1;
        Path targetFolder = null;
        boolean runAssembly = true;
        final List<String> assemblers = new ArrayList<>();
        final List<String> methods = new ArrayList<>();
        int timelimit = 120; // default two hours
        String outgroup = null;
        int selectRL = -1;
        double selectCoverage = -1.0;
        int nThreads = -1;
        FileType bagsFileType = FileType.FASTA;

        // read commands
        CommentsBufferedReader br = new CommentsBufferedReader("#", new InputStreamReader(System.in));
        String line;
        loop:
        while ((line = br.readLineNoComment().trim()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String command = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";

            switch (command) {
                case "files":
                    final int n = Integer.parseInt(st.nextToken());
                    if (st.hasMoreTokens())
                        referenceFolder = Paths.get(st.nextToken().trim());
                    for (int i = 0; i < n; i++)
                        fileList.add(br.readNonemptyLine());
                    break;
                case "coverage":
                    ParseUtils.parseAllToDoubleList(st, coverageValues);
                    break;
                case "readlength":
                    ParseUtils.parseAllToIntList(st, readLengthValues);
                    break;
                case "cyclic":
                    cyclic = ParseUtils.parseBoolean(st.nextToken());
                    break;
                case "reverse":
                    shouldReverse = ParseUtils.parseBoolean(st.nextToken());
                    break;
                case "complement":
                    shouldComplement = Boolean.parseBoolean(st.nextToken());
                    break;
                case "seed":
                    seed = Long.parseLong(st.nextToken());
                    break;
                case "target":
                    targetFolder = Paths.get(line.substring(7).trim());
                    break;
                case "runassembly":
                    runAssembly = Boolean.parseBoolean(st.nextToken());
                    break;
                case "assemblers":
                    ParseUtils.parseAllToStringList(st, assemblers);
                    break;
                case "methods":
                    final int m = Integer.parseInt(st.nextToken());
                    for (int i = 0; i < m; i++)
                        methods.add(br.readNonemptyLine());
                    break;
                case "timelimit":
                    timelimit = Integer.parseInt(st.nextToken());
                    break;
                case "outgroup":
                    outgroup = st.nextToken();
                    break;
                case "nthreads":
                    nThreads = Integer.parseInt(st.nextToken());
                    break;
                case "select":
                    selectRL = Integer.parseInt(st.nextToken());
                    selectCoverage = Double.parseDouble(st.nextToken());
                    break;
                case "bagsfiletype":
                    bagsFileType = FileType.byExtension(st.nextToken());
                    break;
                case "endinput":
                    break loop;
                default:
                    if (!command.isEmpty() && !line.startsWith("#"))
                        Utils.dieWithMessage("Unknown command : " + line);
                    break;
            }
        }

        // clean up unspecified values
        if (targetFolder == null) {
            targetFolder = Paths.get("");
        }
        if (outgroup == null)
            outgroup = fileList.get(0);
        if (!fileList.contains(outgroup))
            Utils.dieWithMessage("Outgroup must be in list of files.");
        return new ExperimentSettings(referenceFolder, fileList, coverageValues,
                readLengthValues, cyclic, shouldReverse, shouldComplement, seed,
                targetFolder, runAssembly, assemblers, methods, timelimit, outgroup,
                nThreads, bagsFileType, selectRL, selectCoverage);
    }

    private static DistanceCalculator<Sequence, ? extends Number> getOrientedDistance(boolean shouldReverse, boolean shouldComplement, DistanceCalculator<Sequence, ? extends Number> innerDistance) {
        if (shouldReverse && shouldComplement)
            return new UnorientedComplementDistance(innerDistance);
        else if (shouldReverse) // and not should complement
            return new UnorientedDistance(innerDistance);
        else if (shouldComplement) // and not shoudl reverse
            return new ComplementDistance(innerDistance);
        else // normal edit distance
            return innerDistance;
    }

    /**
     * Gets list with properly initialized methods.
     * @param readLength Read length for the experiment.
     * @param coverage Coverage for the experiment.
     * @return The list of methods.
     */
    public List<Method> getMethods(final int readLength, final double coverage) { // TODO split
        // and now generate the methods
        List<Method> methodsL = new ArrayList<>();
        final DistanceCalculator<Sequence, ? extends Number> editDistance = getOrientedDistance(shouldReverse, shouldComplement, new EditDistance());
        final MarginGapPenaulty gap = new LinearMarginGapPenalty(coverage, readLength, 2);
        final DistanceCalculator<Sequence, ? extends Number> marginGapDistance = getOrientedDistance(shouldReverse, shouldComplement, new MarginGapEditDistance(0, 1, 1, gap));

        for (String methodCommand : methods) {
            StringTokenizer st = new StringTokenizer(methodCommand);
            String command = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";
            double ratio = -1;
            if (command.equals("sample")) {
                final double sampled = Double.parseDouble(st.nextToken());
                command = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";
                ratio = sampled / coverage;
            }

            switch (command) {
                case "maxsize":
                    methodsL.add(new ReadsBagMethod(new MaxSizeSimilarity<>(), "maxSize", bagsFileType));
                    break;
                case "mongeelkan":
                    methodsL.add(new ReadsBagMethod(DistanceUtils.adapt(new SymmetricMongeElkanDistance<>(editDistance)), "MongeElkan", bagsFileType));
                    break;
                case "mongeelkanscale":
                    methodsL.add(new ReadsBagMethod(DistanceUtils.prodWithMaxSize(new SymmetricMongeElkanDistance<>(editDistance)), "MongeElkanScale", bagsFileType));
                    break;
                case "margingaps":
                    methodsL.add(getMethodIf(new ReadsBagMethod(DistanceUtils.prodWithMaxSize(new SymmetricMongeElkanDistance<>(marginGapDistance)), "MarginGaps", bagsFileType), coverage > 0.5));
                    break;
                case "threshold":
                    final double threshold = Double.parseDouble(st.nextToken());
                    methodsL.add(getMethodIf(new ReadsBagMethod(DistanceUtils.prodWithMaxSize(new SymmetricMongeElkanDistance<>(new DistanceThreshold<>(marginGapDistance, threshold * readLength, readLength))), command, bagsFileType), coverage > 0.5));
                    break;
                case "tripletsmg":
                    methodsL.add(getMethodIf(new EmbeddedMethod(DistanceUtils.prodWithMaxSize2(new MongeElkanEmbeddedDistance<>(new ManhattanDistance(), marginGapDistance, 3000)), new TripletsEmbedding(), "tripletsMG", bagsFileType, ratio), coverage > 0.5));
                    break;
                case "assemblylongest":
                    for (String assembler : assemblers)
                        methodsL.add(new AssemblyMethod(editDistance, assembler));
                    break;
                case "assemblybest":
                    for (String assembler : assemblers)
                        methodsL.add(new CombinedMethod(DistanceUtils.adapt(new BestMatchDistance<>(editDistance)), assembler));
                    break;
                default:
                    if (!command.isEmpty() && !command.startsWith("#"))
                        Utils.dieWithMessage("Unknown method : " + methodCommand);
                    break;
            }

            if (ratio > 0.0 && ratio < 1.0 && methodsL.get(methodsL.size() - 1).getInputType() == ReadsBag.class)
                methodsL.set(methodsL.size() - 1, new SamplingMethod(methodsL.get(methodsL.size() - 1), ratio));
        }
        return methodsL;
    }

    /**
     * Gets method if the condition is {@code true}. Otherwise returns a dummy
     * method that fails on every dataset.
     * @param m Method to get.
     * @param condition Condition that must be satisfied.
     * @return Method {@code m} or a dummy method.
     */
    private Method getMethodIf(Method m, boolean condition) {
        if (condition)
            return m;
        else
            return new UnsupportedMethod(m.getName());
    }

    /**
     * Gets the reference.
     * @return The reference.
     */
    public Method getReferenceMethod() {
        return new ReferenceMethod(getOrientedDistance(shouldReverse, shouldComplement, new EditDistance()));
    }

    /** Gets list of coverages to use. If nothing is selected, all values are
     * used, otherwise only the selected one.
     * @return Coverage list. */
    public List<Double> getCoverage() {
        return selectCoverage <= 0 ? coverageValues : Arrays.asList(selectCoverage);
    }

    /** Gets list of read lengths to use. If nothing is selected, all values are
     * used, otherwise only the selected one.
     * @return Read length list. */
    public List<Integer> getReadLength() {
        return selectRL <= 0 ? readLengthValues : Arrays.asList(selectRL);
    }

    /** Selects a particular read length and coverage for the experiment.
     * @param selectReadLength Read length.
     * @param selectCoverage Coverage. */
    public void select(int selectReadLength, double selectCoverage) {
        if (selectReadLength > 0 && !readLengthValues.contains(selectReadLength))
            throw new IllegalArgumentException("Unknown read length value : " + selectReadLength);
        if (selectCoverage > 0 && !coverageValues.contains(selectCoverage))
            throw new IllegalArgumentException("Unknown coverage value : " + selectCoverage);

        this.selectCoverage = selectCoverage;
        this.selectRL = selectReadLength;
    }

    /** Gets unique string representation of read length and coverage. This
     * string shuold be used to name folders for the particular read length and
     * coverage.
     * @param readLength The read length.
     * @param coverage The coverage.
     * @return Identification of the values. */
    public String getCoverageRLString(int readLength, double coverage) {
        final String coverageSt;
        if (coverage == (long) coverage)
            coverageSt = Long.toString((long) coverage);
        else
            coverageSt = Double.toString(coverage).replace(".", "-");
        return coverageSt + "_" + readLength;
    }

    /** Gets path to reads bag with the experiment for selected read length and
     * coverage.
     * @param readLength The read length.
     * @param coverage The coverage.
     * @return Folder with reads bags.
     */
    public Path getBagsFolder(int readLength, double coverage) {
        return targetFolder.resolve("bags_" + getCoverageRLString(readLength, coverage));
    }

    /**
     * Name of assembly folder for the selected assembler.
     * @param assembler Assembler name.
     * @return Name of the folder that contains assembly files.
     */
    public String getAssemblyFolder(String assembler) {
        return "assembly_" + assembler;
    }

    /** Gets number of threads. If not set, uses the number of cores minus one.
     * @return Number of threads to use. */
    public int getNThreads() {
        if (nThreads <= 0)
            return Math.max(Runtime.getRuntime().availableProcessors() - 1, 1);
        else
            return nThreads;
    }
}
