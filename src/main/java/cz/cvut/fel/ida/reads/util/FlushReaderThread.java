package cz.cvut.fel.ida.reads.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Ryšavý
 */
public class FlushReaderThread extends Thread {

    private final BufferedReader in;

    public FlushReaderThread(Reader in) {
        this.in = new BufferedReader(in);
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = in.readLine()) != null)
                System.err.println(line);
        } catch (IOException ex) {
            Logger.getLogger(FlushReaderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void interrupt() {
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(FlushReaderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt();
    }

}
