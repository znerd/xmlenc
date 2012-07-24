// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc.perftests;

import java.io.FileWriter;
import java.io.StringWriter;

import org.znerd.xmlenc.XMLEncoder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Performance tests for class <code>XMLEncoder</code>.
 */
public class XMLEncoderTests extends TestCase {

   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   private static final int ROUNDS = 40000;


   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a test suite with all test cases defined by this class.
    *
    * @return
    *    the test suite, never <code>null</code>.
    */
   public static Test suite() {
      return new TestSuite(XMLEncoderTests.class);
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>XMLEncoderTests</code> test suite with the
    * specified name. The name will be passed to the superconstructor.
    *
    * @param name
    *    the name for this test suite.
    */
   public XMLEncoderTests(String name) {
      super(name);
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Tests the performance of the <code>XMLEncoder</code> in the UTF-8
    * encoding.
    *
    * @throws Exception
    *    in case of an error.
    */
   public void testPerformanceXMLEncoder_UTF8()
   throws Exception {
      doTestXMLEncoder("UTF-8", true);
   }
   public void testOverheadXMLEncoder_UTF8()
   throws Exception {
      doTestXMLEncoder("UTF-8", false);
   }

   /**
    * Tests the performance of the <code>XMLEncoder</code> in the ASCII
    * encoding.
    *
    * @throws Exception
    *    in case of an error.
    */
   public void testPerformanceXMLEncoder_ASCII()
   throws Exception {
      doTestXMLEncoder("ASCII", true);
   }
   public void testOverheadXMLEncoder_ASCII()
   throws Exception {
      doTestXMLEncoder("ASCII", false);
   }


   /**
    * Tests the performance of writing small documents in the specified
    * encoding.
    *
    * @param encoding
    *    the encoding to test with, should not be <code>null</code>.
    *
    * @param real
    *    flag that indicates whether the test should really be run, if
    *    <code>false</code> then only the overhead is tested.
    *
    * @throws IOException
    *    in case of an I/O error.
    */
   private void doTestXMLEncoder(String encoding, boolean real)
   throws Exception {

      XMLEncoder enc = XMLEncoder.getEncoder(encoding);
      FileWriter out = new FileWriter("perftests.out");

      for (int i = 0; i < ROUNDS; i++) {

         System.currentTimeMillis();

         if (! real) {
            continue;
         }

         enc.declaration(out);
         enc.whitespace(out, "\n\n    ");
         enc.text(out, "result", true);
         enc.text(out, "param", true);
         enc.attribute(out, "xmlns:ex", "http://example.org/someurlhere/something/", '"', true);
         enc.attribute(out, "idref", "a5", '\'', false);
         enc.text(out, "param", true);
         enc.text(out, "Here is &amp; comes a long string ans it contains some special chars like < and > and some more like \t (tab) and so on.", false);
         enc.attribute(out, "idref", "a6", '\'', false);
         enc.text(out, "This is a test with some chars & stuff", true);
         enc.text(out, "\n", false);
         enc.whitespace(out, "");
         enc.text(out, "", true);
         enc.text(out, "&amp;", false);
         enc.attribute(out, "id", "52738659283475", '"', true);
         enc.attribute(out, "some", "thing & where", '\'', true);
         enc.attribute(out, "some2", "&amp;", '\'', false);
         enc.attribute(out, "ex:something", "this", '"', false);
         enc.attribute(out, "xmlns:ex", "http://example.org/someurlhere/something/", '"', true);
         enc.text(out, "Here is a longer string & it contains some special chars like < and > and some more like \t (tab) and so on.", true);
      }
   }
}
