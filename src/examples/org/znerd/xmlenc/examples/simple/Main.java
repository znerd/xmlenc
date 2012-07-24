// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc.examples.simple;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.znerd.xmlenc.XMLOutputter;

/**
 * Simple example program that shows how to use the <code>XMLOutputter</code>.
 */
public class Main extends Object {

   /**
    * Main function.
    *
    * @param args
    *    the arguments to this function.
    */
   public final static void main(String[] args) throws IOException {

      Writer writer = new OutputStreamWriter(System.out, "utf-8");

      XMLOutputter outputter = new XMLOutputter(writer, "iso-8859-1");
      writeXML(outputter);

      System.out.println();
      System.out.println();

      outputter.reset(writer, "utf-8");
      writeXML(outputter);
   }

   private final static void writeXML(XMLOutputter outputter) throws IOException {
      outputter.declaration();
      outputter.whitespace("\n");
      outputter.dtd("html",
                  "-//W3C//DTD XHTML 1.0 Transitional//EN",
                  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
      outputter.whitespace("\n\n");
      outputter.startTag("html");
      outputter.attribute("lang", "en");
      outputter.whitespace("\n");
      outputter.startTag("head");
      outputter.startTag("title");
      outputter.pcdata("Example document");
      outputter.endTag(); // title
      outputter.endTag(); // head

      outputter.startTag("body");
      outputter.attribute("class", "SummaryPage");

      outputter.whitespace("\n");
      outputter.startTag("h1");
      outputter.pcdata("Example document");
      outputter.endTag(); // h1

      outputter.startTag("a");
      outputter.attribute("class", "extlink");
      outputter.attribute("href", "http://www.google.com/");
      outputter.attribute("title", "www.google.com");
      outputter.pcdata("Google");
      outputter.endTag(); // a

      outputter.startTag("p");
      outputter.endTag();

      outputter.comment(" This is a comment. ");
      outputter.whitespace("\n");
      outputter.startTag("table");
      outputter.startTag("caption");
      outputter.pcdata("Here are some special characters:");
      outputter.endTag();

      outputter.startTag("tr");
      outputter.startTag("td");
      outputter.pcdata("Smaller than:");
      outputter.endTag(); // td
      outputter.startTag("td");
      outputter.pcdata("<");
      outputter.endTag(); // td
      outputter.endTag(); // tr

      outputter.startTag("tr");
      outputter.startTag("td");
      outputter.pcdata("Larger than:");
      outputter.endTag(); // td
      outputter.startTag("td");
      outputter.pcdata("<");
      outputter.endTag(); // td
      outputter.endTag(); // tr

      outputter.startTag("tr");
      outputter.startTag("td");
      outputter.pcdata("Ampersand:");
      outputter.endTag(); // td
      outputter.startTag("td");
      outputter.pcdata("&");
      outputter.endTag(); // td
      outputter.endTag(); // tr

      outputter.startTag("tr");
      outputter.startTag("td");
      outputter.pcdata("Char 256:");
      outputter.endTag(); // td
      outputter.startTag("td");
      outputter.pcdata("\u0100");
      outputter.endTag(); // td
      outputter.endTag(); // tr

      outputter.endTag(); // table

      outputter.close(); // body and html

      outputter.getWriter().flush();
   }
}
