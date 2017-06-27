package cz.cvut.fel.ida.reads.experiment;

import java.nio.file.Path;
import java.util.List;

/**
 * Dummy method to indicate that a method cannot be run.
 *
 * @author Petr Ryšavý
 */
public class UnsupportedMethod extends SimilarityMethod<Void> {

    /**
     * Creates a new instance of the dummy method.
     * @param name Name of the method.
     */
    public UnsupportedMethod(String name) {
        super(null, name);
    }

    @Override
    public boolean areDataCorrect(Void[] data) {
        return false;
    }

    @Override
    public Class<Void> getInputType() {
        return void.class;
    }

    @Override
    public Void[] loadData(List<Path> files) {
        return new Void[0];
    }

}
