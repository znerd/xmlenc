// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Class that represents this <em>XMLenc</em> library.
 *
 * @since XMLenc 0.30
 */
public final class Library extends Object {

    private static final String VERSION;

    private Library() {
        // empty
    }

    static {
        VERSION = Library.class.getPackage().getImplementationVersion();
    }

    /**
     * Returns the version of the <em>xmlenc</em> library.
     *
     * @return the version of this library, for example <code>"1.0"</code>, never <code>null</code>.
     */
    public static final String getVersion() {
        return VERSION;
    }
}
