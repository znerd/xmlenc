/*
 * $Id: InvalidXMLException.java,v 1.3 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc;

/**
 * Exception thrown to indicate a specified XML element is not found and hence
 * unexpected.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 *
 * @since xmlenc 0.37
 */
public final class NoSuchElementException extends RuntimeException {

   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>NoSuchElementException</code> with the specified
    * detail message.
    *
    * @param message
    *    the optional detail message, or <code>null</code>.
    */
   public NoSuchElementException(String message) {
      super(message);
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------
}
