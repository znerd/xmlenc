/*
 * $Id: XMLEventListenerState.java,v 1.3 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * State for an XML event listener.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 *
 * @since xmlenc 0.30
 */
public final class XMLEventListenerState
extends Object{

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
    * Creates a new <code>State</code>.
    *
    * @param name
    *    the name of the state, should not be <code>null</code>.
    */
   XMLEventListenerState(String name) {
      _name = name;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The name of this state. This field should never be <code>null</code>.
    */
   private final String _name;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   public String toString() {
      return _name;
   }
}
