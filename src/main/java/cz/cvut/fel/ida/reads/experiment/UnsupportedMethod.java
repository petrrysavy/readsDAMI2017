package cz.cvut.fel.ida.reads.experiment;

import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author petr
 */
public class UnsupportedMethod extends SimilarityMethod<Void> {

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
