package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.embedded.EmbeddedReadsBag;
import cz.cvut.fel.ida.reads.embedded.EmbeddingFunction;
import cz.cvut.fel.ida.reads.io.FileType;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import cz.cvut.fel.ida.reads.util.IOUtils;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 */
public class EmbeddedMethod<T> extends SimilarityMethod<EmbeddedReadsBag> {

    private final EmbeddingFunction<Sequence, T> embedding;
    private final FileType bagsFormat;
    private final double samplingRatio;

    public EmbeddedMethod(AbstractMeasure<EmbeddedReadsBag> similarity, EmbeddingFunction<Sequence, T> embedding,
            String name, FileType bagsFormat, double samplingRatio) {
        super(similarity, name);
        this.embedding = embedding;
        this.bagsFormat = bagsFormat;
        this.samplingRatio = samplingRatio;
    }

    @Override
    public Class<EmbeddedReadsBag> getInputType() {
        return EmbeddedReadsBag.class;
    }

    @Override
    public EmbeddedReadsBag[] loadData(List<Path> files) {
        ReadsBag[] readsBag = IOUtils.newReadBagGroupLoader(files, bagsFormat).loadReadBags();
        EmbeddedReadsBag[] embeddedBags = new EmbeddedReadsBag[readsBag.length];
        for (int i = 0; i < readsBag.length; i++)
            embeddedBags[i] = embeddBag(readsBag[i]);
        return embeddedBags;
    }

    private EmbeddedReadsBag embeddBag(ReadsBag bag) {
        if (samplingRatio > 0.0 && samplingRatio < 1.0)
            bag = bag.sample(samplingRatio);

        return new EmbeddedReadsBag<>(bag, embedding);
    }

}
