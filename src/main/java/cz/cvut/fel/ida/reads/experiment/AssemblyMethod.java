package cz.cvut.fel.ida.reads.experiment;

import cz.cvut.fel.ida.reads.io.FASTAMultipleFileLoader;
import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Ryšavý
 */
public class AssemblyMethod extends SimilarityMethod<Sequence> {

    public AssemblyMethod(DistanceCalculator<Sequence, ? extends Number> innerDistance, String name) {
        super(innerDistance, name);
    }

    @Override
    public Class<Sequence> getInputType() {
        return Sequence.class;
    }

    @Override
    public Path resolve(Path baseDir, String file) {
        return baseDir.resolve("assembly_" + getName()).resolve(file);
    }

    @Override
    public Sequence[] loadData(List<Path> files) {
        try {
            ReadsBag[] bags = new FASTAMultipleFileLoader(files).loadReadBags();
            Sequence[] sequences = new Sequence[bags.length];
            for (int i = 0; i < bags.length; i++)
                sequences[i] = bags[i].longestSequence();
            return sequences;
        } catch (IllegalArgumentException iae) {
            if (iae.getMessage().startsWith("File must exist")) {
                Logger.getLogger(AssemblyMethod.class.getName()).log(Level.INFO, "Did not find assembly file.", iae);
                return new Sequence[0];
            }
            throw iae;
        }
    }

    @Override
    public boolean areDataCorrect(Sequence[] data) {
        for (Sequence s : data)
            if (s == null || s.isEmpty())
                return false;
        return super.areDataCorrect(data);
    }
}
