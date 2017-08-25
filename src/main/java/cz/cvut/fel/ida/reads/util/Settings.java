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

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Static objects of the project with settings.
 *
 * @author Petr Ryšavý
 */
public final class Settings {

    /** The charset used for loading input files. */
    public static final Charset CHARSET = Charset.forName("UTF-8");

    /** Logger. */
    public static final Logger LOGGER = Logger.getGlobal();

    /** Random number generator. */
    public static final Random RANDOM = new Random(42);

    /** Do not let anybody to instantiate the class. */
    private Settings() {
    }
}
