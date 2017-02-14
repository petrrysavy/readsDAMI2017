package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import cz.cvut.fel.ida.reads.model.Sequence;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FASTAMultipleFileLoader implements ReadBagGroupLoader, SequenceGroupLoader {

    private final Iterable<Path> paths;
    private final Set<String> selectedNames;

    public FASTAMultipleFileLoader(Iterable<Path> paths, Set<String> selectedNames) {
        this.paths = paths;
        this.selectedNames = selectedNames;
    }

    public FASTAMultipleFileLoader(Iterable<Path> paths) {
        this(paths, null);
    }

    @Override
    public Sequence[] loadSequences() {
        List<Sequence> sequences = new ArrayList<>();
        for (Path p : paths) {
            if (selectedNames == null || selectedNames.contains(p.getFileName().toString())) {
                sequences.add(new FASTASequenceLoader(p).loadSequence());
            }
        }
        Sequence[] arr = new Sequence[sequences.size()];
        return sequences.toArray(arr);
    }

    @Override
    public ReadsBag[] loadReadBags() {
        List<ReadsBag> bags = new ArrayList<>();
        for (Path p : paths) {
            if (selectedNames == null || selectedNames.contains(p.getFileName().toString())) {
                bags.add(new FASTAReadBagLoader(p).loadBagOfReads());
            }
        }
        ReadsBag[] arr = new ReadsBag[bags.size()];
        return bags.toArray(arr);
    }
}
