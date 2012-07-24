/*
 * $Id: Library.java,v 1.3 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc;

/**
 * Class that represents this <em>xmlenc</em> library.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 *
 * @since xmlenc 0.30
 */
public final class Library extends Object {

   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns the version of the <em>xmlenc</em> library.
    *
    * @return
    *    the version of this library, for example <code>"%%VERSION%%"</code>,
    *    never <code>null</code>.
    */
   public static final String getVersion() {
      return "%%VERSION%%";
   }


   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>Library</code> object.
    */
   private Library() {
      // empty
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------
}
