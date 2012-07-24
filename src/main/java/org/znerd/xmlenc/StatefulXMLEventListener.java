// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Stateful <code>XMLEventListener</code>. This interface adds a single
 * {@link #getState()}.
 *
 * @since xmlenc 0.32
 */
public interface StatefulXMLEventListener
extends XMLEventListener {

   /**
    * Returns the current state of this outputter.
    *
    * @return
    *    the current state, cannot be <code>null</code>.
    */
   XMLEventListenerState getState();
}
