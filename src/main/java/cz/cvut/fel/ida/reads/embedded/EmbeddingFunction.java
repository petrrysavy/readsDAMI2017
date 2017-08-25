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
package cz.cvut.fel.ida.reads.embedded;

/**
 * The function that defines an embedding. Embedding is a projection from one
 * space (for example from the space of sequences) to another space (for example
 * space of numerical vectors). Embeddings are often defined in order to provide
 * approximate results faster based on calculations in the embedded space.
 *
 * @author Petr Ryšavý
 * @param <K> The type of the original space.
 * @param <V> The type of the embedded values.
 */
public interface EmbeddingFunction<K, V> {

    /**
     * Calculates the projection of a key.
     * @param key The original value.
     * @return It's projection to the embedded space.
     */
    public V project(K key);
}
