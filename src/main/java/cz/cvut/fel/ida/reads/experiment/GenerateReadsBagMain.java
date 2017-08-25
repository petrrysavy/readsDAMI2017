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
import cz.cvut.fel.ida.reads.io.FASTAReadBagExporter;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.Settings;
import cz.cvut.fel.ida.reads.util.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This main class generates read sets using a simple configuration file.
 *
 * @author Petr Ryšavý
 */
public class GenerateReadsBagMain {

    public static void main(String[] args) throws IOException {
        ExperimentSettings settings = ExperimentSettings.readFromSystemIn();

        Settings.RANDOM.setSeed(settings.seed);

        // and finally generate the reads bags
        if (!Files.exists(settings.targetFolder)) {
            Files.createDirectories(settings.targetFolder);
        }

        List<Path> files = new ArrayList<>(settings.fileList.size());
        for (String file : settings.fileList)
            files.add(settings.referenceFolder.resolve(file));

        Sequence[] seqs = new FASTAMultipleFileLoader(files).loadSequences();
        for (Integer readLength : settings.readLengthValues) {
            for (Double coverage : settings.coverageValues) {
                final Path experimentFolder = settings.getBagsFolder(readLength, coverage);
                Files.createDirectories(experimentFolder);

                ReadsBag[] bags = Utils.generateReadBags(seqs, coverage, readLength, settings.cyclic, settings.shouldReverse, settings.shouldComplement, settings.random);
                for (int i = 0; i < bags.length; i++) {
                    final Path target = experimentFolder.resolve(seqs[i].getFile().getFileName());
                    new FASTAReadBagExporter(target).exportReadsBag(bags[i]);
                }
            }
        }
    }
}
