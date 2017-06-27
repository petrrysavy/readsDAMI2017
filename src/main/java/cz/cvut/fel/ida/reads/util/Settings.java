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
