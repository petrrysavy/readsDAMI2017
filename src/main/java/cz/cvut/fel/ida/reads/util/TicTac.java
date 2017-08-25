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
package cz.cvut.fel.ida.reads.util;

/**
 * Simple class for measuring time.
 *
 * @author Petr Ryšavý
 */
public final class TicTac {

    /** Last timestamp. */
    private long timestamp;

    /** Creates the new object and starts counting. */
    public TicTac() {
        tic();
    }

    /** Starts counting and updates the timestapt to the current one. */
    public void tic() {
        timestamp = System.currentTimeMillis();
    }

    /**
     * Returns in milliseconds time since the last timestamp.
     * @return The time since the last call of {@code tic()}.
     */
    public long toc() {
        return System.currentTimeMillis() - timestamp;
    }

    /**
     * Returns in milliseconds time since the last timestamp and starts counting
     * again.
     * @return The time since the last call of {@code tic()}.
     */
    public long toctic() {
        final long retval = toc();
        tic();
        return retval;
    }
}
