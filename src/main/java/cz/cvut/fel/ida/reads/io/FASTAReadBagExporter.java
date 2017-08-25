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
import cz.cvut.fel.ida.reads.util.Settings;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Petr Ryšavý
 */
public class FASTAReadBagExporter implements ReadBagExporter {
    private final Path file;

    public FASTAReadBagExporter(Path file) {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null.");
        if (!Files.isWritable(file) && Files.exists(file))
            throw new IllegalArgumentException("File is not writable");

        this.file = file;
    }

    @Override
    public void exportReadsBag(ReadsBag bag) {
        try {
            BufferedWriter bw = Files.newBufferedWriter(file, Settings.CHARSET);
            int i = 0;
            for (Sequence s : bag) {
                bw.append('>').append(s.getDescription() == null ? "sequence " + i : s.getDescription()).append('\n');
                bw.write(s.getSequence());
                bw.append('\n');
            }
            bw.flush();
            bw.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new FileWriteException("Cannot write file : " + file);
        }
    }
}
