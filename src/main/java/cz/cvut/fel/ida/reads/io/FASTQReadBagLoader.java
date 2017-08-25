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
package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.IOUtils;
import cz.cvut.fel.ida.reads.util.Settings;
import cz.cvut.fel.ida.reads.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Petr Ryšavý
 */
public class FASTQReadBagLoader implements ReadBagLoader {

    private final Path file;

    public FASTQReadBagLoader(Path file) {
        IOUtils.checkCanReadFile(file);
        this.file = file;
    }

    @Override
    public ReadsBag loadBagOfReads() {
        try {
            ReadsBag bag = new ReadsBag(32, IOUtils.stripKnownExtension(file.getFileName().toString()), file);
            BufferedReader br = Files.newBufferedReader(file, Settings.CHARSET);

            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                final String description = line;
                final String sequence = br.readLine();
                final String optional = br.readLine();
                final String quality = br.readLine();

                if (description.isEmpty() || description.charAt(0) != '@'
                        || sequence.isEmpty()
                        || optional.isEmpty() || optional.charAt(0) != '+'
                        || quality.isEmpty()
                        || quality.length() != sequence.length())
                    throw new FileReadException("File does not match FASTQ format");

                bag.add(new Sequence(Utils.checkFASTASequence(sequence.toCharArray()), description.trim().substring(1), file));
            }

            return bag;
        } catch (IOException ex) {
            throw new FileReadException("Cannot read file.");
        }
    }
}
