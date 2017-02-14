package cz.cvut.fel.ida.reads.experiment;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petr
 */
public class RunMethodsThread extends Thread {

    private final ExperimentSettings settings;
    private final List<Method> methods;
    private final List<ExtendedResult> results;
    private final int index;
    private final int nthreads;
    private final Path experimentFolder;

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
