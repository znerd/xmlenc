/*
 * $Id: AllTests.java,v 1.3 2007/08/21 21:50:53 znerd Exp $
 */
package org.znerd.xmlenc.perftests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Combination of all performance tests.
 *
 * @version $Revision: 1.3 $ $Date: 2007/08/21 21:50:53 $
 * @author Ernst de Haan (<a href="mailto:ernst@ernstdehaan.com">ernst@ernstdehaan.com</a>)
 */
public class AllTests extends TestSuite {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

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


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

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
