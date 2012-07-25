// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests on the <code>XMLChecker</code> class.
 */
public class XMLCheckerTest {

    @Before
    public void setUp() {
        // empty
    }

    @Test
    public void testCheckName() {
        doTestCheckName("", false);
        doTestCheckName(" ", false);
        doTestCheckName("a", true);
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

    private void doTestCheckName(String name, boolean okay) {
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
