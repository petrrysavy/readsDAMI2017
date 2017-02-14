package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.io.FASTAMultipleFileLoader;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.similarity.AbstractMeasure;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Ryšavý
 */
public class CombinedMethod extends ReadsBagMethod {

    public CombinedMethod(AbstractMeasure<ReadsBag> similarity, String name) {
        super(similarity, name);
    }

    @Override
    public Path resolve(Path baseDir, String file) {
        return baseDir.resolve("assembly_" + getName()).resolve(file);
    }

    @Override
    public ReadsBag[] loadData(List<Path> files) {
        try {
            return new FASTAMultipleFileLoader(files).loadReadBags();
        } catch (IllegalArgumentException iae) {
            if (iae.getMessage().startsWith("File must exist")) {
                Logger.getLogger(AssemblyMethod.class.getName()).log(Level.INFO, "Did not find assembly file.", iae);
                return new ReadsBag[0];
            }
            throw iae;
        }
    }

    @Override
    public boolean areDataCorrect(ReadsBag[] data) {
        for (ReadsBag rb : data)
            if (rb == null || rb.isEmpty())
                return false;
        return super.areDataCorrect(data);
    }
}
