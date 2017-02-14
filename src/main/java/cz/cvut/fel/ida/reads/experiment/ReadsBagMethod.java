package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.io.FileType;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.util.IOUtils;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 */
public class ReadsBagMethod extends SimilarityMethod<ReadsBag> {

    private final FileType bagsFormat;

    public ReadsBagMethod(AbstractMeasure<ReadsBag> similarity, String name) {
        this(similarity, name, FileType.FASTA);
    }

    public ReadsBagMethod(AbstractMeasure<ReadsBag> similarity, String name, FileType bagsFormat) {
        super(similarity, name);
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
