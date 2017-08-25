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
 * Method based on embedding reads to another space.
 *
 * @author Petr Ryšavý
 * @param <T> The target space of the embedding.
 */
public class EmbeddedMethod<T> extends SimilarityMethod<EmbeddedReadsBag> {

    /** Function that calculates the embedding. */
    private final EmbeddingFunction<Sequence, T> embedding;
    /** File format of the bags. */
    private final FileType bagsFormat;
    /** If this includes sampling, than this is the ratio of sampled values. */
    private final double samplingRatio;

    /**
     * Creates new embedded method.
     * @param similarity This will calculate the distance.
     * @param embedding Function used for embedding calculation.
     * @param name Name of this method.
     * @param bagsFormat Format of reads bag used.
     * @param samplingRatio How many reads should be considered. Use {@code 1.0}
     * as default value.
     */
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

    /**
     * Calcualtes the embedding of the reads bag. Samples if needed.
     * @param bag Bag to be loaded.
     * @return The embedded reads bag.
     */
    private EmbeddedReadsBag embeddBag(ReadsBag bag) {
        if (samplingRatio > 0.0 && samplingRatio < 1.0)
            bag = bag.sample(samplingRatio);

        return new EmbeddedReadsBag<>(bag, embedding);
    }

}
