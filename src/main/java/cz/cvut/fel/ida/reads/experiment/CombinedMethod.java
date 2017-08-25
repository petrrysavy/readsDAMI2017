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
import cz.cvut.fel.ida.reads.io.FileType;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Method that combines approaches of assembly and distance measurement. As
 * input it takes a set of contigs (represented as {@code ReadsBag} object and
 * calculates a distance among them.
 *
 * @author Petr Ryšavý
 */
public class CombinedMethod extends ReadsBagMethod {

    /**
     * Creates new method that uses assembly for calculating distance.
     * @param similarity Measure in reads bag, which are contigs in this case.
     * @param name Name of the assembler to be used.
     */
    public CombinedMethod(AbstractMeasure<ReadsBag> similarity, String name) {
        super(similarity, name, FileType.FASTA);
    }

    @Override
    public Path resolve(Path baseDir, String file) {
        return baseDir.resolve("assembly_" + getName()).resolve(file);
    }

    @Override
    public ReadsBag[] loadData(List<Path> files) {
        try {
            return new FASTAMultipleFileLoader(files).loadReadBags();
        } catch (IllegalArgumentException iae) {
            if (iae.getMessage().startsWith("File must exist")) {
                Logger.getLogger(AssemblyMethod.class.getName()).log(Level.INFO, "Did not find assembly file.", iae);
                return new ReadsBag[0];
            }
            throw iae;
        }
    }

    /**
     * Returns {@code true} if all sequences were assembled. That means that
     * there is at least one contig for each genome. {@inheritDoc }
     */
    @Override
    public boolean areDataCorrect(ReadsBag[] data) {
        for (ReadsBag rb : data)
            if (rb == null || rb.isEmpty())
                return false;
        return super.areDataCorrect(data);
    }
}
