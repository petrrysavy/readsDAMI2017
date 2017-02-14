package cz.cvut.fel.ida.reads.util;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author Petr Ryšavý
 */
public final class Settings
{
    public static final Charset CHARSET = Charset.forName("UTF-8");

    public static final Logger LOGGER = Logger.getGlobal();
    
    public static final Random RANDOM = new Random(42);

    private Settings()
    {

    }
}
