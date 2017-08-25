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
 * Utility class for manipulating input and output.
 *
 * @author Petr Ryšavý
 */
public class IOUtils {

    /** Do not let anybody to instantiate the class. */
    private IOUtils() {
    }

    /**
     * Prints list of lines to a writer.
     * @param list List of strings to print.
     * @param out Target writer.
     * @throws IOException When writing to writer fails.
     */
    public static void printTo(Iterable<String> list, Writer out) throws IOException {
        for (String st : list) {
            out.write(st);
            out.write('\n');
        }
        out.flush();
    }

    /**
     * Checks whether the file can be read. File must not be null, must exist,
     * it must be a regular file and must be readable.
     * @param file File to check.
     */
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

    /**
     * Removes extension that is known.
     * @param filename Name of file, possible with extension.
     * @return {@code filename}, however without extension.
     */
    public static String stripKnownExtension(String filename) {
        if (filename.endsWith(".fa") || filename.endsWith(".fq"))
            return filename.substring(0, filename.length() - 3);
        if (filename.endsWith(".fasta") || filename.endsWith(".fastq"))
            return filename.substring(0, filename.length() - 6);
        return filename;
    }

    /**
     * Creates new loader for group of reads based on known file type.
     * @param files List of files.
     * @param filetype Type of reads in the files FASTA or FASTQ.
     * @return Loader for the specified files.
     */
    public static ReadBagGroupLoader newReadBagGroupLoader(List<Path> files, FileType filetype) {
        switch (filetype) {
            case FASTA:
                return new FASTAMultipleFileLoader(files);
            case FASTQ:
                return new FASTQMultipleFileLoader(files);
            default:
                throw new IllegalArgumentException("Unknown filetype : " + filetype);
        }
    }
}
