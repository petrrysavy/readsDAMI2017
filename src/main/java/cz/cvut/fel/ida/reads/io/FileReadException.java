package cz.cvut.fel.ida.reads.io;

/**
 * @author Petr Ryšavý
 */
public class FileReadException extends RuntimeException
{
    public FileReadException()
    {
    }

    public FileReadException(String msg)
    {
        super(msg);
    }
}
