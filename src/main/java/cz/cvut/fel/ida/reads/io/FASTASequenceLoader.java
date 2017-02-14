package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.util.IOUtils;
import cz.cvut.fel.ida.reads.util.Settings;
import cz.cvut.fel.ida.reads.util.StringUtils;
import cz.cvut.fel.ida.reads.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Petr Ryšavý
 */
public class FASTASequenceLoader implements SequenceLoader {

    private final Path file;

    public FASTASequenceLoader(Path file) {
        IOUtils.checkCanReadFile(file);
        this.file = file;
    }

    @Override
    public Sequence loadSequence() {
        try {
            BufferedReader br = Files.newBufferedReader(file, Settings.CHARSET);

            final String description = br.readLine();

            if (description.isEmpty() || description.charAt(0) != '>')
                throw new FileReadException("File does not match FASTA format");

            String line;
            ArrayList<String> lines = new ArrayList<>(1024);
            while ((line = br.readLine()) != null && !line.isEmpty() && line.charAt(0) != '>')
                lines.add(line);

            if (line != null)
                Settings.LOGGER.log(Level.INFO, "File {0} may contain more than one FASTA sequence. Loading only the first one.", file);

            return new Sequence(Utils.checkFASTASequence(StringUtils.toCharArray(lines)), description.trim().substring(1), file);
        } catch (IOException ex) {
            throw new FileReadException("Cannot read file.");
        }
    }
}
