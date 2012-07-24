// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Class that represents this <em>xmlenc</em> library.
 *
 * @since xmlenc 0.30
 */
public final class Library extends Object {

   /**
    * Returns the version of the <em>xmlenc</em> library.
    *
    * @return
    *    the version of this library, for example <code>"%%VERSION%%"</code>,
    *    never <code>null</code>.
    */
   public static final String getVersion() {
      return "%%VERSION%%"; // FIXME
   }


   /**
    * Constructs a new <code>Library</code> object.
    */
   private Library() {
      // empty
   }
}
