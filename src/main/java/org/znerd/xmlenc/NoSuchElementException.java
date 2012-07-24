// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Exception thrown to indicate a specified XML element is not found and hence unexpected.
 * 
 * @since XMLenc 0.53
 */
public final class NoSuchElementException extends RuntimeException {

    private static final long serialVersionUID = -6322505250831708968L;

    /**
     * Constructs a new <code>NoSuchElementException</code> with the specified detail message.
     * 
     * @param message
     *        the optional detail message, or <code>null</code>.
     */
    public NoSuchElementException(String message) {
        super(message);
    }
}
