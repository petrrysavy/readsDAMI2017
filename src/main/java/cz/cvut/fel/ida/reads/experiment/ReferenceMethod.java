package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.io.FASTAMultipleFileLoader;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 */
public class ReferenceMethod extends SimilarityMethod<Sequence> {

    public ReferenceMethod(DistanceCalculator<Sequence, ? extends Number> distance) {
        super(distance, "reference");
    }

    @Override
    public Class<Sequence> getInputType() {
        return Sequence.class;
    }

    @Override
    public Sequence[] loadData(List<Path> paths) {
        return new FASTAMultipleFileLoader(paths).loadSequences();
    }

}
