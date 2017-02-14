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
