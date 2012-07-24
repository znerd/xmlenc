/**
 * XMLenc: fast low-level stream-based XML output library for Java.
 *
 * <p />This light-weight XML output library fills the gap between a
 * light-weight parser like SAX, and a heavy-weight XML output library, like
 * JDOM.
 *
 * <h3>Example</h3>
 * <p>The example below shows how XMLenc is typically used. When run, the example
 * generates the following XML output:</p>
 *
 * <blockquote><pre>&lt;?xml version="1.0" encoding="ISO-8859-1"?&gt;
 *&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;
 *
 *&lt;html lang="en"&gt;
 *&lt;head&gt;&lt;title&gt;Example document&lt;/title&gt;&lt;/head&gt;&lt;body class="SummaryPage"&gt;
 *&lt;h1&gt;Example document&lt;/h1&gt;&lt;/body&gt;&lt;/html&gt;</pre></blockquote>
 *
 * <p>This XML document can be produced using the specified code:</p>
 *
 * <blockquote><pre>import java.io.IOException;
 *import java.io.OutputStreamWriter;
 *import java.io.Writer;
 *import org.znerd.xmlenc.XMLOutputter;
 *
 *public class Main {

 *    private static final String ENCODING = "ISO-8859-1";
 *
 *    public final static void main(String[] args) throws IOException {
 *
 *        Writer writer = new OutputStreamWriter(System.out, ENCODING);
 *        XMLOutputter outputter = new XMLOutputter(writer, ENCODING);
 *        outputter.declaration();
 *        outputter.whitespace("\n");
 *        outputter.dtd("html",
 *                      "-//W3C//DTD XHTML 1.0 Transitional//EN",
 *                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
 *        outputter.whitespace("\n\n");
 *        outputter.startTag("html");
 *        outputter.attribute("lang", "en");
 *        outputter.whitespace("\n");
 *        outputter.startTag("head");
 *        outputter.startTag("title");
 *        outputter.pcdata("Example document");
 *        outputter.endTag("head"); // closes: title and head
 *
 *        outputter.startTag("body");
 *        outputter.attribute("class", "SummaryPage");
 *
 *        outputter.whitespace("\n");
 *        outputter.startTag("h1");
 *        outputter.pcdata("Example document");
 *
 *        outputter.endDocument(); // closes: h1, body and html
 *        outputter.getWriter().flush();
 *    }
 *}</pre></blockquote>
 *
 * @since XMLenc 0.1
 */
package org.znerd.xmlenc;
