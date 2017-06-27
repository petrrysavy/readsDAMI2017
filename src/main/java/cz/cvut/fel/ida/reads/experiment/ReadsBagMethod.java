package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.io.FileType;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.util.IOUtils;
import java.nio.file.Path;
import java.util.List;

/**
 * A method based on calculating similarity on reads bags.
 *
 * @author Petr Ryšavý
 */
public class ReadsBagMethod extends SimilarityMethod<ReadsBag> {

    /** File format of the input. */
    private final FileType bagsFormat;

    /**
     * Instantiates the method.
     * @param distance Distance measure.
     * @param name Name of the method.
     * @param bagsFormat Type of the data files.
     */
    public ReadsBagMethod(AbstractMeasure<ReadsBag> distance, String name, FileType bagsFormat) {
        super(distance, name);
        this.bagsFormat = bagsFormat;
    }

    @Override
    public Class<ReadsBag> getInputType() {
        return ReadsBag.class;
    }

    @Override
    public ReadsBag[] loadData(List<Path> files) {
        return IOUtils.newReadBagGroupLoader(files, bagsFormat).loadReadBags();
    }
}
