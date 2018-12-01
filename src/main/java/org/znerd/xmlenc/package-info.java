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
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;
 *
 * &lt;html lang="en"&gt;
 * &lt;head&gt;&lt;title&gt;Example document&lt;/title&gt;&lt;/head&gt;&lt;body class="SummaryPage"&gt;
 * &lt;h1&gt;Example document&lt;/h1&gt;&lt;/body&gt;&lt;/html&gt;</pre></blockquote>
 *
 * <p>This XML document can be produced using the specified code:</p>
 *
 * <blockquote><pre>import java.io.IOException;
 * import java.io.OutputStreamWriter;
 * import java.io.Writer;
 * import org.znerd.xmlenc.XMLOutputter;
 *
 * public class Main {
 *
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
 * }</pre></blockquote>
 *
 * <h3>Implementation Notes</h3>
 *
 * Different Unicode characters need to be treated differently. Within attribute values, the following ranges are identified:
 *
 * <table>
 * <thead>
 * <tr><th>ID</th><th>Decimal range</th><th>Categorization</th><th>Escaping</th>
 * </thead>
 * <tbody>
 * <tr><td>A</td><td>0-8</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>B</td><td>9-10</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>C</td><td>11-12</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>D</td><td>13</td><td>Normal character</td><td>Never needed</td></tr>
 * <tr><td>E</td><td>14-31</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>F</td><td>32-33</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>G</td><td>34</td><td>Quote (")</td><td>If quotation mark</td></tr>
 * <tr><td>H</td><td>35-37</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>I</td><td>38</td><td>Ampersand (&amp;)</td><td>If escapeAmpersands=true</td></tr>
 * <tr><td>J</td><td>39</td><td>Apostrophe (')</td><td>If quotation mark</td></tr>
 * <tr><td>K</td><td>40-59</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>L</td><td>60</td><td>Less than (&lt;)</td><td>Always</td></tr>
 * <tr><td>M</td><td>61</td><td>Normal character</td><td>Never needed</td></tr>
 * <tr><td>N</td><td>62</td><td>Greater than (>)</td><td>Always</td></tr>
 * <tr><td>O</td><td>63-127</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>P</td><td>128+</td><td>Normal characters</td><td>If encoding is ASCII</td></tr>
 * </tbody>
 * </table>
 *
 * <p>Outside attribute values, these ranges changes slightly:
 *
 * <table>
 * <thead>
 * <tr><th>ID</th><th>Decimal range</th><th>Categorization</th><th>Escaping</th>
 * </thead>
 * <tbody>
 * <tr><td>A</td><td>0-8</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>B</td><td>9-10</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>C</td><td>11-12</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>D</td><td>13</td><td>Normal character</td><td>Never needed</td></tr>
 * <tr><td>E</td><td>14-31</td><td>Control characters</td><td>N/A - Not allowed in XML 1.0</td></tr>
 * <tr><td>FGH</td><td>32-37</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>I</td><td>38</td><td>Ampersand (&amp;)</td><td>If escapeAmpersands=true</td></tr>
 * <tr><td>JK</td><td>39-59</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>L</td><td>60</td><td>Less than (&lt;)</td><td>Always</td></tr>
 * <tr><td>M</td><td>61</td><td>Normal character</td><td>Never needed</td></tr>
 * <tr><td>N</td><td>62</td><td>Greater than (>)</td><td>Always</td></tr>
 * <tr><td>O</td><td>63-127</td><td>Normal characters</td><td>Never needed</td></tr>
 * <tr><td>P</td><td>128+</td><td>Normal characters</td><td>If encoding is ASCII</td></tr>
 * </tbody>
 * </table>
 *
 * <p>The following characters are expected to be encountered the most often:
 * <ol>
 * <li>32 Space (part of range F)
 * <li>10 Linefeed (part of range B)
 * <li>13 Carriage return (part of range D)
 * <li>48-57 Digits 0-9 (part of range K)
 * <li>65-90 Uppercase letters A-Z (part of range O)
 * <li>97-122 Lowercase letters a-z (part of range O)
 * </ol>
 *
 * <p>After that, the following characters are expected to be encountered the most often:
 *
 * <ol>
 * <li>9 Tab (part of range B)
 * <li>33 Exclamation mark (part of range F)
 * <li>34 Quote (range G)
 * <li>35-37 Hash, dollar, percent (range H)
 * <li>38 Ampersand (range I)
 * <li>39 Apostrophe (range J)
 * <li>40-47 Punctuation, etc. (part of range K)
 * <li>58-59 Punctuation, etc. (part of range K)
 * <li>60 Less-than (range L)
 * <li>61 Equals (range M)
 * <li>62 Greater-than (range N)
 * <li>63-64 Question, at-sign (part of range O)
 * <li>91-96 Punctuation, etc. (part of range O)
 * <li>123-127 Punctuation (part of range O)
 * </ol>
 *
 * <p>And the following characters are expected to be encountered the least:
 *
 * <ol>
 * <li>128+ High characters (range P)
 * </ol>
 *
 * <p>For more technical information about XML and encoding, see:
 * <ul>
 * <li><a href="http://www.w3.org/TR/REC-xml">XML 1.0 Recommendation</a>
 * <li><a href="http://www.jimprice.com/ascii-0-127.gif">ASCII chart</a>
 * </ul>
 *
 * @since XMLenc 0.1
 */
package org.znerd.xmlenc;

