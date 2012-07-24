// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Exception thrown when invalid XML is detected.
 *
 * @since XMLenc 0.37
 */
public final class InvalidXMLException extends RuntimeException {

   /**
    * Constructs a new <code>InvalidXMLException</code> with the specified
    * detail message.
    *
    * @param message
    *    the optional detail message, or <code>null</code>.
    */
   public InvalidXMLException(String message) {
      super(message);
   }
}
