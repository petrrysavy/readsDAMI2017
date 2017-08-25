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
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Method based on assembly. Uses the longest contig from each sequence file.
 *
 * @author Petr Ryšavý
 */
public class AssemblyMethod extends SimilarityMethod<Sequence> {

    /**
     * Constructs a new instance of the method.
     * @param innerDistance The distance measure used in calculations.
     * @param name Name of the method.
     */
    public AssemblyMethod(DistanceCalculator<Sequence, ? extends Number> innerDistance, String name) {
        super(innerDistance, name);
    }

    @Override
    public Class<Sequence> getInputType() {
        return Sequence.class;
    }

    @Override
    public Path resolve(Path baseDir, String file) {
        return baseDir.resolve("assembly_" + getName()).resolve(file);
    }

    @Override
    public Sequence[] loadData(List<Path> files) {
        try {
            ReadsBag[] bags = new FASTAMultipleFileLoader(files).loadReadBags();
            Sequence[] sequences = new Sequence[bags.length];
            for (int i = 0; i < bags.length; i++)
                sequences[i] = bags[i].longestSequence();
            return sequences;
        } catch (IllegalArgumentException iae) {
            if (iae.getMessage().startsWith("File must exist")) {
                Logger.getLogger(AssemblyMethod.class.getName()).log(Level.INFO, "Did not find assembly file.", iae);
                return new Sequence[0];
            }
            throw iae;
        }
    }

    /**
     * Returns {@code true} if all sequences were assembled. That means that
     * none of the sequences is empty. {@inheritDoc }
     */
    @Override
    public boolean areDataCorrect(Sequence[] data) {
        for (Sequence s : data)
            if (s == null || s.isEmpty())
                return false;
        return super.areDataCorrect(data);
    }
}
