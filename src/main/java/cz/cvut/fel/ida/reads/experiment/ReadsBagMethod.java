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
