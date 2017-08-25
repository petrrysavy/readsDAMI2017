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

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Class that is capable of running a method and fetching the result. To obtain
 * the result and run the method within a time limit, you need only to
 * instantiate this class and call {@link #invoke() } method.
 *
 * @author Petr Ryšavý
 */
public class MethodCallable implements Callable<ExtendedResult> {

    /** Settings of the experiment. */
    private final ExperimentSettings settings;
    /** Location of the experiment. */
    private final Path experimentFolder;
    /** Method that will be evaluated. */
    private final Method m;
    /** Fetched results. */
    private final ExtendedResult r;

    /**
     * Creates new callable that will run thread with a method.
     * @param settings Settings of the experiment.
     * @param m Method to be tested.
     * @param experimentFolder Location of the experiment.
     */
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

    /**
     * Invokes the method and waits for it to finish up to a given time limit.
     * @return The result of the run.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public ExtendedResult invoke() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ExtendedResult> future = executor.submit(this);

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
