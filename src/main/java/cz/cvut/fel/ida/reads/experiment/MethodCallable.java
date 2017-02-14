package cz.cvut.fel.ida.reads.experiment;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author petr
 */
public class MethodCallable implements Callable<ExtendedResult> {

    private final ExperimentSettings settings;
    private final Path experimentFolder;
    private final Method m;
    private final ExtendedResult r;

    public MethodCallable(ExperimentSettings settings, Method m, Path experimentFolder) {
        this.settings = settings;
        this.m = m;
        this.experimentFolder = experimentFolder;
        r = new ExtendedResult(m.getName(), null, null, null);
        r.put(Result.FINISHED, 0);
    }

    @Override
    public ExtendedResult call() throws Exception {
        Thread t = new MethodThread(settings, m, experimentFolder, r);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie) {
            t.stop();
            Thread.currentThread().interrupt();
        }

        return r;

    }

    public ExtendedResult invoke() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        MethodCallable callable = new MethodCallable(settings, m, experimentFolder);
        Future<ExtendedResult> future = executor.submit(callable);

        try {
            return future.get(settings.timelimit, TimeUnit.MINUTES);
        } catch (TimeoutException ex) {
            future.cancel(true);
            return r;
        } finally {
            executor.shutdownNow();
        }
    }

}
