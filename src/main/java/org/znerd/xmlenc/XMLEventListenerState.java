// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * State for an XML event listener.
 *
 * @since XMLenc 0.30
 */
public final class XMLEventListenerState extends Object {

    /**
     * Creates a new <code>State</code>.
     *
     * @param name the name of the state, should not be <code>null</code>.
     */
    XMLEventListenerState(String name) {
        _name = name;
    }

    /**
     * The name of this state. This field should never be <code>null</code>.
     */
    private final String _name;

    @Override
    public String toString() {
        return _name;
    }
}
