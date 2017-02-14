package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.algo.OutgroupRerootify;
import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.util.ArrayUtils;
import cz.cvut.fel.ida.reads.util.TicTac;
import java.nio.file.Path;

class MethodThread extends Thread {

    private final ExperimentSettings settings;
    private final Path experimentFolder;
    private final Method m;
    private final ExtendedResult r;

    public MethodThread(ExperimentSettings settings, Method m, Path experimentFolder, ExtendedResult result) {
        this.settings = settings;
        this.m = m;
        this.experimentFolder = experimentFolder;
        this.r = result;
    }

    @Override
    public void run() {
        System.err.println("------------------------------------------------------");
        System.err.println("Running method " + m.getName());

        TicTac tictac = new TicTac();
        Object[] data = m.loadData(experimentFolder, settings.fileList);
        if (!m.areDataCorrect(data) || data.length != settings.fileList.size()) {
            System.err.println("data incorrect " + m.getName() + " corr " + m.areDataCorrect(data) + " datalen " + data.length + " should " + settings.fileList.size());
            return; // data are incorrect, method failed
        }
        r.put(Result.NUMBER_OF_DATA, data.length);

        final double[][] distances = m.getDistanceMatrix(data);
        r.put(Result.DISTANCE_MATRIX_TIME, tictac.toc());
        final double[] distancesFlat = ArrayUtils.flattern(distances);
        r.setDistances(distancesFlat);
        tictac.tic();

        final HierarchicalTreeNode upgmaTree = m.buildTree(data, distances, TreeType.UPGMA_TREE);
        r.put(Result.UPGMA_TREE_BUILD_TIME, tictac.toc());
        r.setUpgmaTree(upgmaTree);

        tictac.tic();
        final HierarchicalTreeNode njTree = new OutgroupRerootify(m.buildTree(data, distances, TreeType.NEIGHBOR_JOINING_TREE)).outgroopReRootify(settings.outgroup, RunExperiment.COMPARATOR);
        r.put(Result.NJ_TREE_BUILD_TIME, tictac.toc());
        r.setNjTree(njTree);
        r.put(Result.FINISHED, 1);

        RunExperiment.printTree(njTree);
    }
}
