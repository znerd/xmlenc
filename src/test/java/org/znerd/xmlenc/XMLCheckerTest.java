// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc.tests;

import org.znerd.xmlenc.InvalidXMLException;
import org.znerd.xmlenc.XMLChecker;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests on the <code>XMLChecker</code> class.
 */
public class XMLCheckerTest extends TestCase {

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
      return new TestSuite(XMLCheckerTest.class);
   }


   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>XMLCheckerTest</code> test suite with the
    * specified name. The name will be passed to the superconstructor.
    *
    * @param name
    *    the name for this test suite.
    */
   public XMLCheckerTest(String name) {
      super(name);
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Performs setup for the tests.
    */
   protected void setUp() {
      // empty
   }

   private void reset() {
      // empty
   }

   public void testCheckName() {
      doTestCheckName("",     false);
      doTestCheckName(" ",    false);
      doTestCheckName("a",    true);
      doTestCheckName("html", true);
      doTestCheckName("HTML", true);
      doTestCheckName("HT_L", true);
      doTestCheckName("HT_L", true);
      doTestCheckName("HT L", false);
      doTestCheckName("ht l", false);
      doTestCheckName("hrl ", false);
      doTestCheckName("h:tm", true);
      doTestCheckName("h:t ", false);
      doTestCheckName("&hhh", false);
      doTestCheckName("hhh&", false);
      doTestCheckName(":hhh", true);
      doTestCheckName("_hhh", true);
      doTestCheckName("-hhh", false);
      doTestCheckName("hhh:", true);
      doTestCheckName("hhh-", true);
   }

   public void doTestCheckName(String name, boolean okay) {

      final String production = "Name";

      try {
         XMLChecker.checkName(name);
         if (!okay) {
            fail("The string \"" + name + "\" should be considered invalid for the \"" + production + "\" production.");
         }
      } catch (InvalidXMLException exception) {
         if (okay) {
            fail("The string \"" + name + "\" should be considered valid for the \"" + production + "\" production.");
         }
      }
   }
}
