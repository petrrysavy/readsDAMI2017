package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
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

/**
 *
 * @author Petr Ryšavý
 */
public class FASTAReadBagLoader implements ReadBagLoader {

    private final Path file;

    public FASTAReadBagLoader(Path file) {
        IOUtils.checkCanReadFile(file);
        this.file = file;
    }

    @Override
    public ReadsBag loadBagOfReads() {
        try {
            ReadsBag bag = new ReadsBag(32, IOUtils.stripKnownExtension(file.getFileName().toString()), file);
            BufferedReader br = Files.newBufferedReader(file, Settings.CHARSET);

            String line = br.readLine();
            while (line != null) {
                final String description = line;

                if (description.isEmpty() || description.charAt(0) != '>')
                    throw new FileReadException("File does not match FASTA format");

                ArrayList<String> lines = new ArrayList<>(1024);
                while ((line = br.readLine()) != null && !line.isEmpty() && line.charAt(0) != '>')
                    lines.add(line);

                bag.add(new Sequence(Utils.checkFASTASequence(StringUtils.toCharArray(lines)), description.trim().substring(1), file));
            }

            return bag;
        } catch (IOException ex) {
            throw new FileReadException("Cannot read file.");
        }
    }
}
