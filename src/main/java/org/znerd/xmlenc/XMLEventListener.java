// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Interface for XML event listeners.
 *
 * <h3>State transitions</h3>
 *
 * XML event sources must obey a state model when calling
 * <code>XMLEventListener</code>s. However, an <code>XMLEventListener</code>
 * is not required to check that this state model is actually respected. If it
 * does, then it will throw an {@link IllegalStateException} if the state
 * model is violated.
 *
 * <p>Stateful <code>XMLEventListener</code> implementations should
 * implement the {@link StatefulXMLEventListener} interface instead of
 * implementing <code>XMLEventListener</code> directly.
 *
 * <p>Initially the state of an uninitialized <code>XMLEventListener</code> is
 * {@link #UNINITIALIZED UNINITIALIZED}.
 *
 * <p>The following table defines how the state changes when a certain
 * method is called in a certain state. Horizontally are the current states,
 * vertically the notification methods. The cells self contain the
 * new state.
 *
 * <p><table class="states">
 *    <tr>
 *       <th></th>
 *       <th><acronym title="BEFORE_XML_DECLARATION">S0</acronym></th>
 *       <th><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></th>
 *       <th><acronym title="BEFORE_ROOT_ELEMENT">S2</acronym></th>
 *       <th><acronym title="START_TAG_OPEN">S3</acronym></th>
 *       <th><acronym title="WITHIN_ELEMENT">S4</acronym></th>
 *       <th><acronym title="AFTER_ROOT_ELEMENT">S5</acronym></th>
 *       <th><acronym title="DOCUMENT_ENDED">S6</acronym></th>
 *    </tr>
 *    <tr>
 *       <th>{@link #declaration()}</th>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #dtd(String,String,String)}</th>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S2</acronym></td>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S2</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #startTag(String)}</th>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #attribute(String,String)}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="START_TAG_OPEN">S3</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #endTag()}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym>/<acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym>/<acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #pcdata(String)}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #pcdata(char[],int,int)}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #cdata(String)}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #whitespace(String)}</th>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #whitespace(char[],int,int)}</th>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #comment(String)}</th>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #pi(String,String)}</th>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_DTD_DECLARATION">S1</acronym></td>
 *       <td><acronym title="BEFORE_ROOT_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="WITHIN_ELEMENT">S4</acronym></td>
 *       <td><acronym title="AFTER_ROOT_ELEMENT">S5</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 *    <tr>
 *       <th>{@link #endDocument()}</th>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *       <td><acronym title="DOCUMENT_ENDED">S6</acronym></td>
 *       <td><acronym title="DOCUMENT_ENDED">S6</acronym></td>
 *       <td><acronym title="DOCUMENT_ENDED">S6</acronym></td>
 *       <td class="err"><acronym title="IllegalStateException">ISE</acronym></td>
 *    </tr>
 * </table>
 *
 * <p>List of states as used in the table:
 *
 * <ul>
 *    <li>S0: {@link #BEFORE_XML_DECLARATION BEFORE_XML_DECLARATION}</li>
 *    <li>S1: {@link #BEFORE_DTD_DECLARATION BEFORE_DTD_DECLARATION}</li>
 *    <li>S2: {@link #BEFORE_ROOT_ELEMENT    BEFORE_ROOT_ELEMENT}</li>
 *    <li>S3: {@link #START_TAG_OPEN         START_TAG_OPEN}</li>
 *    <li>S4: {@link #WITHIN_ELEMENT         WITHIN_ELEMENT}</li>
 *    <li>S5: {@link #AFTER_ROOT_ELEMENT     AFTER_ROOT_ELEMENT}</li>
 *    <li>S6: {@link #DOCUMENT_ENDED         DOCUMENT_ENDED}</li>
 * </ul>
 *
 * @since XMLenc 0.30
 */
public interface XMLEventListener
extends XMLEventListenerStates {

   /**
    * Resets this XML event listener. The state will be set to
    * {@link #UNINITIALIZED UNINITIALIZED}.
    */
   void reset();

   /**
    * Returns the current state of this outputter.
    *
    * @return
    *    the current state, cannot be <code>null</code>.
    *
    * @throws UnsupportedOperationException
    *    if this is not a stateful XML event listener.
    */
   XMLEventListenerState getState()
   throws UnsupportedOperationException;

   /**
    * Sets the state of this XML event listener. Normally, it is not necessary
    * to call this method and it should be used with great care.
    *
    * <p>Calling this method with {@link #UNINITIALIZED UNINITIALIZED} as
    * the state is equivalent to calling {@link #reset()}.
    *
    * @param newState
    *    the new state, not <code>null</code>.
    *
    * @param newElementStack
    *    the new element stack, if <code>newState == {@link #START_TAG_OPEN START_TAG_OPEN}
    *    || newState == {@link #WITHIN_ELEMENT WITHIN_ELEMENT}</code> then it should be
    *    non-<code>null</code> and containing no <code>null</code> elements,
    *    otherwise it must be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>newState == null
    *          || (newState == {@link #START_TAG_OPEN START_TAG_OPEN} &amp;&amp; newElementStack == null)
    *          || (newState == {@link #WITHIN_ELEMENT WITHIN_ELEMENT} &amp;&amp; newElementStack == null)
    *          || (newState != {@link #START_TAG_OPEN START_TAG_OPEN} &amp;&amp; newState != {@link #WITHIN_ELEMENT WITHIN_ELEMENT} &amp;&amp; newElementStack != null)
    *          || newElementStack[<i>n</i>] == null</code> (where <code>0 &lt;= <i>n</i> &lt; newElementStack.length</code>).
    */
   void setState(XMLEventListenerState newState, String[] newElementStack)
   throws IllegalArgumentException;

   /**
    * Notification of an XML declaration. No encoding is explicitly specified.
    *
    * @throws IllegalStateException
    *    if <code>getState() != BEFORE_XML_DECLARATION</code>.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE ERROR_STATE}.
    */
   void declaration() throws IllegalStateException, IOException;

   /**
    * Notification of a document type declaration.
    *
    * <p>An external subset can be specified using either a
    * <em>system identifier</em> (alone), or using both a
    * <em>public identifier</em> and a <em>system identifier</em>. It can
    * never be specified using a <em>public identifier</em> alone.
    *
    * <p>For example, for XHTML 1.0 the public identifier is:
    *
    * <blockquote><code>-//W3C//DTD XHTML 1.0 Transitional//EN</code></blockquote>
    *
    * <p>while the system identifier is:
    *
    * <blockquote><code>http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd</code></blockquote>
    *
    * <p>The output is typically similar to this:
    *
    * <blockquote><code>&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</code></blockquote>
    *
    * or alternatively, if only the <em>system identifier</em> is specified:
    *
    * <blockquote><code>&lt;!DOCTYPE html SYSTEM "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</code></blockquote>
    *
    * @param name
    *    the name of the document type, not <code>null</code>.
    *
    * @param publicID
    *    the public identifier, can be <code>null</code>.
    *
    * @param systemID
    *    the system identifier, can be <code>null</code>, but otherwise
    *    it should be a properly formatted URL, see
    *    <a href="http://www.w3.org/TR/2000/REC-xml-20001006#sec-external-ent">section 4.2.2 External Entities</a>
    *    in the XML 1.0 Specification.
    *
    * @throws IllegalStateException
    *    if <code>getState() != {@link #BEFORE_XML_DECLARATION BEFORE_XML_DECLARATION}&amp;&amp;
    *             getState() != {@link #BEFORE_DTD_DECLARATION BEFORE_DTD_DECLARATION}</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null ||
    *          (publicID != null &amp;&amp; systemID == null)</code>.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void dtd(String name, String publicID, String systemID)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of an element start tag.
    *
    * @param type
    *    the type of the tag to start, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() != {@link #BEFORE_XML_DECLARATION BEFORE_XML_DECLARATION} &amp;&amp;
    *             getState() != {@link #BEFORE_DTD_DECLARATION BEFORE_DTD_DECLARATION} &amp;&amp;
    *             getState() != {@link #BEFORE_ROOT_ELEMENT    BEFORE_ROOT_ELEMENT} &amp;&amp;
    *             getState() != {@link #START_TAG_OPEN         START_TAG_OPEN} &amp;&amp;
    *             getState() != {@link #WITHIN_ELEMENT         WITHIN_ELEMENT}</code>.
    *
    *
    * @throws IllegalArgumentException
    *    if <code>type == null</code>.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE ERROR_STATE}.
    */
   void startTag(String type)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Adds an attribute to the current element. There must currently be an
    * open element.
    *
    * <p>The attribute value is surrounded by single quotes.
    *
    * @param name
    *    the name of the attribute, not <code>null</code>.
    *
    * @param value
    *    the value of the attribute, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() != {@link #START_TAG_OPEN}</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null || value == null</code>.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void attribute(String name, String value)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of an element end tag.
    *
    * @throws IllegalStateException
    *    if <code>getState() != START_TAG_OPEN &amp;&amp;
    *             getState() != WITHIN_ELEMENT</code>
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void endTag()
   throws IllegalStateException, IOException;

   /**
    * Notification of a PCDATA section (as a <code>String</code>).
    *
    * @param text
    *    the PCDATA section contents, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() != START_TAG_OPEN &amp;&amp;
    *             getState() != WITHIN_ELEMENT</code>
    *
    * @throws IllegalArgumentException
    *    if <code>text == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void pcdata(String text)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of a PCDATA section (as a <code>char</code> array).
    *
    * @param ch
    *    the character array containing the PCDATA section contents, not
    *    <code>null</code>.
    *
    * @param start
    *    the start index in the array, must be &gt;= 0 and it must be &lt;
    *    <code>ch.length</code>.
    *
    * @param length
    *    the number of characters to read from the array, must be &gt; 0.
    *
    * @throws IllegalStateException
    *    if <code>getState() != START_TAG_OPEN &amp;&amp;
    *             getState() != WITHIN_ELEMENT</code>
    *
    * @throws IllegalArgumentException
    *    if <code>ch     ==    null
    *          || start  &lt;  0
    *          || start  &gt;= ch.length
    *          || length &lt;  0</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if <code>start + length &gt; ch.length</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void pcdata(char[] ch, int start, int length)
   throws IllegalStateException, IllegalArgumentException, IndexOutOfBoundsException, InvalidXMLException, IOException;

   /**
    * Notification of a CDATA section.
    *
    * <p>A CDATA section can contain any string, except
    * <code>"]]&gt;"</code>. This will, however, not be checked by this
    * method.
    *
    * <p>Left angle brackets and ampersands will be output in their literal
    * form; they need not (and cannot) be escaped using
    * <code>"&amp;lt;"</code> and <code>"&amp;amp;"</code>.
    *
    * <p>If the specified string is empty (i.e.
    * <code>"".equals(text)</code>, then nothing will be output.
    *
    * <p>If the specified string contains characters that cannot be printed
    * in this encoding, then the result is undefined.
    *
    * @param text
    *    the contents of the CDATA section, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() != START_TAG_OPEN &amp;&amp;
    *             getState() != WITHIN_ELEMENT</code>
    *
    * @throws IllegalArgumentException
    *    if <code>text == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void cdata(String text)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of ignorable whitespace (as a <code>String</code>).
    * Ignorable whitespace can be found anywhere in an XML document,
    * except above the XML declaration.
    *
    * <p>This method does not check if the string actually contains
    * whitespace.
    *
    * <p>If the state equals {@link #BEFORE_XML_DECLARATION}, then it will be set to
    * {@link #BEFORE_DTD_DECLARATION}, otherwise if the state is
    * {@link #START_TAG_OPEN} then it will be set to {@link #WITHIN_ELEMENT},
    * otherwise the state will not be changed.
    *
    * @param whitespace
    *    the ignorable whitespace to be written, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() == ERROR_STATE</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>whitespace == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void whitespace(String whitespace)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of ignorable whitespace (as a <code>String</code>).
    * Ignorable whitespace can be found anywhere in an XML document,
    * except above the XML declaration.
    *
    * <p>This method does not check if the string actually contains
    * whitespace.
    *
    * <p>If the state equals {@link #BEFORE_XML_DECLARATION}, then it will be set to
    * {@link #BEFORE_DTD_DECLARATION}, otherwise if the state is
    * {@link #START_TAG_OPEN} then it will be set to {@link #WITHIN_ELEMENT},
    * otherwise the state will not be changed.
    *
    * @param ch
    *    the character array containing the text to be written, not
    *    <code>null</code>.
    *
    * @param start
    *    the start index in the array, must be &gt;= 0 and it must be &lt;
    *    <code>ch.length</code>.
    *
    * @param length
    *    the number of characters to read from the array, must be &gt; 0.
    *
    * @throws IllegalStateException
    *    if <code>getState() == ERROR_STATE</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>ch     ==    null
    *          || start  &lt;  0
    *          || start  &gt;= ch.length
    *          || length &lt;  0</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if <code>start + length &gt; ch.length</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void whitespace(char[] ch, int start, int length)
   throws IllegalStateException, IllegalArgumentException, IndexOutOfBoundsException, InvalidXMLException, IOException;

   /**
    * Notification of a comment. The comment should not contain the string
    * <code>"--"</code>.
    *
    * <p>If the state equals {@link #BEFORE_XML_DECLARATION}, then it will be set to
    * {@link #BEFORE_DTD_DECLARATION}, otherwise if the state is
    * {@link #START_TAG_OPEN} then it will be set to {@link #WITHIN_ELEMENT},
    * otherwise the state will not be changed.
    *
    * @param text
    *    the text for the comment be written, not <code>null</code>.
    *
    * @throws IllegalStateException
    *    if <code>getState() == ERROR_STATE</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>text == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void comment(String text)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of a processing instruction. A target and an optional
    * instruction should be specified.
    *
    * <p>A processing instruction can appear above and below the root
    * element, and between elements. It cannot appear inside an element start
    * or end tag, nor inside a comment. Processing instructions cannot be
    * nested.
    *
    * <p>If the state equals {@link #BEFORE_XML_DECLARATION}, then it will be set to
    * {@link #BEFORE_DTD_DECLARATION}, otherwise the state will not be
    * changed.
    *
    * @param target
    *    an identification of the application at which the instruction is
    *    targeted, not <code>null</code>.
    *
    * @param instruction
    *    the instruction, can be <code>null</code>, which is equivalent to an
    *    empty string.
    *
    * @throws IllegalStateException
    *    if <code>getState() == ERROR_STATE</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>target == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void pi(String target, String instruction)
   throws IllegalStateException, IllegalArgumentException, InvalidXMLException, IOException;

   /**
    * Notification of the end of the document. After calling this method, none
    * of the other notification methods can be called until {@link #reset()}
    * is called.
    *
    * @throws IllegalStateException
    *    if <code>getState() == UNINITIALIZED
    *          || getState() == DOCUMENT_ENDED</code>.
    *
    * @throws IOException
    *    if an I/O error occurs; this will set the state to
    *    {@link #ERROR_STATE}.
    */
   void endDocument()
   throws IllegalStateException, IOException;
}
