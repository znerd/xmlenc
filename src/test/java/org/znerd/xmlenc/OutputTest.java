// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Before;

/**
 * Output tests. These tests check that the output from the output methods in
 * class <code>XMLOutputter</code> is as expected.
 */
public class OutputTest {

    private final static String DEFAULT_ENCODING = "UTF-8";

    /**
     * The output destination for the XML outputter.
     */
    private StringWriter _stringWriter;

    /**
     * The XML outputter used in all tests.
     */
    private XMLOutputter _outputter;

    /**
     * Prepares this test set before the tests are executed.
     */
    @Before
    public void setUp() {
        _outputter = new XMLOutputter();
    }

    /**
     * Resets this test set for a new test to be executed.
     */
    private void reset() {
        _stringWriter = new StringWriter();
        assertNotNull(_outputter);
        try {
            _outputter.reset(_stringWriter, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException uee) {
            fail("The default encoding, " + DEFAULT_ENCODING + " is not supported.");
        }
    }

    /**
     * Performs all tests that check that document type definitions are
     * properly output.
     * 
     * @throws IOException in case of an I/O error.
     */
    public void testDtdOutput() throws IOException {

        final String type1 = "html";
        final String type2 = "HTML";
        final String publicID = "-//W3C//DTD XHTML 1.0 Transitional//EN";
        final String systemID = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";

        // Perform null-tests with type 1
        doTestDtd(null, null, null);
        doTestDtd(null, null, systemID);
        doTestDtd(null, publicID, null);
        doTestDtd(null, publicID, systemID);
        doTestDtd(type1, null, null);
        doTestDtd(type1, null, systemID);
        doTestDtd(type1, publicID, null);
        doTestDtd(type1, publicID, systemID);

        // Perform null-tests with type 2
        doTestDtd(null, null, null);
        doTestDtd(null, null, systemID);
        doTestDtd(null, publicID, null);
        doTestDtd(null, publicID, systemID);
        doTestDtd(type2, null, null);
        doTestDtd(type2, null, systemID);
        doTestDtd(type2, publicID, null);
        doTestDtd(type2, publicID, systemID);

        // XXX: The tests below should fail in the future

        // Perform empty string-tests with type 1
        doTestDtd("", "", "");
        doTestDtd("", "", systemID);
        doTestDtd("", publicID, "");
        doTestDtd("", publicID, systemID);
        doTestDtd(type1, "", "");
        doTestDtd(type1, "", systemID);
        doTestDtd(type1, publicID, "");
        doTestDtd(type1, publicID, systemID);

        // Perform empty string-tests with type 2
        doTestDtd("", "", "");
        doTestDtd("", "", systemID);
        doTestDtd("", publicID, "");
        doTestDtd("", publicID, systemID);
        doTestDtd(type2, "", "");
        doTestDtd(type2, "", systemID);
        doTestDtd(type2, publicID, "");
        doTestDtd(type2, publicID, systemID);

        // TODO: Tests with strings starting with whitespace
        // TODO: Tests with strings ending with whitespace
    }

    /**
     * Performs all tests that check that PCDATA sections are properly output.
     * 
     * @throws IOException in case of an I/O error.
     */
    public void testPcdataOutput() throws IOException {

        // Test with escaping enabled
        reset();
        _outputter.setEscaping(true);
        _outputter.startTag("a");
        _outputter.pcdata("Test &amp;");
        String s = _stringWriter.toString();
        assertNotNull(s);
        assertEquals("<a>Test &amp;amp;", s);

        // Test with escaping disabled
        reset();
        _outputter.setEscaping(false);
        _outputter.startTag("a");
        _outputter.pcdata("Test &amp;");
        s = _stringWriter.toString();
        assertNotNull(s);
        assertEquals("<a>Test &amp;", s);

        // Test different characters
        StringWriter sw = new StringWriter();
        XMLOutputter out = new XMLOutputter(sw, "UTF-8");
        out.startTag("a");
        String value = "~!@#$%^*()_+`-=[]\\;:,./?";
        out.pcdata(value);
        String expected = "<a>" + value;
        assertEquals(expected, sw.toString());

        sw = new StringWriter();
        out = new XMLOutputter(sw, "UTF-8");
        out.startTag("a");
        out.pcdata("\"'");
        expected = "<a>\"'";
        assertEquals(expected, sw.toString());

        sw = new StringWriter();
        out = new XMLOutputter(sw, "UTF-8");
        out.startTag("a");
        value = "<>";
        out.pcdata(value);
        expected = "<a>&lt;&gt;";
        assertEquals(expected, sw.toString());
    }

    /**
     * Performs all tests that check that attributes are properly output.
     * 
     * @throws IOException in case of an I/O error.
     */
    public void testAttributeOutput() throws IOException {
        doTestAttribute("UTF-8");
        doTestAttribute("US-ASCII");
    }

    /**
     * Performs all tests that check that indentation settings cause proper
     * output.
     * 
     * @throws IOException in case of an I/O error.
     */
    public void testIndentationOutput() throws IOException {
        doTestIndentation(" ");
        doTestIndentation("  ");
        doTestIndentation("   ");
        doTestIndentation("\t");
        doTestIndentation("\t\t");
        doTestIndentation("\t\t\t");
        doTestIndentation(" \t ");
        doTestIndentation("\t ");
        doTestIndentation("\t \t");
    }

    private void doTestDtd(String name, String publicID, String systemID) throws IOException {

        reset();

        if (name == null) {
            try {
                _outputter.dtd(name, publicID, systemID);
                fail("Expected dtd(String,String,String) to fail if name == null.");
            } catch (IllegalArgumentException exception) {
                // as expected
            }
            return;
        }

        if (publicID != null && systemID == null) {
            try {
                _outputter.dtd(name, publicID, systemID);
                fail("Expected dtd(String,String,String) to fail if publicID != null && systemID == null.");
            } catch (IllegalArgumentException exception) {
                // as expected
            }
            return;
        }

        boolean invalidXML = name != null && XMLChecker.isName(name) == false || publicID != null && XMLChecker.isPubidLiteral('"' + publicID + '"') == false || systemID != null && XMLChecker.isSystemLiteral('"' + systemID + '"') == false;

        if (invalidXML) {
            try {
                _outputter.dtd(name, publicID, systemID);
                // TODO: Improve message for when components are null
                fail("Expected dtd(String,String,String) to fail with name=\"" + name + "\"; publicID=\"" + publicID + "\"; systemID=\"" + systemID + "\".");
            } catch (InvalidXMLException exception) {
                // as expected
            }
            return;
        }

        _outputter.dtd(name, publicID, systemID);
        String s = _stringWriter.toString();
        assertNotNull(s);

        String expected = "<!DOCTYPE " + name;
        if (systemID != null) {
            if (publicID != null) {
                expected += " PUBLIC \"" + publicID + "\" \"";
            } else {
                expected += " SYSTEM \"";
            }
            expected += systemID + '"';
        }

        expected += ">";
        assertEquals(expected, s);
    }

    private void doTestAttribute(String encoding) throws IOException {
        doTestAttribute(encoding, '\'');
        doTestAttribute(encoding, '"');
    }

    private void doTestAttribute(String encoding, char quotationMark) throws IOException {
        final String elem = "a";

        final String[] attrs = { "b", "c", "aaaa", "bbbbb", "a:b", "~!@#$%^*()_+`-=[]\\;:,./?", };

        for (int i = 0; i < attrs.length / 2; i++) {
            StringWriter sw = new StringWriter();
            XMLOutputter out = new XMLOutputter(sw, encoding);
            out.setQuotationMark(quotationMark);
            out.startTag(elem);
            String name = attrs[i * 2];
            String value = attrs[i * 2 + 1];
            out.attribute(name, value);
            String expected = "<" + elem + " " + name + "=" + quotationMark + value + quotationMark;
            assertEquals(expected, sw.toString());
        }

        StringWriter sw = new StringWriter();
        XMLOutputter out = new XMLOutputter(sw, encoding);
        out.setQuotationMark(quotationMark);
        out.startTag(elem);
        String name = "a";
        String value = "&";
        out.attribute(name, value);
        String expected = "<a a=" + quotationMark + "&amp;" + quotationMark;
        assertEquals(expected, sw.toString());

        sw = new StringWriter();
        out = new XMLOutputter(sw, encoding);
        out.setQuotationMark(quotationMark);
        out.startTag(elem);
        value = "" + quotationMark;
        String encodedQM = quotationMark == '\'' ? "&apos;" : "&quot;";
        out.attribute(name, value);
        expected = "<a a=" + quotationMark + encodedQM + quotationMark;
        assertEquals(expected, sw.toString());

        sw = new StringWriter();
        out = new XMLOutputter(sw, encoding);
        out.setQuotationMark(quotationMark);
        out.startTag(elem);
        value = "<>";
        out.attribute(name, value);
        expected = "<a a=" + quotationMark + "&lt;&gt;" + quotationMark;
        assertEquals(expected, sw.toString());
    }

    private void doTestIndentation(String indent) throws IOException {

        String[] encodings = new String[] { "UTF-8", "US-ASCII" };

        String parent = "father";
        String child = "son";
        String baby = "grandson";

        LineBreak[] breaks = new LineBreak[] { LineBreak.DOS, LineBreak.MACOS, LineBreak.UNIX };

        for (String enc : encodings) {
            for (LineBreak b : breaks) {
                String br = b.toString();
                String expected, actual;

                // Test with indentation
                StringWriter sw = new StringWriter();
                XMLOutputter out = new XMLOutputter(sw, enc);
                out.setLineBreak(b);
                out.setIndentation(indent);
                out.startTag(parent);
                out.startTag(child);
                out.startTag(baby);
                out.endDocument();

                expected = "<" + parent + '>' + br + indent + '<' + child + '>' + br + indent + indent + '<' + baby + "/>" + br + indent + "</" + child + '>' + br + "</" + parent + '>';
                actual = sw.toString();
                if (!actual.equals(expected)) {
                    System.out.println("Expected:\n" + expected);
                    System.out.println("\nActual:\n" + actual);
                }
                assertEquals(expected, actual);

                // Test the same thing, but with a declaration prepended
                sw = new StringWriter();
                out = new XMLOutputter(sw, enc);
                out.setLineBreak(b);
                out.setIndentation(indent);
                out.declaration();
                out.startTag(parent);
                out.startTag(child);
                out.startTag(baby);
                out.endDocument();

                expected = "<?xml version=\"1.0\" encoding=\"" + enc + "\"?>" + br + '<' + parent + '>' + br + indent + '<' + child + '>' + br + indent + indent + '<' + baby + "/>" + br + indent + "</" + child + '>' + br + "</" + parent + '>';
                actual = sw.toString();
                if (!actual.equals(expected)) {
                    System.out.println("Expected:\n" + expected);
                    System.out.println("\nActual:\n" + actual);
                }
                assertEquals(expected, actual);

                // Test the same thing, but with a DTD prepended instead
                sw = new StringWriter();
                out = new XMLOutputter(sw, enc);
                out.setLineBreak(b);
                out.setIndentation(indent);
                out.dtd("a", null, "http://c/");
                out.startTag(parent);
                out.startTag(child);
                out.startTag(baby);
                out.endDocument();

                expected = "<!DOCTYPE a SYSTEM \"http://c/\">" + br + '<' + parent + '>' + br + indent + '<' + child + '>' + br + indent + indent + '<' + baby + "/>" + br + indent + "</" + child + '>' + br + "</" + parent + '>';
                actual = sw.toString();
                if (!actual.equals(expected)) {
                    System.out.println("Expected:\n" + expected);
                    System.out.println("\nActual:\n" + actual);
                }
                assertEquals(expected, actual);

                // Test the same thing, but with both declaration and DTD
                sw = new StringWriter();
                out = new XMLOutputter(sw, enc);
                out.setLineBreak(b);
                out.setIndentation(indent);
                out.declaration();
                out.dtd("a", null, "http://c/");
                out.startTag(parent);
                out.startTag(child);
                out.startTag(baby);
                out.endDocument();

                expected = "<?xml version=\"1.0\" encoding=\"" + enc + "\"?>" + br + "<!DOCTYPE a SYSTEM \"http://c/\">" + br + '<' + parent + '>' + br + indent + '<' + child + '>' + br + indent + indent + '<' + baby + "/>" + br + indent + "</" + child + '>' + br + "</" + parent + '>';
                actual = sw.toString();
                if (!actual.equals(expected)) {
                    System.out.println("Expected:\n" + expected);
                    System.out.println("\nActual:\n" + actual);
                }
                assertEquals(expected, actual);
            }
        }
    }
}
