package cz.cvut.fel.ida.reads.io;

/**
 * @author Petr Ryšavý
 */
public class FileWriteException extends RuntimeException
{
    public FileWriteException()
    {
    }

    public FileWriteException(String msg)
    {
        super(msg);
    }
}
