/*
 * Copyright (c) 2016 Petr Rysavy <rysavpe1@fel.cvut.cz>
 *
 * This file is part of the project readsIDA2016, which is available on 
 * <https://github.com/petrrysavy/readsIDA2016/>.
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
 * along with This program.  If not, see <http ://www.gnu.org/licenses/>.
 */
package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.Settings;
import java.nio.file.Path;

/**
 * A representation of bag of reads. This is efficiently a multiset of sequences
 * and a description.
 *
 * @author Petr Ryšavý
 */
public class ReadsBag extends HashMultiset<Sequence> {

    /**
     * Description of the bag of reads.
     */
    private final String description;
    private final Path file;

    /**
     * Constructs a new bag of reads.
     *
     * @param initialCapacity Initial capacity of the multiset.
     * @param description Description, a comment or {@code null}.
     */
    public ReadsBag(int initialCapacity, String description) {
        this(initialCapacity, description, null);
    }

    public ReadsBag(int initialCapacity, String description, Path file) {
        super(initialCapacity);
        this.description = description;
        this.file = file;
    }

    public ReadsBag(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Returns the description.
     *
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    public Path getFile() {
        return file;
    }

    /**
     * Finds the longest sequence.
     *
     * @return The longest sequence or {@code null} if the bag is empty.
     */
    public Sequence longestSequence() {
        int maxLenght = -1;
        Sequence maximal = null;
        for (Sequence s : toSet())
            if (s.length() > maxLenght) {
                maximal = s;
                maxLenght = s.length();
            }
        return maximal;
    }

    /**
     * Constructs a bag of reads from string representation of sequences.
     *
     * @param sequences The sequences to be stored in the new reads bag.
     * @return A read bags that contains all the sequences.
     */
    public static ReadsBag fromString(String... sequences) {
        final ReadsBag bag = new ReadsBag(sequences.length, null);
        for (String sequence : sequences)
            bag.add(Sequence.fromString(sequence));
        return bag;
    }

    public ReadsBag sample(double ratio) {
        final ReadsBag sampled = new ReadsBag((int) (this.size() * ratio) + 10, description + " sampled", file);
        for (Sequence s : this)
            if (Settings.RANDOM.nextDouble() < ratio)
                sampled.add(s);
        return sampled;
    }

    /**
     * {@inheritDoc}
     *
     * @return Description of the reads bag.
     */
    @Override
    public String toString() {
        return description;
    }

}
