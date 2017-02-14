package cz.cvut.fel.ida.reads.util;

/**
 *
 * @author Petr Ryšavý
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Vytvoří novou instanci <code>ElementNotFoundException</code> bez detailní
     * zprávy.
     */
    public ElementNotFoundException() {
    }

    /**
     * Konstruktor tvořící novou instanci <code>ElementNotFoundException</code>
     * se specifikovanou zprávou.
     *
     * @param msg Detailní zpráva.
     */
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
