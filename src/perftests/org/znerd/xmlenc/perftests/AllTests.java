// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc.perftests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Combination of all performance tests.
 */
public class AllTests extends TestSuite {

   /**
    * Returns a test suite with all test cases.
    *
    * @return
    *    the test suite, never <code>null</code>.
    */
   public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTestSuite(XMLEncoderTests.class);
      return suite;
   }

   /**
    * Constructs a new <code>AllTests</code> object with the specified name.
    * The name will be passed to the superconstructor.
    *
    * @param name
    *    the name for this test case.
    */
   public AllTests(String name) {
      super(name);
   }
}
