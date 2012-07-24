// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests on the <code>XMLOutputter</code> class.
 */
public class XMLOutputterTest extends TestCase {

    /**
     * Returns a test suite with all test cases defined by this class.
     * 
     * @return
     *         the test suite, never <code>null</code>.
     */
    public static Test suite() {
        return new TestSuite(XMLOutputterTest.class);
    }

    private final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Constructs a new <code>NumberCentralTest</code> test suite with the
     * specified name. The name will be passed to the superconstructor.
     * 
     * @param name
     *        the name for this test suite.
     */
    public XMLOutputterTest(String name) {
        super(name);
    }

    private StringWriter _stringWriter;
    private XMLOutputter _outputter;

    /**
     * Performs setup for the tests.
     */
    @Override
    protected void setUp() {
        _outputter = new XMLOutputter();
    }

    private void reset() {
        assertNotNull(_outputter);
        _stringWriter = new StringWriter();
        try {
            _outputter.reset(_stringWriter, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException uee) {
            fail("The default encoding, " + DEFAULT_ENCODING + " is not supported.");
        }
    }

    public void testConstructor() {
        assertNull(_outputter.getWriter());
        assertEquals(XMLEventListenerStates.UNINITIALIZED, _outputter.getState());
        assertEquals(0, _outputter.getElementStackSize());
        assertEquals(LineBreak.NONE, _outputter.getLineBreak());
        assertEquals("", _outputter.getIndentation());
    }

    public void testGetWriter() {
        reset();
        assertEquals(_stringWriter, _outputter.getWriter());
    }

    public void testSetState() throws InvalidXMLException, IOException {
        reset();

        try {
            _outputter.setState(null, null);
            fail("XMLOutputter.setState(null, null) should throw an IllegalArgumentException if newState == null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        XMLEventListenerState state = XMLEventListenerStates.ERROR_STATE;
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.AFTER_ROOT_ELEMENT;
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.WITHIN_ELEMENT;
        _outputter.setState(state, new String[] { "html" });
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.START_TAG_OPEN;
        _outputter.setState(state, new String[] { "html" });
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.BEFORE_ROOT_ELEMENT;
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.BEFORE_DTD_DECLARATION;
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());

        state = XMLEventListenerStates.BEFORE_XML_DECLARATION;
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());

        // Set the same state twice
        _outputter.setState(state, null);
        assertEquals(state, _outputter.getState());
    }

    public void testGetState() throws InvalidXMLException, IOException {
        reset();
        assertEquals(XMLEventListenerStates.BEFORE_XML_DECLARATION, _outputter.getState());

        _outputter.declaration();
        assertEquals(XMLEventListenerStates.BEFORE_DTD_DECLARATION, _outputter.getState());

        _outputter.whitespace("\n");
        assertEquals(XMLEventListenerStates.BEFORE_DTD_DECLARATION, _outputter.getState());

        _outputter.dtd("html", null, null);
        assertEquals(XMLEventListenerStates.BEFORE_ROOT_ELEMENT, _outputter.getState());

        _outputter.whitespace("\n");
        assertEquals(XMLEventListenerStates.BEFORE_ROOT_ELEMENT, _outputter.getState());

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.attribute("xml:lang", "en");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.comment(" Some comment here ");
        assertEquals(XMLEventListenerStates.WITHIN_ELEMENT, _outputter.getState());

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.endTag();
        assertEquals(XMLEventListenerStates.WITHIN_ELEMENT, _outputter.getState());

        _outputter.endTag();
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        reset();

        _outputter.dtd("html", null, null);
        assertEquals(XMLEventListenerStates.BEFORE_ROOT_ELEMENT, _outputter.getState());

        _outputter.whitespace("\n\n");
        assertEquals(XMLEventListenerStates.BEFORE_ROOT_ELEMENT, _outputter.getState());

        _outputter.whitespace("\n\n");
        assertEquals(XMLEventListenerStates.BEFORE_ROOT_ELEMENT, _outputter.getState());

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        reset();

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.endTag();
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.whitespace("\n\n");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.pi("app", "instr");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.comment(" Some comment ");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        reset();

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.attribute("lang", "en");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.whitespace(" ");
        assertEquals(XMLEventListenerStates.WITHIN_ELEMENT, _outputter.getState());

        reset();

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.attribute("lang", "en");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.comment(" ");
        assertEquals(XMLEventListenerStates.WITHIN_ELEMENT, _outputter.getState());

        reset();

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.attribute("lang", "en");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.pi("app", "instruction");
        assertEquals(XMLEventListenerStates.WITHIN_ELEMENT, _outputter.getState());

        reset();

        _outputter.startTag("html");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.attribute("lang", "en");
        assertEquals(XMLEventListenerStates.START_TAG_OPEN, _outputter.getState());

        _outputter.endTag();
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.whitespace("   ");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.comment(" no comment ");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());

        _outputter.pi("app", "instruction");
        assertEquals(XMLEventListenerStates.AFTER_ROOT_ELEMENT, _outputter.getState());
    }

    public void testGetElementStackSize() throws InvalidXMLException, IOException {

        reset();

        assertEquals(0, _outputter.getElementStackSize());

        _outputter.startTag("book");
        assertEquals(1, _outputter.getElementStackSize());

        _outputter.startTag("chapter");
        assertEquals(2, _outputter.getElementStackSize());

        _outputter.startTag("chapter");
        assertEquals(3, _outputter.getElementStackSize());

        _outputter.endTag();
        assertEquals(2, _outputter.getElementStackSize());

        _outputter.endTag();
        assertEquals(1, _outputter.getElementStackSize());

        _outputter.endTag();
        assertEquals(0, _outputter.getElementStackSize());

        reset();
        _outputter.startTag("book");
        _outputter.startTag("chapter");
        _outputter.startTag("section");
        _outputter.startTag("para");
        _outputter.startTag("sentence");
        _outputter.startTag("word");
        _outputter.startTag("letter");
        _outputter.close();
        assertEquals(0, _outputter.getElementStackSize());
    }

    public void testGetQuotationMark() {
        reset();

        char c = _outputter.getQuotationMark();
        if (c != '\'' && c != '"') {
            fail("XMLOutputter.getQuotationMark() returned '" + c + "'.");
        }
    }

    public void testSetQuotationMark() {
        reset();

        _outputter.setQuotationMark('\'');
        assertEquals('\'', _outputter.getQuotationMark());
        _outputter.setQuotationMark('\'');
        assertEquals('\'', _outputter.getQuotationMark());

        _outputter.setQuotationMark('"');
        assertEquals('"', _outputter.getQuotationMark());
        _outputter.setQuotationMark('"');
        assertEquals('"', _outputter.getQuotationMark());
    }

    public void testDeclaration() throws InvalidXMLException, IOException {

        reset();

        _outputter.declaration();

        try {
            _outputter.declaration();
            fail("XMLOutputter.declaration() should throw an IllegalStateException if called previously.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.startTag("book");

        try {
            _outputter.declaration();
            fail("XMLOutputter.declaration() should throw an IllegalStateException if called previously.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        reset();

        _outputter.startTag("book");

        try {
            _outputter.declaration();
            fail("XMLOutputter.declaration() should throw an IllegalStateException if an element is already started.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        reset();

        _outputter.whitespace("   ");

        try {
            _outputter.declaration();
            fail("XMLOutputter.declaration() should throw an IllegalStateException if whitespace is already output.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        reset();

        _outputter.dtd("html", null, null);

        try {
            _outputter.declaration();
            fail("XMLOutputter.declaration() should throw an IllegalStateException if the document type declaration is already output.");
        } catch (IllegalStateException ise) { /* as expected */
        }
    }

    public void testDtd() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.dtd(null, null, null);
            fail("XMLOutputter.dtd(String,String,String) should fail if all arguments are null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.dtd(null, "public", "system");
            fail("XMLOutputter.dtd(String,String,String) should fail if first argument is null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.dtd(null, null, "system");
            fail("XMLOutputter.dtd(String,String,String) should fail if first and second arguments are null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.dtd(null, "public", null);
            fail("XMLOutputter.dtd(String,String,String) should fail if first and third arguments are null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.dtd("html", "public", null);
            fail("XMLOutputter.dtd(String,String,String) should fail if a public identifier is specified without a system identifier.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.dtd("html", null, null);

        try {
            _outputter.dtd("html", null, null);
            fail("XMLOutputter.dtd(String,String,String) should fail if called twice.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        reset();

        _outputter.declaration();
        _outputter.dtd("html", null, null);

        try {
            _outputter.dtd("html", null, null);
            fail("XMLOutputter.dtd(String,String,String) should fail if called twice.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        reset();

        _outputter.declaration();
        _outputter.dtd("html", null, "system");

        try {
            _outputter.dtd("html", null, null);
            fail("XMLOutputter.dtd(String,String,String) should fail if called twice.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        reset();

        _outputter.declaration();
        _outputter.dtd("html", "public", "system");

        try {
            _outputter.dtd("html", null, null);
            fail("XMLOutputter.dtd(String,String,String) should fail if called twice.");
        } catch (IllegalStateException iae) { /* as expected */
        }
    }

    public void testStag() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.startTag(null);
            fail("XMLOutputter.startTag(null) should throw an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.startTag("book");
        _outputter.startTag("chapter");
        _outputter.startTag("section");
        _outputter.startTag("section");

        // Only one root element is allowed
        reset();
        _outputter.startTag("book");
        _outputter.endTag();
        try {
            _outputter.startTag("book");
            fail("XMLOutputter.startTag(String) should throw an IllegalStateException if an attempt is made to write a second root element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        // See what happens if we call endTag()
        reset();
        _outputter.startTag("book");
        _outputter.startTag("chapter");
        _outputter.endTag();
        _outputter.startTag("chapter");
        _outputter.endTag(); // chapter
        _outputter.endTag(); // book

        // Call attribute() too
        reset();
        _outputter.startTag("book");
        _outputter.attribute("title", "Java in a Nutshell");

        _outputter.startTag("chapter");
        _outputter.attribute("id", "chapter1");
        _outputter.endTag(); // chapter

        _outputter.startTag("chapter");
        _outputter.attribute("id", "chapter2");

        _outputter.startTag("section");
        _outputter.endTag(); // section
        _outputter.endTag(); // chapter
        _outputter.endTag(); // book
    }

    public void testAttribute() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.attribute("key", "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.declaration();

        try {
            _outputter.attribute("key", "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.dtd("html", null, null);

        try {
            _outputter.attribute("key", "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.startTag("book");

        try {
            _outputter.attribute(null, null);
            fail("XMLOutputter.attribute(String,String) should throw an IllegalArgumentException if key == null && value == null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.attribute("key", null);
            fail("XMLOutputter.attribute(String,String) should throw an IllegalArgumentException if value == null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.attribute(null, "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalArgumentException if key == null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.attribute("key", "value");

        _outputter.endTag();

        try {
            _outputter.attribute("key", "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalStateException if the root element is already closed.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        reset();

        _outputter.startTag("book");
        _outputter.pcdata("Hello");

        try {
            _outputter.attribute("key", "value");
            fail("XMLOutputter.attribute(String,String) should throw an IllegalStateException if PCDATA is written after an element is started.");
        } catch (IllegalStateException ise) { /* as expected */
        }
    }

    public void testEtag() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.endTag();
            fail("XMLOutputter.endTag() should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.startTag("book");
        _outputter.endTag();

        try {
            _outputter.endTag();
            fail("XMLOutputter.endTag() should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }
    }

    public void testPcdata() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.pcdata("Hello");
            fail("XMLOutputter.pcdata(String) should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        _outputter.startTag("book");

        try {
            _outputter.pcdata(null);
            fail("XMLOutputter.pcdata(String) should throw an IllegalArgumentException if text == null.");
        } catch (IllegalArgumentException ise) { /* as expected */
        }

        _outputter.pcdata("Hello");

        _outputter.endTag();

        try {
            _outputter.pcdata("Hello");
            fail("XMLOutputter.pcdata(String) should throw an IllegalStateException if there is no current element.");
        } catch (IllegalStateException ise) { /* as expected */
        }

        // The following characters are invalid according to the XML 1.0
        // Specification:
        //
        // 0x00 - 0x08, 0x0b, 0x0c, 0x0e - 0x1f
        //
        // See:
        // http://www.w3.org/TR/REC-xml
        // http://www.jimprice.com/ascii-0-127.gif

        for (int i = 0x00; i <= 0x1f; i++) {
            reset();
            _outputter.startTag("book");
            if (i <= 0x08 || i == 0x0b || i == 0x0c || i >= 0x0e) {
                try {
                    _outputter.pcdata("" + (char) i);
                    fail("XMLOutputter.pcdata(String) should throw an InvalidXMLException if character 0x" + Integer.toHexString(i) + " is printed.");
                } catch (InvalidXMLException ixe) { /* as expected */
                }
            } else {
                _outputter.pcdata("" + (char) i);
            }
        }
    }

    public void testWhitespace() throws InvalidXMLException, IOException {

        reset();

        _outputter.whitespace("   ");
        _outputter.dtd("html", null, null);
        _outputter.whitespace("   ");
        _outputter.whitespace("   ");
        _outputter.startTag("html");
        _outputter.startTag("head");
        _outputter.startTag("title");
        _outputter.pcdata("An HTML page");
        _outputter.endTag();
        _outputter.close();
        _outputter.whitespace("\n");

        reset();

        _outputter.declaration();
        _outputter.whitespace("   ");
        _outputter.whitespace("	");
        _outputter.dtd("html", null, null);
        _outputter.whitespace("   ");
        _outputter.whitespace("   ");
        _outputter.startTag("html");
        _outputter.startTag("head");
        _outputter.startTag("title");
        _outputter.pcdata("An HTML page");
        _outputter.endTag();
        _outputter.close();
        _outputter.whitespace("\n");

        reset();

        _outputter.declaration();
        try {
            String s = "   Hallo";
            _outputter.whitespace(s);
            fail("XMLOutputter.whitespace(\"" + s + "\") should throw an InvalidXMLException.");
        } catch (InvalidXMLException exception) { /* as expected */
        }
    }

    public void testComment() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.comment(null);
            fail("XMLOutputter.comment(null) should throw an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.comment("");

        reset();

        _outputter.declaration();
        _outputter.comment(" First comment ");
        _outputter.comment(" Second comment ");

        _outputter.startTag("book");
        _outputter.comment(" Third comment ");
        _outputter.endTag();
    }

    public void testPi() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.pi(null, null);
            fail("XMLOutputter.pi(String,String) should throw an IllegalArgumentException if both arguments are null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        try {
            _outputter.pi(null, "instruction");
            fail("XMLOutputter.pi(String,String) should throw an IllegalArgumentException if the first argument is null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.pi(" myapp ", "Do something with this.");
    }

    public void testCdata() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.cdata("Hello");
            fail("XMLOutputter.cdata(String) should throw an IllegalStateException if not within an element.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.declaration();

        try {
            _outputter.cdata("Hello");
            fail("XMLOutputter.cdata(String) should throw an IllegalStateException if not within an element.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.dtd("html", null, null);

        try {
            _outputter.cdata("Hello");
            fail("XMLOutputter.cdata(String) should throw an IllegalStateException if not within an element.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.startTag("html");

        try {
            _outputter.cdata(null);
            fail("XMLOutputter.cdata(String) should throw an IllegalArgumentException if the argument is null.");
        } catch (IllegalArgumentException iae) { /* as expected */
        }

        _outputter.cdata("Hello");

        _outputter.close();

        try {
            _outputter.cdata("Hello");
            fail("XMLOutputter.cdata(String) should throw an IllegalStateException if not within an element.");
        } catch (IllegalStateException iae) { /* as expected */
        }
    }

    public void testEndDocument() throws InvalidXMLException, IOException {

        reset();

        try {
            _outputter.endDocument();
            fail("XMLOutputter.endDocument() should throw an IllegalStateException if no element was started.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.declaration();

        try {
            _outputter.endDocument();
            fail("XMLOutputter.endDocument() should throw an IllegalStateException if no element was started.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.dtd("html", null, null);

        try {
            _outputter.endDocument();
            fail("XMLOutputter.endDocument() should throw an IllegalStateException if no element was started.");
        } catch (IllegalStateException iae) { /* as expected */
        }

        _outputter.startTag("html");

        _outputter.endDocument();

        try {
            _outputter.endDocument();
            fail("XMLOutputter.endDocument() should throw an IllegalStateException if it is called twice.");
        } catch (IllegalStateException iae) { /* as expected */
        }
    }

    public void testLineBreak() throws InvalidXMLException, IOException {

        reset();

        assertEquals(LineBreak.NONE, _outputter.getLineBreak());

        LineBreak[] breaks = new LineBreak[] { LineBreak.NONE, LineBreak.DOS, LineBreak.MACOS, LineBreak.UNIX };

        for (LineBreak break1 : breaks) {
            _outputter.setLineBreak(break1);
            assertEquals(break1, _outputter.getLineBreak());
        }

        // Changing to null should equal NONE and should empty the indentation
        _outputter.setLineBreak(LineBreak.UNIX);
        _outputter.setIndentation("\t");
        assertEquals(LineBreak.UNIX, _outputter.getLineBreak());
        assertEquals("\t", _outputter.getIndentation());
        _outputter.setLineBreak(null);
        assertEquals(LineBreak.NONE, _outputter.getLineBreak());
        assertEquals("", _outputter.getIndentation());

        // Changing to NONE should empty the indentation
        _outputter.setLineBreak(LineBreak.UNIX);
        _outputter.setIndentation("\t");
        assertEquals(LineBreak.UNIX, _outputter.getLineBreak());
        assertEquals("\t", _outputter.getIndentation());
        _outputter.setLineBreak(LineBreak.NONE);
        assertEquals(LineBreak.NONE, _outputter.getLineBreak());
        assertEquals("", _outputter.getIndentation());
    }

    public void testIndentation() throws InvalidXMLException, IOException {

        reset();

        assertEquals(LineBreak.NONE, _outputter.getLineBreak());

        try {
            _outputter.setIndentation(null);
            fail("Expected IllegalStateException when calling setIndentation without having a LineBreak set.");
        } catch (IllegalStateException exception) {
            // as expected
        }

        _outputter.setLineBreak(LineBreak.MACOS);
        _outputter.setIndentation(null);
        assertEquals("", XMLOutputter.DEFAULT_INDENTATION);
        assertEquals("", _outputter.getIndentation());

        _outputter.setIndentation(" ");
        assertEquals(" ", _outputter.getIndentation());

        _outputter.setIndentation("   ");
        assertEquals("   ", _outputter.getIndentation());

        _outputter.setIndentation("\t");
        assertEquals("\t", _outputter.getIndentation());

        _outputter.setIndentation("\t");
        assertEquals("\t", _outputter.getIndentation());
    }

    public void testInvalidIndentation() throws InvalidXMLException, IOException {

        reset();
        _outputter.setLineBreak(LineBreak.UNIX);

        String[] invalid = { " bla", "bla ", "\t\t bla \t", "bla", "b" };
        for (String indent : invalid) {
            try {
                _outputter.setIndentation(indent);
                fail("XMLOutputter.setIndentation should not accept \"" + indent + "\".");
            } catch (IllegalArgumentException exception) {
                // as expected
            }

            assertEquals(XMLOutputter.DEFAULT_INDENTATION, _outputter.getIndentation());
        }
    }
}
