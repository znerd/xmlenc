/*
 * $Id: StatefulXMLEventListener.java,v 1.3 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc;

/**
 * Stateful <code>XMLEventListener</code>. This interface adds a single
 * {@link #getState()}.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
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
