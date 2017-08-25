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

import cz.cvut.fel.ida.reads.io.FASTAMultipleFileLoader;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.nio.file.Path;
import java.util.List;

/**
 * The reference. Calculates the given distance.
 *
 * @author Petr Ryšavý
 */
public class ReferenceMethod extends SimilarityMethod<Sequence> {

    /**
     * The reference method.
     * @param distance Distance to be used.
     */
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
