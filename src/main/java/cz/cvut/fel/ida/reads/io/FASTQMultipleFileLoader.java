package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.ReadsBag;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class FASTQMultipleFileLoader implements ReadBagGroupLoader {

    private final Iterable<Path> paths;
    private final Set<String> selectedNames;

    public FASTQMultipleFileLoader(Iterable<Path> paths, Set<String> selectedNames) {
        this.paths = paths;
        this.selectedNames = selectedNames;
    }

    public FASTQMultipleFileLoader(Iterable<Path> paths) {
        this(paths, null);
    }

    @Override
    public ReadsBag[] loadReadBags() {
        List<ReadsBag> bags = new ArrayList<>();
        for (Path p : paths) {
            if (selectedNames == null || selectedNames.contains(p.getFileName().toString())) {
                bags.add(new FASTQReadBagLoader(p).loadBagOfReads());
            }
        }
        ReadsBag[] arr = new ReadsBag[bags.size()];
        return bags.toArray(arr);
    }
}
