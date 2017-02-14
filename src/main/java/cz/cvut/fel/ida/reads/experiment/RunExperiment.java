package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.embedded.EmbeddedReadsBag;
import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.model.TreeNode;
import cz.cvut.fel.ida.reads.similarity.impl.FowlkesMallowsIndex;
import cz.cvut.fel.ida.reads.similarity.impl.TreeNodeFowlkesMallowsIndex;
import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.FlushReaderThread;
import cz.cvut.fel.ida.reads.util.IOUtils;
import cz.cvut.fel.ida.reads.util.Settings;
import cz.cvut.fel.ida.reads.util.StatsUtils;
import cz.cvut.fel.ida.reads.util.TicTac;
import cz.cvut.fel.ida.reads.util.Utils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This main class generates read sets using a simple configuration file.
 *
 * @author Petr Ryšavý
 */
public class RunExperiment {

    public static final Comparator COMPARATOR = new DatasetComparator();

    public static void main(String[] args) throws IOException, InterruptedException {
        ExperimentSettings settings = ExperimentSettings.readFromSystemIn();
        Settings.RANDOM.setSeed(settings.seed);
        if (args.length == 2)
            settings.select(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
        final int nthreads = settings.getNThreads();

        ExtendedResult reference = calculateReference(settings);
        if (reference == null) {
            Utils.dieWithMessage("!!! Was not able to finish reference !!!");
            return;
        }

        for (Integer readLength : settings.getReadLength()) {
            for (Double coverage : settings.getCoverage()) {
                final Path experimentFolder = settings.getBagsFolder(readLength, coverage);

                // here we will store assembly times
                final Map<String, Long> assemblyTime = CollectionUtils.pairWithAll(settings.assemblers, new Long(-1));

                runAssemblers(settings, experimentFolder, assemblyTime);

                List<Method> methods = settings.getMethods(readLength, coverage);
                ArrayList<ExtendedResult> results = new ArrayList<>(methods.size() + 1);
                results.add(reference);
                CollectionUtils.growToSize(results, methods.size() + 1);
                RunMethodsThread[] threads = new RunMethodsThread[nthreads];

                // start each thread and wait for them to finish
                for (int i = 0; i < nthreads; i++) {
                    threads[i] = new RunMethodsThread(settings, methods, results, i, nthreads, experimentFolder);
                    threads[i].start();
                }
                Utils.joinAll(threads);

                // now compare distances and compare trees
                for (ExtendedResult r : results) {
                    if (r.getOrDefault(Result.FINISHED, 0).intValue() == 1) {
                        r.put(Result.DISTANCE_MATRIX_CORRELATION, StatsUtils.getPearsonsCorrelationCoefficient(r.getDistances(), reference.getDistances()));
                        compareTrees(reference.getUpgmaTree(), r.getUpgmaTree(), Result.UPGMA_FOWLKES_MALLOWS_INDEX, r.get(Result.NUMBER_OF_DATA).intValue(), r);
                        compareTrees(reference.getNjTree(), r.getNjTree(), Result.NJ_FOWLKES_MALLOWS_INDEX, r.get(Result.NUMBER_OF_DATA).intValue(), r);
                    } else
                        r.put(Result.NUMBER_OF_DATA, settings.fileList.size());
                    r.put(Result.ASSEMBLY_TIME, assemblyTime.getOrDefault(r.getMethodName(), 0L));
                }

                List<String> stringList = printResults(results);
                IOUtils.printTo(stringList, new OutputStreamWriter(System.out));
                final Path resultFolder = experimentFolder.resolve("result");
                Files.createDirectories(resultFolder);
                Files.write(resultFolder.resolve("result.dat"), stringList, StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        }
    }

    private static ExtendedResult calculateReference(ExperimentSettings settings) throws InterruptedException {
        Logger.getLogger(RunExperiment.class.getName()).log(Level.INFO, "Going to calculate reference.");
        ExtendedResult reference;
        try {
            reference = new MethodCallable(settings, settings.getReferenceMethod(), settings.referenceFolder).invoke();
            if (reference.get(Result.FINISHED).intValue() != 1)
                reference = null;
        } catch (ExecutionException ex) {
            return null;
        }
        return reference;
    }

    private static void runAssemblers(ExperimentSettings settings, final Path experimentFolder, final Map<String, Long> assemblyTime) throws IOException, InterruptedException {
        final TicTac tictac = new TicTac();
        // run assemblers if needed, just one by one
        if (settings.runAssembly) {
            for (String assembler : settings.assemblers) {
                String command = String.format("%s %s %s %s",
                        "./sh/runassembly.sh",
                        assembler,
                        settings.getAssemblyFolder(assembler),
                        experimentFolder.toAbsolutePath().toString());
                Logger.getLogger(RunExperiment.class.getName()).log(Level.INFO, "Going to start assebly : " + assembler);
                Logger.getLogger(RunExperiment.class.getName()).log(Level.INFO, "command : " + command);

                tictac.tic();
                Process p = Runtime.getRuntime().exec(command);

                FlushReaderThread stdout = new FlushReaderThread(new InputStreamReader(p.getInputStream()));
                FlushReaderThread stderr = new FlushReaderThread(new InputStreamReader(p.getErrorStream()));

                stdout.start();
                stderr.start();

                p.waitFor(settings.timelimit, TimeUnit.MINUTES);
                if (p.isAlive()) {
                    Logger.getLogger(RunExperiment.class.getName()).log(Level.INFO, "Going to kill assembly, run out of time : " + assembler);
                    p.destroy();
                    stdout.interrupt();
                    stderr.interrupt();
                } else
                    assemblyTime.put(assembler, tictac.toc());
            }
        }
    }

    private static List<String> printResults(List<ExtendedResult> results) {
        List<String> stringList = new ArrayList<>(results.size() + 1);
        stringList.add(String.format("%30s %10s %10s %10s %10s %10s %10s %12s  %s  %s",
                "method",
                Result.FINISHED,
                Result.NUMBER_OF_DATA,
                Result.ASSEMBLY_TIME,
                Result.DISTANCE_MATRIX_TIME,
                Result.UPGMA_TREE_BUILD_TIME,
                Result.NJ_TREE_BUILD_TIME,
                Result.DISTANCE_MATRIX_CORRELATION,
                printFMIndexInfo(Result.UPGMA_FOWLKES_MALLOWS_INDEX, results.iterator().next()),
                printFMIndexInfo(Result.NJ_FOWLKES_MALLOWS_INDEX, results.iterator().next())));
        for (Result r : results)
            stringList.add(r.toString());
        return stringList;
    }

    public static String printFMIndexInfo(String prefix, Result r) {
        final int count = r.get(Result.NUMBER_OF_DATA).intValue();
        StringBuilder sb = new StringBuilder(count * 12);
        for (int k = 2; k < count; k++)
            sb.append(String.format("%12s ", prefix + k));
        return sb.toString();
    }

    public static String printFMIndex(String prefix, Result r) {
        final int count = r.get(Result.NUMBER_OF_DATA).intValue();
        StringBuilder sb = new StringBuilder(count * 12);
        for (int k = 2; k < count; k++)
            sb.append(String.format("%12.10f ", r.getOrDefault(prefix + k, Double.NaN).doubleValue()));
        return sb.toString();
    }

    public static void printTree(TreeNode t) {
        printTreeRecursively(t, 0);
    }

    private static void printTreeRecursively(TreeNode t, int spaces) {
        for (int i = 0; i < spaces; i++)
            System.out.print(" ");
        System.out.println(t.value == null ? "null : " + ((HierarchicalTreeNode) t).leftLength + " " + ((HierarchicalTreeNode) t).rightLength : getDatasetName(t.value));
        final int len = t.value == null ? 4 : t.toString().length();
        if (t.left != null)
            printTreeRecursively(t.left, spaces + len);
        if (t.right != null)
            printTreeRecursively(t.right, spaces + len);
    }

    public static String getDatasetName(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof String)
            return ((String) obj);
        if (obj instanceof Sequence)
            return ((Sequence) obj).getFile().getFileName().toString();
        if (obj instanceof ReadsBag)
            return ((ReadsBag) obj).getFile().getFileName().toString();
        if (obj instanceof EmbeddedReadsBag)
            return ((EmbeddedReadsBag) obj).getBag().getFile().getFileName().toString();

        throw new IllegalArgumentException("Unknown class : " + obj.getClass());
    }

    @SuppressWarnings("unchecked")
    public static void compareTrees(HierarchicalTreeNode expected, HierarchicalTreeNode actual, String prefix, int count, Result r) {
        TreeNodeFowlkesMallowsIndex<String> fmIndex = new TreeNodeFowlkesMallowsIndex<>(new FowlkesMallowsIndex<>());
        final HierarchicalTreeNode<String> expectedSt = toDatasetNameTree(expected);
        final HierarchicalTreeNode<String> actualSt = toDatasetNameTree(actual);

        for (int k = 2; k < count; k++) {
            fmIndex.setK(k);
            r.put(prefix + k, fmIndex.getSimilarity(actualSt, expectedSt));
        }
    }

    private static HierarchicalTreeNode<String> toDatasetNameTree(HierarchicalTreeNode node) {
        if (node == null)
            return null;

        return new HierarchicalTreeNode<>(
                toDatasetNameTree((HierarchicalTreeNode) node.left),
                node.leftLength,
                toDatasetNameTree((HierarchicalTreeNode) node.right),
                node.rightLength,
                getDatasetName(node.value));
    }
}
