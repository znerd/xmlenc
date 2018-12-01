// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * All <code>XMLEventListenerState</code>s.
 *
 * @since XMLenc 0.31
 */
public interface XMLEventListenerStates {

    /**
     * Uninitialized state. In this state no events notifications are valid.
     */
    static final XMLEventListenerState UNINITIALIZED = new XMLEventListenerState("UNINITIALIZED");

    /**
     * The initial initialized state. No XML declaration has been written, no
     * DTD declaration, nothing at all.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>XML declaration ({@link XMLEventListener#declaration()})</li>
     * <li>DTD declaration ({@link XMLEventListener#dtd(String, String, String)})</li>
     * <li>white space ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * <li>start tag ({@link XMLEventListener#startTag(String)})</li>
     * </ul>
     */
    static final XMLEventListenerState BEFORE_XML_DECLARATION = new XMLEventListenerState("BEFORE_XML_DECLARATION");

    /**
     * State after XML declaration but before the DTD declaration, if any. This
     * state is reached right after a {@link XMLEventListener#declaration()} event notification.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>DTD declaration ({@link XMLEventListener#dtd(String, String, String)})</li>
     * <li>white space ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * <li>start tag ({@link XMLEventListener#startTag(String)})</li>
     * </ul>
     */
    static final XMLEventListenerState BEFORE_DTD_DECLARATION = new XMLEventListenerState("BEFORE_DTD_DECLARATION");

    /**
     * State after DTD declaration but before the root element. This
     * state is reached right after a {@link XMLEventListener#dtd(String, String, String)} event
     * notification.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>white space ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * <li>start tag ({@link XMLEventListener#startTag(String)})</li>
     * </ul>
     */
    static final XMLEventListenerState BEFORE_ROOT_ELEMENT = new XMLEventListenerState("BEFORE_ROOT_ELEMENT");

    /**
     * State in which a start tag is still open. This state is entered after
     * {@link XMLEventListener#startTag(String)} is called.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>white space ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * <li>attribute ({@link XMLEventListener#attribute(String, String)})</li>
     * <li>another start tag ({@link XMLEventListener#startTag(String)})</li>
     * <li>end tag ({@link XMLEventListener#endTag()})</li>
     * </ul>
     */
    static final XMLEventListenerState START_TAG_OPEN = new XMLEventListenerState("START_TAG_OPEN");

    /**
     * State within an element, start tag is closed.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>white space ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * <li>start tag ({@link XMLEventListener#startTag(String)})</li>
     * <li>end tag ({@link XMLEventListener#endTag()})</li>
     * </ul>
     */
    static final XMLEventListenerState WITHIN_ELEMENT = new XMLEventListenerState("WITHIN_ELEMENT");

    /**
     * State after the root element.
     * <p />
     * In this state the following event notifications are valid:
     * <ul>
     * <li>whitespace ({@link XMLEventListener#whitespace(String)})</li>
     * <li>comment ({@link XMLEventListener#comment(String)})</li>
     * <li>processing instruction ({@link XMLEventListener#pi(String, String)})</li>
     * </ul>
     */
    static final XMLEventListenerState AFTER_ROOT_ELEMENT = new XMLEventListenerState("AFTER_ROOT_ELEMENT");

    /**
     * State entered when the document is ended. No more event notifications are
     * valid.
     */
    static final XMLEventListenerState DOCUMENT_ENDED = new XMLEventListenerState("DOCUMENT_ENDED");

    /**
     * State reached when there was an error while writing output. No more
     * event notifications are valid.
     */
    static final XMLEventListenerState ERROR_STATE = new XMLEventListenerState("ERROR_STATE");
}
