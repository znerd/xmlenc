// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * Tests that check that declarations are properly output.
 */
public class DeclarationOutputTest {

    @Test
    public void testUtf8Uppercase() throws IOException {
        doTestDeclaration("UTF-8");
        doTestDeclaration("utf-8");
        doTestDeclaration("ISO-8859-1");
        doTestDeclaration("iso-8859-6");
        doTestDeclaration("ISo-8859-11");
        doTestDeclaration("US-ASCII");
    }

    @Test
    public void testUtf8Lowercase() throws IOException {
        doTestDeclaration("utf-8");
    }

    @Test
    public void testIso88591Uppercase() throws IOException {
        doTestDeclaration("ISO-8859-1");
    }

    @Test
    public void testIso88596Lowercase() throws IOException {
        doTestDeclaration("iso-8859-6");
    }

    @Test
    public void testIso885911MixedCase() throws IOException {
        doTestDeclaration("ISo-8859-11");
    }

    @Test
    public void testUsasciiUppercase() throws IOException {
        doTestDeclaration("US-ASCII");
    }

    /**
     * Check that declarations are properly output for the specified encoding.
     * 
     * @param encoding the encoding to test, should not be <code>null</code>.
     * @throws IOException in case of an I/O error.
     */
    private void doTestDeclaration(String encoding) throws IOException {

        StringWriter stringWriter = new StringWriter();
        XMLOutputter outputter = new XMLOutputter();

        // Reset the outputter with the specified encoding
        outputter.reset(stringWriter, encoding);

        // Write the declaration
        outputter.declaration();

        // Get the result as a String
        String s = stringWriter.toString();

        // The result cannot be null
        assertNotNull(s);

        // There are 2 possible outputs
        String possibility1 = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        String possibility2 = "<?xml version='1.0' encoding='" + encoding + "'?>";

        if (!s.equals(possibility1) && !s.equals(possibility2)) {
            fail("Expected declaration() to output either `" + possibility1 + "' or `" + possibility2 + "'.");
        }
    }

}
