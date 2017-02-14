package cz.cvut.fel.ida.reads.util;

import cz.cvut.fel.ida.reads.io.FASTAMultipleFileLoader;
import cz.cvut.fel.ida.reads.io.FASTQMultipleFileLoader;
import cz.cvut.fel.ida.reads.io.FileType;
import static cz.cvut.fel.ida.reads.io.FileType.FASTA;
import static cz.cvut.fel.ida.reads.io.FileType.FASTQ;
import cz.cvut.fel.ida.reads.io.ReadBagGroupLoader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author petr
 */
public class IOUtils {

    private IOUtils() {
    }

    public static void printTo(Iterable<String> list, Writer out) throws IOException {
        for (String st : list) {
            out.write(st);
            out.write('\n');
        }
        out.flush();
    }

    public static void checkCanReadFile(Path file) {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null.");
        if (!Files.exists(file))
            throw new IllegalArgumentException("File must exist : " + file);
        if (!Files.isRegularFile(file))
            throw new IllegalArgumentException("Input file must be a regular file.");
        if (!Files.isReadable(file))
            throw new IllegalArgumentException("File is not readable");
    }

    public static String stripKnownExtension(String filename) {
        if (filename.endsWith(".fa") || filename.endsWith(".fq"))
            return filename.substring(0, filename.length() - 3);
        if (filename.endsWith(".fasta") || filename.endsWith(".fastq"))
            return filename.substring(0, filename.length() - 6);
        return filename;
    }

    public static ReadBagGroupLoader newReadBagGroupLoader(List<Path> files, FileType filetype) {
        switch (filetype) {
            case FASTA:
                return new FASTAMultipleFileLoader(files);
            case FASTQ:
                return new FASTQMultipleFileLoader(files);
            default:
                throw new IllegalArgumentException("Unknown filetype : "+filetype);
        }
    }
}
