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

import cz.cvut.fel.ida.reads.util.ArrayUtils;
import cz.cvut.fel.ida.reads.util.Utils;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * A simple representation of the sequence.
 *
 * @author Petr Ryšavý
 */
public class Sequence implements CharSequence {

    /**
     * The sequence to be stored.
     */
    private final char[] sequence;
    /**
     * Description.
     */
    private final String description;
    private final Path file;
    private Sequence complement;
    private Sequence reverse;

    /**
     * Creates new sequence.
     *
     * @param sequence The sequence to be stored.
     * @param description Description of the sequence. Can be {@code null}.
     */
    public Sequence(char[] sequence, String description) {
        this(sequence, description, null);
    }

    /**
     * Creates new sequence.
     *
     * @param sequence The sequence to be stored.
     * @param description Description of the sequence. Can be {@code null}.
     * @param file The file that contains this sequence.
     */
    public Sequence(char[] sequence, String description, Path file) {
        this.sequence = sequence;
        this.description = description;
        this.file = file;
    }

    /**
     * Returns the character array representation of this sequence.
     *
     * @return Array that contains this sequence. Note that possible changes
     * will propagate to this class.
     */
    public char[] getSequence() {
        return sequence;
    }

    /**
     * Returns description of this sequence.
     *
     * @return Textual description.
     */
    public String getDescription() {
        return description;
    }

    public Path getFile() {
        return file;
    }

    /**
     * {@inheritDoc} This method returns number of symbols in this sequence.
     */
    @Override
    public int length() {
        return sequence.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char charAt(int index) {
        if (index < 0 || index > sequence.length)
            throw new IndexOutOfBoundsException("Index out of bounds : " + index);
        return sequence[index];
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sequence subSequence(int start, int end) {
        if (start < 0 || start > end || end > sequence.length)
            throw new IndexOutOfBoundsException("Illegal subsequence indices : start=" + start + ", end=" + end);

        char[] arr = new char[end - start];
        System.arraycopy(sequence, start, arr, 0, arr.length);
        return new Sequence(arr, description + " [subsequence " + start + '-' + end + ']');
    }

    public Sequence subSequence2(int start, int length) {
        return subSequence(start, start + length);
    }

    public Sequence cyclicSubsequence(int start, int length) {
        start = start % sequence.length;
        final char[] arr = new char[length];
        int j = 0;
        for (int i = start; i < sequence.length && j < arr.length; i++, j++)
            arr[j] = sequence[i];
        while (j < arr.length)
            for (int i = 0; i < sequence.length && j < arr.length; i++, j++)
                arr[j] = sequence[i];
        return new Sequence(arr, description + " [cyclic subsequence " + start + " of length " + length + ']');
    }

    public Sequence reverse() {
        if (this.reverse == null) {
            this.reverse = reverseCopy();
            this.reverse.reverse = this;

            if (this.complement != null && this.complement.reverse != null) {
                this.complement.reverse.complement = this.reverse;
                this.reverse.complement = this.complement.reverse;
            }
        }

        return this.reverse;
    }

    public Sequence reverseCopy() {
        return new Sequence(ArrayUtils.reversedCopy(sequence), description + ", reversed", file);
    }

    public Sequence complement() {
        if (this.complement == null) {
            this.complement = complementCopy();
            this.complement.complement = this;

            if (this.reverse != null && this.reverse.complement != null) {
                this.reverse.complement.reverse = this.complement;
                this.complement.reverse = this.reverse.complement;
            }
        }

        return this.complement;
    }

    public Sequence complementCopy() {
        return new Sequence(Utils.complementaryCopy(sequence), description + ", complement", file);
    }

    public Sequence reverseComplement() {
        return this.reverse().complement();
    }

    public Sequence reverseComplementCopy() {
        final char[] reversed = ArrayUtils.reversedCopy(sequence);
        Utils.makeComplementary(reversed);
        return new Sequence(reversed, description + ", reversed complement", file);
    }

    /**
     * Constructs a sequence from string representation.
     *
     * @param string String that will form the newly created sequence. It will
     * be used as description too.
     * @return Sequence from the given string.
     */
    public static Sequence fromString(String string) {
        return new Sequence(string.toCharArray(), string);
    }

    /**
     * {@inheritDoc}
     *
     * @return Description or sequence itself, whatever is shorter. If the
     * description is {@code null}, the method returns the sequence.
     */
    @Override
    public String toString() {
        if (description != null && sequence.length >= description.length())
            return description;
        else
            return new String(sequence);
    }

    /**
     * {@inheritDoc} Hash code depends only on the sequence. Description does
     * not matter.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.sequence);
    }

    /**
     * {@inheritDoc} Two sequences are equal if the contain the same symbols.
     * Descriptions do not matter.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Sequence other = (Sequence) obj;
        return Arrays.equals(this.sequence, other.sequence);
    }
}
