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
