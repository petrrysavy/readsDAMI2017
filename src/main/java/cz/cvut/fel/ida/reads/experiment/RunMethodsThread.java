package cz.cvut.fel.ida.reads.experiment;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread that just runs list of methods one by one and captures the results.
 * Runs methods that give modulo {@code index} after dividing by the number of
 * threads {@code nthreads}.
 *
 * @author Petr Ryšavý
 */
public class RunMethodsThread extends Thread {

    /** Settings of the experiments. */
    private final ExperimentSettings settings;
    /** List of the methods to be evaluated. Not all will be run. This should be
     * shared among multiple instances of this class. */
    private final List<Method> methods;
    /** Results for each of the methods. In the same order as the methods. This
     * should be shared among multiple instances of this class and only values
     * belonging to this particular instance should be modified. */
    private final List<ExtendedResult> results;
    /** Index of this thread. */
    private final int index;
    /** Number of threads = number of running instances of this class. */
    private final int nthreads;
    /** Folder with the experiment data. */
    private final Path experimentFolder;

    /**
     * Creates new thread.
     * @param settings Settings of the experiments.
     * @param methods List of the methods to be evaluated. Not all will be run.
     * This should be shared among multiple instances of this class.
     * @param results Results for each of the methods. In the same order as the
     * methods. This should be shared among multiple instances of this class and
     * only values belonging to this particular instance should be modified.
     * @param index Index of this thread.
     * @param nthreads Number of threads = number of running instances of this
     * class.
     * @param experimentFolder Folder with the experiment data.
     */
    public RunMethodsThread(ExperimentSettings settings, List<Method> methods, List<ExtendedResult> results, int index, int nthreads, Path experimentFolder) {
        this.settings = settings;
        this.methods = methods;
        this.results = results;
        this.index = index;
        this.nthreads = nthreads;
        this.experimentFolder = experimentFolder;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < methods.size(); i++)
                if ((i % nthreads) == index)
                    // the first index is reserved for reference
                    results.set(i + 1, new MethodCallable(settings, methods.get(i), experimentFolder).invoke());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(RunMethodsThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(RunMethodsThread.class.getName()).log(Level.INFO, "Run method thread finished : {0}", index);
    }
}
