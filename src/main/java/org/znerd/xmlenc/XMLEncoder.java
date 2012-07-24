// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Encodes character streams for an XML document.
 *
 * <p>The following encodings are supported:
 *
 * <ul>
 *    <li><code>UTF-8</code>
 *    <li><code>UTF-16</code>
 *    <li><code>US-ASCII</code>, with alias <code>ASCII</code>
 *    <li>all <code>ISO-8859</code> encodings
 * </ul>
 *
 * @since xmlenc 0.1
 */
public class XMLEncoder extends Object {

   // For this encoder, different Unicode characters are treated differently.
   //
   // Within attribute values, the following applies:
   //
   // ID  Dec     Description          Escaping
   // __  ______  __________________   _________________________
   //
   // A   0-8     Control characters   -- Not allowed in XML 1.0 --
   // B   9-10    Normal characters    Never needed
   // C   11-12   Control characters   -- Not allowed in XML 1.0 --
   // D   13      Normal character     Never needed
   // E   14-31   Control characters   -- Not allowed in XML 1.0 --
   // F   32-33   Normal characters    Never needed
   // G   34      Quote (")            If quotation mark
   // H   35-37   Normal characters    Never needed
   // I   38      Ampersand (&)        If escapeAmpersands=true
   // J   39      Apostrophe (')       If quotation mark
   // K   40-59   Normal characters    Never needed
   // L   60      Less than (<)        Always
   // M   61      Normal character     Never needed
   // N   62      Greater than (>)     Always
   // O   63-127  Normal characters    Never needed
   // P   128+    Normal characters    If encoding is ASCII
   //
   // Outside attribute values, the following applies:
   //
   // ID  Dec     Description          Escaping
   // __  ______  __________________   _________________________
   //
   // A   0-8     Control characters   -- Not allowed in XML 1.0 --
   // B   9-10    Normal characters    Never needed
   // C   11-12   Control characters   -- Not allowed in XML 1.0 --
   // D   13      Normal character     Never needed
   // E   14-31   Control characters   -- Not allowed in XML 1.0 --
   // FGH 32-37   Normal characters    Never needed
   // I   38      Ampersand (&)        If escapeAmpersands=true
   // JK  39-59   Normal characters    Never needed
   // L   60      Less than (<)        Always
   // M   61      Normal character     Never needed
   // N   62      Greater than (>)     Always
   // O   63-127  Normal characters    Never needed
   // P   128+    Normal characters    If encoding is ASCII
   //
   // The following characters are expected to be encountered the most often:
   //
   //  32      Space                      Part of range F
   //  10      Linefeed                   Part of range B
   //  13      Carriage return            Range D
   //  48-57   Digits 0-9                 Part of range K
   //  65-90   Uppercase letters A-Z      Part of range O
   //  97-122  Lowercase letters a-z      Part of range O
   //
   // After that, the following characters are expected to be encountered the
   // most often:
   //
   //  9       Tab                        Part of range B
   //  33      Exclamation mark           Part of range F
   //  34      Quote                      Range G
   //  35-37   Hash, dollar, percent      Range H
   //  38      Ampersand                  Range I
   //  39      Apostrophe                 Range J
   //  40-47   Punctuation, etc.          Part of range K
   //  58-59   Punctuation, etc.          Part of range K
   //  60      Less-than                  Range L
   //  61      Equals                     Range M
   //  62      Greater-than               Range N
   //  63-64   Question, at-sign          Part of range O
   //  91-96   Punctuation, etc.          Part of range O
   //  123-127 Punctuation                Part of range O
   //
   // And the following characters are expected to be encountered the least:
   //
   //  128+    High characters            Range P
   //
   //
   // See:
   // http://www.w3.org/TR/REC-xml
   // http://www.jimprice.com/ascii-0-127.gif


   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Retrieves an <code>XMLEncoder</code> for the specified encoding. If no
    * suitable instance can be returned, then an exception is thrown.
    *
    * @param encoding
    *    the name of the encoding, not <code>null</code>.
    *
    * @return
    *    an <code>XMLEncoder</code> instance that matches the specified
    *    encoding, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>encoding == null</code>.
    *
    * @throws UnsupportedEncodingException
    *    if the specified encoding is not supported.
    */
   public static final XMLEncoder getEncoder(String encoding)
   throws IllegalArgumentException, UnsupportedEncodingException {
      return new XMLEncoder(encoding);
   }


   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   /**
    * The first part of a declaration, before the encoding.
    */
   private static final char[] DECLARATION_START = "<?xml version=\"1.0\" encoding=\"".toCharArray();

   /**
    * The length of <code>DECLARATION_START</code>.
    */
   private static final int DECLARATION_START_LENGTH = DECLARATION_START.length;

   /**
    * The last part of a declaration, after the encoding.
    */
   private static final char[] DECLARATION_END = "\"?>".toCharArray();

   /**
    * The length of <code>DECLARATION_END</code>.
    */
   private static final int DECLARATION_END_LENGTH = DECLARATION_END.length;

   /**
    * Character array representing the string <code>"&gt;"</code>.
    */
   private static final char[] ESC_GREATER_THAN = new char[] { '&', 'g', 't', ';' };

   /**
    * Character array representing the string <code>"&lt;"</code>.
    */
   private static final char[] ESC_LESS_THAN = new char[] { '&', 'l', 't', ';' };

   /**
    * Character array representing the string <code>"&amp;amp;"</code>.
    */
   private static final char[] ESC_AMPERSAND = new char[] { '&', 'a', 'm', 'p', ';' };

   /**
    * Character array representing the string <code>"&amp;apos;"</code>.
    */
   private static final char[] ESC_APOSTROPHE = new char[] { '&', 'a', 'p', 'o', 's', ';' };

   /**
    * Character array representing the string <code>"&amp;apos;"</code>.
    */
   private static final char[] ESC_QUOTE = new char[] { '&', 'q', 'u', 'o', 't', ';' };

   /**
    * Character array representing the string <code>"&amp;#"</code>.
    */
   private static final char[] AMPERSAND_HASH = new char[] { '&', '#' };

   /**
    * Character array representing the string <code>"='"</code>.
    */
   private static final char[] EQUALS_APOSTROPHE = new char[] { '=', '\'' };

   /**
    * Character array representing the string <code>"=\""</code>.
    */
   private static final char[] EQUALS_QUOTE = new char[] { '=', '"' };


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>XMLEncoder</code> instance.
    *
    * @param encoding
    *    the name of the encoding, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>encoding == null</code>.
    *
    * @throws UnsupportedEncodingException
    *    if the specified encoding is not supported.
    *
    * @deprecated
    *    Deprecated since xmlenc 0.47.
    *    Use the factory method {@link #getEncoder(String)} instead.
    */
   public XMLEncoder(String encoding)
   throws IllegalArgumentException, UnsupportedEncodingException {

      // Check argument
      if (encoding == null) {
         throw new IllegalArgumentException("encoding == null");
      }

      // Uppercase encoding to compare it with supported encodings in a
      // case-insensitive manner
      String ucEncoding = encoding.toUpperCase();

      // Check if the encoding supports all Unicode characters
      if (ucEncoding.equals("UTF-8") || ucEncoding.equals("UTF-16")) {
         _sevenBitEncoding = false;

      // Check if this is an ISO 646-based character set (7-bit ASCII)
      } else if (ucEncoding.equals("US-ASCII")
              || ucEncoding.equals("ASCII")
              || ucEncoding.startsWith("ISO-8859-")) {
         _sevenBitEncoding = true;

      // Otherwise fail
      } else {
         throw new UnsupportedEncodingException(encoding);
      }

      // Store encoding literally as passed
      _encoding = encoding;
      _encodingCharArray = encoding.toCharArray();
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The name of the encoding. Cannot be <code>null</code>.
    */
   private final String _encoding;

   /**
    * The name of the encoding as a character array. Cannot be
    * <code>null</code>.
    */
   private final char[] _encodingCharArray;

   /**
    * Flag that indicates whether the encoding is based on the ISO 646
    * character set. The value is <code>true</code> if the encoding is a 7 bit
    * encoding, or <code>false</code> if the encoding supports all Unicode
    * characters.
    */
   private final boolean _sevenBitEncoding;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Returns the encoding.
    *
    * @return
    *    the encoding passed to the constructor, never <code>null</code>.
    */
   public String getEncoding() {
      return _encoding;
   }

   /**
    * Writes an XML declaration.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @throws NullPointerException
    *    if <code>out == null</code>.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void declaration(Writer out)
   throws NullPointerException, IOException {
      out.write(DECLARATION_START, 0, DECLARATION_START_LENGTH);
      out.write(_encodingCharArray);
      out.write(DECLARATION_END, 0, DECLARATION_END_LENGTH);
   }

   /**
    * Writes the specified text. Any characters that are non-printable in this
    * encoding will be escaped.
    *
    * <p />It must be specified whether ampersands should be escaped. Unless
    * ampersands are escaped, entity references can be written.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param text
    *    the text to be written, not <code>null</code>.
    *
    * @param escapeAmpersands
    *    flag that indicates whether ampersands should be escaped.
    *
    * @throws NullPointerException
    *    if <code>out == null || text == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void text(Writer out, String text, boolean escapeAmpersands)
   throws NullPointerException, InvalidXMLException, IOException {

      text(out,
           text.toCharArray(),
           0,
           text.length(),
           escapeAmpersands);
   }

   /**
    * Writes text from the specified character array. Any characters that are
    * non-printable in this encoding will be escaped.
    *
    * <p />It must be specified whether ampersands should be escaped. Unless
    * ampersands are escaped, entity references can be written.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param ch
    *    the character array from which to retrieve the text to be written,
    *    not <code>null</code>.
    *
    * @param start
    *    the start index into <code>ch</code>, must be &gt;= 0.
    *
    * @param length
    *    the number of characters to take from <code>ch</code>, starting at
    *    the <code>start</code> index.
    *
    * @param escapeAmpersands
    *    flag that indicates if ampersands should be escaped.
    *
    * @throws NullPointerException
    *    if <code>out == null || ch == null</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if <code>start &lt; 0
    *          || start + length &gt; ch.length</code>; this may not be
    *          checked before the character stream is written to, so this may
    *          cause a <em>partial</em> failure.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void text(Writer  out,
                    char[]  ch,
                    int     start,
                    int     length,
                    boolean escapeAmpersands)
   throws NullPointerException,
          IndexOutOfBoundsException,
          InvalidXMLException,
          IOException {

      int end = start + length;

      // The position after the last escaped character
      int lastEscaped = start;

      for (int i = start; i < end; i++) {
         int c = (int) ch[i];

         if ((c >= 63 && c <= 127) || (c >= 39 && c <= 59) || (c >= 32 && c <= 37)
               || (c == 38 && !escapeAmpersands) || (c > 127 && !_sevenBitEncoding)
               || c == 10 || c == 13 || c == 61 || c == 9) {
            continue;
         } else {
            out.write(ch, lastEscaped, i - lastEscaped);
            if (c == 60) {
               out.write(ESC_LESS_THAN, 0, 4);
            } else if (c == 62) {
               out.write(ESC_GREATER_THAN, 0, 4);
            } else if (c == 38) {
               out.write(ESC_AMPERSAND, 0, 5);
            } else if (c > 127) {
               out.write(AMPERSAND_HASH, 0, 2);
               out.write(Integer.toString(c));
               out.write(';');
            } else {
               throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid.");
            }
            lastEscaped = i + 1;
         }
      }
      out.write(ch, lastEscaped, end - lastEscaped);
   }

   /**
    * Writes the specified character. If the character is non-printable in
    * this encoding, then it will be escaped.
    *
    * <p />It is safe for this method to assume that the specified character
    * does not need to be escaped unless the encoding does not support the
    * character.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param c
    *    the character to be written.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs.
    *
    * @deprecated
    *    Deprecated since xmlenc 0.51.
    *    Use the text method {@link #text(Writer, char, boolean)} instead.
    */
   public void text(Writer out, char c) throws InvalidXMLException, IOException {
      if ((c >= 63 && c <= 127) || (c >= 39 && c <= 59) || (c >= 32 && c <= 37)
            || (c == 38) || (c > 127 && !_sevenBitEncoding)
            || c == 10 || c == 13 || c == 61 || c == 9) {
         out.write(c);
      } else {
         if (c == 60) {
            out.write(ESC_LESS_THAN, 0, 4);
         } else if (c == 62) {
            out.write(ESC_GREATER_THAN, 0, 4);
         } else if (c > 127) {
            out.write(AMPERSAND_HASH, 0, 2);
            out.write(Integer.toString(c));
            out.write(';');
         } else {
            throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid.");
         }
      }
   }

   /**
    * Writes the specified character. If the character is non-printable in
    * this encoding, then it will be escaped.
    *
    * <p />It is safe for this method to assume that the specified character
    * does not need to be escaped unless the encoding does not support the
    * character.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param c
    *    the character to be written.
    *
    * @param escapeAmpersands
    *    flag that indicates if ampersands should be escaped.
    *
    * @throws InvalidXMLException
    *    if the specified text contains an invalid character.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void text(Writer out, char c, boolean escapeAmpersands) throws InvalidXMLException, IOException {
      if ((c >= 63 && c <= 127) || (c >= 39 && c <= 59) || (c >= 32 && c <= 37)
            || (c == 38 && escapeAmpersands) || (c > 127 && !_sevenBitEncoding)
            || c == 10 || c == 13 || c == 61 || c == 9) {
         out.write(c);
      } else {
         if (c == 60) {
            out.write(ESC_LESS_THAN, 0, 4);
         } else if (c == 62) {
            out.write(ESC_GREATER_THAN, 0, 4);
         } else if (c == 38) {
            out.write(ESC_AMPERSAND, 0, 5);
         } else if (c > 127) {
            out.write(AMPERSAND_HASH, 0, 2);
            out.write(Integer.toString(c));
            out.write(';');
         } else {
            throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid.");
         }
      }
   }

   /**
    * Writes the specified whitespace string.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param s
    *    the character string to be written, not <code>null</code>.
    *
    * @throws NullPointerException
    *    if <code>out == null || s == null</code>.
    *
    * @throws InvalidXMLException
    *    if the specified character string contains a character that is
    *    invalid as whitespace.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void whitespace(Writer out, String s)
   throws NullPointerException, InvalidXMLException, IOException {

      char[] ch  = s.toCharArray();
      int length = ch.length;
      whitespace(out, ch, 0, length);
   }

   /**
    * Writes whitespace from the specified character array.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param ch
    *    the character array from which to retrieve the text to be written,
    *    not <code>null</code>.
    *
    * @param start
    *    the start index into <code>ch</code>, must be &gt;= 0.
    *
    * @param length
    *    the number of characters to take from <code>ch</code>, starting at
    *    the <code>start</code> index.
    *
    * @throws NullPointerException
    *    if <code>out == null || ch == null</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if <code>start &lt; 0
    *          || start + length &gt; ch.length</code>; this may not be
    *          checked before the character stream is written to, so this may
    *          cause a <em>partial</em> failure.
    *
    * @throws InvalidXMLException
    *    if the specified character array contains a character that is invalid
    *    as whitespace.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void whitespace(Writer out,
                          char[] ch,
                          int    start,
                          int    length)
   throws NullPointerException,
          IndexOutOfBoundsException,
          InvalidXMLException,
          IOException {

      // Check the string
      XMLChecker.checkS(ch, start, length);

      // Write the complete character string at once
      out.write(ch, start, length);
   }

   /**
    * Writes an attribute assignment.
    *
    * @param out
    *    the character stream to write to, not <code>null</code>.
    *
    * @param name
    *    the name of the attribute, not <code>null</code>.
    *
    * @param value
    *    the value of the attribute, not <code>null</code>.
    *
    * @param quotationMark
    *    the quotation mark, must be either the apostrophe (<code>'\''</code>)
    *    or the quote character (<code>'"'</code>).
    *
    * @throws NullPointerException
    *    if <code>out == null || value == null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>quotationMark != '\'' &amp;&amp; quotationMark != '"'</code>.
    *
    * @throws IOException
    *    if an I/O error occurs.
    */
   public void attribute(Writer  out,
                         String  name,
                         String  value,
                         char    quotationMark,
                         boolean escapeAmpersands)
   throws NullPointerException, IOException {

      char[] ch = value.toCharArray();
      int length = ch.length;
      int start = 0;
      int end = start + length;

      // TODO: Call overloaded attribute method that accepts char[]

      // The position after the last escaped character
      int lastEscaped = 0;

      boolean useQuote;
      if (quotationMark == '"') {
         useQuote = true;
      } else if (quotationMark == '\'') {
         useQuote = false;
      } else {
         String error = "Character 0x"
                      + Integer.toHexString((int) quotationMark)
                      + " ('"
                      + quotationMark
                      + "') is not a valid quotation mark.";
         throw new IllegalArgumentException(error);
      }

      out.write(' ');
      out.write(name);

      if (useQuote) {
         out.write(EQUALS_QUOTE, 0, 2);
      } else {
         out.write(EQUALS_APOSTROPHE, 0, 2);
      }

      for (int i = start; i < end; i++) {
         int c = (int) ch[i];

         if ((c >= 63 && c <= 127) || (c >= 40 && c <= 59) || (c >= 32 && c <= 37 && c != 34)
               || (c == 38 && !escapeAmpersands) || (c > 127 && !_sevenBitEncoding)
               || (!useQuote && c == 34) || (useQuote && c == 39) || c == 10 || c == 13 || c == 61 || c == 9) {
            continue;
         } else {
            out.write(ch, lastEscaped, i - lastEscaped);
            if (c == 60) {
               out.write(ESC_LESS_THAN, 0, 4);
            } else if (c == 62) {
               out.write(ESC_GREATER_THAN, 0, 4);
            } else if (c == 34) {
               out.write(ESC_QUOTE, 0, 6);
            } else if (c == 39) {
               out.write(ESC_APOSTROPHE, 0, 6);
            } else if (c == 38) {
               out.write(ESC_AMPERSAND, 0, 5);
            } else if (c > 127) {
               out.write(AMPERSAND_HASH, 0, 2);
               out.write(Integer.toString(c));
               out.write(';');
            } else {
               throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid.");
            }
            lastEscaped = i + 1;
         }
      }

      out.write(ch, lastEscaped, length - lastEscaped);
      out.write(quotationMark);
   }
}
