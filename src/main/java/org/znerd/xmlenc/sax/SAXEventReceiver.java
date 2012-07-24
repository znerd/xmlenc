/*
 * $Id: SAXEventReceiver.java,v 1.7 2007/08/21 21:50:52 znerd Exp $
 */
package org.znerd.xmlenc.sax;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.znerd.xmlenc.XMLEventListener;

/**
 * SAX handler that receives SAX events and transforms them to <em>xmlenc</em>
 * events.
 *
 * @version $Revision: 1.7 $ $Date: 2007/08/21 21:50:52 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 *
 * @since xmlenc 0.31
 */
public class SAXEventReceiver
extends Object
implements ContentHandler {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>SAXEventReceiver</code> that sends events to the
    * specified <code>XMLEventListener</code>.
    *
    * @param eventListener
    *    the {@link XMLEventListener} that should be used, cannot be
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>eventListener == null</code>.
    */
   public SAXEventReceiver(XMLEventListener eventListener)
   throws IllegalArgumentException {
      if (eventListener == null) {
         throw new IllegalArgumentException("eventListener == null");
      }

      _eventListener = eventListener;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The <code>XMLOutputter</code> that is used.
    */
   private final XMLEventListener _eventListener;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   public void setDocumentLocator(Locator locator) {
      // XXX: What should we do here?
   }

   public void startDocument() throws SAXException {
      // XXX: Print declaration here?
   }

   public void endDocument() throws SAXException {
      try {
         _eventListener.endDocument();
      } catch (IOException ioException) {
         throw new SAXException(ioException);
      }
   }

   public void startPrefixMapping(String prefix, String uri)
   throws SAXException {
      // XXX: What should we do here?
   }

   public void endPrefixMapping(String prefix)
   throws SAXException {
      // XXX: What should we do here?
   }

   public void startElement(String uri, String localName, String qName, Attributes atts)
   throws SAXException {
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

   public void endElement(String uri, String localName, String qName)
   throws SAXException {
      try {
         _eventListener.endTag();
      } catch (IOException ioException) {
         throw new SAXException(ioException);
      }
   }

   public void characters(char[] ch, int start, int length)
   throws SAXException {
      try {
         _eventListener.pcdata(ch, start, length);
      } catch (IOException ioException) {
         throw new SAXException(ioException);
      }
   }

   public void ignorableWhitespace(char[] ch, int start, int length)
   throws SAXException {
      try {
         _eventListener.whitespace(ch, start, length);
      } catch (IOException ioException) {
         throw new SAXException(ioException);
      }
   }

   public void processingInstruction(String target, String data)
   throws SAXException {
      try {
         _eventListener.pi(target, data);
      } catch (IOException ioException) {
         throw new SAXException(ioException);
      }
   }

   public void skippedEntity(String name)
   throws SAXException {
      // XXX: Should we do anything here?
   }
}
