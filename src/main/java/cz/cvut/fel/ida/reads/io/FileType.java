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

/**
 *
 * @author petr
 */
public enum FileType {
    FASTA(new String[]{"fa", "fasta"}),
    FASTQ(new String[]{"fq", "fastq"});
    
    private final String[] extensions;
    
    private FileType(String[] extensions) {
        this.extensions = extensions;
    }
    
    public static FileType byExtension(String extension) {
        extension = extension.trim().toLowerCase();
        switch (extension) {
            case "fa":
            case "fasta":
                return FASTA;
            case "fq":
            case "fastq":
                return FASTQ;
            default:
                throw new IllegalArgumentException("Unknown file extension : " + extension);
        }
    }
}
