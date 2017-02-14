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
