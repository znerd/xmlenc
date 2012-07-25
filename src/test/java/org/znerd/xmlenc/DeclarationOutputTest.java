// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * Tests that check that declarations are properly output.
 */
public class DeclarationOutputTest {

    @Test
    public void testUtf8Uppercase() throws IOException {
        doTestDeclaration("UTF-8", '"');
    }

    @Test
    public void testUtf8Lowercase() throws IOException {
        doTestDeclaration("utf-8", '"');
    }

    @Test
    public void testIso88591() throws IOException {
        doTestDeclaration("ISO-8859-1", '"');
    }

    @Test
    public void testIso88596() throws IOException {
        doTestDeclaration("iso-8859-6", '"');
    }

    @Test
    public void testIso885911() throws IOException {
        doTestDeclaration("ISo-8859-11", '"');
    }

    @Test
    public void testUsascii() throws IOException {
        doTestDeclaration("US-ASCII", '"');
    }

    @Test
    public void testAscii() throws IOException {
        doTestDeclaration("ascii", '"');
    }

    @Test
    public void testSingleQuote() throws IOException {
        doTestDeclaration("ASCII", '\'');
    }

    /**
     * Check that declarations are properly output for the specified encoding.
     * 
     * @param encoding the encoding to test, should not be <code>null</code>.
     * @param quotationMark the quotation mark to use, either <code>'\''</code> or
     *        <code>'&quot;'</code>.
     * @throws IOException in case of an I/O error.
     */
    private void doTestDeclaration(String encoding, char quotationMark) throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLOutputter outputter = new XMLOutputter();
        outputter.reset(stringWriter, encoding);
        outputter.setQuotationMark(quotationMark);
        outputter.declaration();

        String expected = "<?xml version=" + quotationMark + "1.0" + quotationMark + " encoding=" + quotationMark + encoding + quotationMark + "?>";
        String actual = stringWriter.toString();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
