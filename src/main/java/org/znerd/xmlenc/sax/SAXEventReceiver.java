// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc.sax;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.znerd.xmlenc.XMLEventListener;

/**
 * SAX handler that receives SAX events and transforms them to <em>xmlenc</em> events.
 * 
 * @since XMLenc 0.31
 */
public class SAXEventReceiver extends Object implements ContentHandler {

    /**
     * Constructs a new <code>SAXEventReceiver</code> that sends events to the
     * specified <code>XMLEventListener</code>.
     * 
     * @param eventListener
     *        the {@link XMLEventListener} that should be used, cannot be <code>null</code>.
     * @throws IllegalArgumentException
     *         if <code>eventListener == null</code>.
     */
    public SAXEventReceiver(XMLEventListener eventListener) throws IllegalArgumentException {
        if (eventListener == null) {
            throw new IllegalArgumentException("eventListener == null");
        }

        _eventListener = eventListener;
    }

    /**
     * The <code>XMLOutputter</code> that is used.
     */
    private final XMLEventListener _eventListener;

    @Override
    public void setDocumentLocator(Locator locator) {
        // XXX: What should we do here?
    }

    @Override
    public void startDocument() throws SAXException {
        // XXX: Print declaration here?
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            _eventListener.endDocument();
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // XXX: What should we do here?
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // XXX: What should we do here?
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        try {
            _eventListener.startTag(qName);
            int attributeCount = atts.getLength();
            for (int i = 0; i < attributeCount; i++) {
                _eventListener.attribute(atts.getQName(i), atts.getValue(i));
            }
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            _eventListener.endTag();
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            _eventListener.pcdata(ch, start, length);
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        try {
            _eventListener.whitespace(ch, start, length);
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        try {
            _eventListener.pi(target, data);
        } catch (IOException ioException) {
            throw new SAXException(ioException);
        }
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        // XXX: Should we do anything here?
    }
}
