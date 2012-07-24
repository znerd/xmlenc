/*
 * $Id: InvalidXMLException.java,v 1.3 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc;

/**
 * Exception thrown when invalid XML is detected.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 *
 * @since xmlenc 0.37
 */
public final class InvalidXMLException extends RuntimeException {

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
    * Constructs a new <code>InvalidXMLException</code> with the specified
    * detail message.
    *
    * @param message
    *    the optional detail message, or <code>null</code>.
    */
   public InvalidXMLException(String message) {
      super(message);
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------
}
