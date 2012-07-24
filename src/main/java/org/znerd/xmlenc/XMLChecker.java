// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Utility class that provides XML checking functionality.
 * 
 * @since XMLenc 0.41
 */
public final class XMLChecker extends Object {

    /**
     * Checks if the specified string matches the <em>S</em> (white space) production. See: <a
     * href="http://www.w3.org/TR/REC-xml#NT-S">Definition of S</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @throws InvalidXMLException if the specified character string does not match the <em>S</em>
     *         production.
     */
    public static final void checkS(String s) throws NullPointerException {
        checkS(s.toCharArray(), 0, s.length());
    }

    /**
     * Checks if the specified part of a character array matches the <em>S</em> (white space)
     * production. See: <a href="http://www.w3.org/TR/REC-xml#NT-S">Definition of S</a>.
     * 
     * @param ch the character array that contains the characters to be checked, cannot be
     *        <code>null</code>.
     * @param start the start index into <code>ch</code>, must be &gt;= 0.
     * @param length the number of characters to take from <code>ch</code>, starting at the
     *        <code>start</code> index.
     * @throws NullPointerException if <code>ch == null</code>.
     * @throws IndexOutOfBoundsException if
     *         <code>start &lt; 0 || start + length &gt; ch.length</code>.
     * @throws InvalidXMLException if the specified character string does not match the <em>S</em>
     *         production.
     */
    public static final void checkS(char[] ch, int start, int length) throws NullPointerException, IndexOutOfBoundsException, InvalidXMLException {

        // Loop through the array and check each character
        for (int i = start; i < length; i++) {
            int c = ch[i];

            if (c != 0x20 && c != 0x9 && c != 0xD && c != 0xA) {
                throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid for the 'S' production (white space).");
            }
        }
    }

    /**
     * Determines if the specified string matches the <em>Name</em> production. See: <a
     * href="http://www.w3.org/TR/REC-xml#NT-Name">Definition of Name</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @return <code>true</code> if the {@link String} matches the production, or <code>false</code>
     *         otherwise.
     */
    public static final boolean isName(String s) throws NullPointerException {
        try {
            checkName(s);
            return true;
        } catch (InvalidXMLException exception) {
            return false;
        }
    }

    /**
     * Checks if the specified string matches the <em>Name</em> production. See: <a
     * href="http://www.w3.org/TR/REC-xml#NT-Name">Definition of Name</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>Name</em> production.
     */
    public static final void checkName(String s) throws NullPointerException, InvalidXMLException {
        checkName(s.toCharArray(), 0, s.length());
    }

    /**
     * Checks if the specified part of a character array matches the <em>Name</em> production. See:
     * <a href="http://www.w3.org/TR/REC-xml#NT-Name">Definition of Name</a>.
     * 
     * @param ch the character array that contains the characters to be checked, cannot be
     *        <code>null</code>.
     * @param start the start index into <code>ch</code>, must be &gt;= 0.
     * @param length the number of characters to take from <code>ch</code>, starting at the
     *        <code>start</code> index.
     * @throws NullPointerException if <code>ch == null</code>.
     * @throws IndexOutOfBoundsException if
     *         <code>start &lt; 0 || start + length &gt; ch.length</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>Name</em> production.
     */
    public static final void checkName(char[] ch, int start, int length) throws NullPointerException, IndexOutOfBoundsException, InvalidXMLException {

        // Minimum length is 1
        if (length < 1) {
            throw new InvalidXMLException("An empty string does not match the 'Name' production.");
        }

        // First char must match: (Letter | '_' | ':')
        int i = start;
        char c = ch[i];
        if (c != '_' && c != ':' && !isLetter(c)) {
            throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is invalid as a starting character in the 'Name' production.");
        }

        // Loop through the array and check each character
        for (i++; i < length; i++) {
            c = ch[i];

            if (!isNameChar(c)) {
                throw new InvalidXMLException("The character 0x" + Integer.toHexString(c) + " is not valid for the 'Name' production.");
            }
        }
    }

    /**
     * Determines if the specified string matches the <em>SystemLiteral</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-SystemLiteral">Definition of SystemLiteral</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @return <code>true</code> if the {@link String} matches the production, or <code>false</code>
     *         otherwise.
     */
    public static final boolean isSystemLiteral(String s) throws NullPointerException {
        try {
            checkSystemLiteral(s);
            return true;
        } catch (InvalidXMLException exception) {
            return false;
        }
    }

    /**
     * Checks if the specified string matches the <em>SystemLiteral</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-SystemLiteral">Definition of SystemLiteral</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>PubidLiteral</em> production.
     */
    public static final void checkSystemLiteral(String s) throws NullPointerException, InvalidXMLException {
        checkSystemLiteral(s.toCharArray(), 0, s.length());
    }

    /**
     * Checks if the specified part of a character array matches the <em>SystemLiteral</em>
     * production. See: <a href="http://www.w3.org/TR/REC-xml#NT-SystemLiteral">Definition of
     * SystemLiteral</a>.
     * 
     * @param ch the character array that contains the characters to be checked, cannot be
     *        <code>null</code>.
     * @param start the start index into <code>ch</code>, must be &gt;= 0.
     * @param length the number of characters to take from <code>ch</code>, starting at the
     *        <code>start</code> index.
     * @throws NullPointerException if <code>ch == null</code>.
     * @throws IndexOutOfBoundsException if
     *         <code>start &lt; 0 || start + length &gt; ch.length</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>SystemLiteral</em> production.
     */
    public static final void checkSystemLiteral(char[] ch, int start, int length) throws NullPointerException, IndexOutOfBoundsException, InvalidXMLException {

        // Minimum length is 3
        if (length < 3) {
            throw new InvalidXMLException("Minimum length for the 'SystemLiteral' production is 3 characters.");
        }

        int lastIndex = start + length - 1;
        char firstChar = ch[0];
        char lastChar = ch[lastIndex];

        // First and last char: single qoute (apostrophe)
        if (firstChar == '\'') {
            if (lastChar != '\'') {
                throw new InvalidXMLException("First character is '\\'', but the " + "last character is 0x" + Integer.toHexString(lastChar) + '.');
            }

            // First and last char: double qoute character
        } else if (firstChar == '"') {
            if (lastChar != '"') {
                throw new InvalidXMLException("First character is '\"', but the " + "last character is 0x" + Integer.toHexString(lastChar) + '.');
            }

            // First character is invalid
        } else {
            throw new InvalidXMLException("First char must either be '\\'' or " + "'\"' instead of 0x" + Integer.toHexString(firstChar) + '.');
        }

        // Check each character
        for (int i = 1; i < length - 1; i++) {
            char c = ch[i];

            if (c == firstChar) {
                if (firstChar == '\'') {
                    throw new InvalidXMLException("Found '\\'' at position " + i + '.');
                } else {
                    throw new InvalidXMLException("Found '\"' at position " + i + '.');
                }
            }
        }
    }

    /**
     * Determines if the specified string matches the <em>PubidLiteral</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-PubidLiteral">Definition of PubidLiteral</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @return <code>true</code> if the {@link String} matches the production, or <code>false</code>
     *         otherwise.
     */
    public static final boolean isPubidLiteral(String s) throws NullPointerException {
        try {
            checkPubidLiteral(s);
            return true;
        } catch (InvalidXMLException exception) {
            return false;
        }
    }

    /**
     * Checks if the specified string matches the <em>PubidLiteral</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-PubidLiteral">Definition of PubidLiteral</a>.
     * 
     * @param s the character string to check, cannot be <code>null</code>.
     * @throws NullPointerException if <code>s == null</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>PubidLiteral</em> production.
     */
    public static final void checkPubidLiteral(String s) throws NullPointerException, InvalidXMLException {
        checkPubidLiteral(s.toCharArray(), 0, s.length());
    }

    /**
     * Checks if the specified part of a character array matches the <em>PubidLiteral</em>
     * production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-PubidLiteral">Definition of PubidLiteral</a>.
     * 
     * @param ch the character array that contains the characters to be checked, cannot be
     *        <code>null</code>.
     * @param start the start index into <code>ch</code>, must be &gt;= 0.
     * @param length the number of characters to take from <code>ch</code>, starting at the
     *        <code>start</code> index.
     * @throws NullPointerException if <code>ch == null</code>.
     * @throws IndexOutOfBoundsException if
     *         <code>start &lt; 0 || start + length &gt; ch.length</code>.
     * @throws InvalidXMLException if the specified character string does not match the
     *         <em>PubidLiteral</em> production.
     */
    public static final void checkPubidLiteral(char[] ch, int start, int length) throws NullPointerException, IndexOutOfBoundsException, InvalidXMLException {

        // Minimum length is 3
        if (length < 3) {
            throw new InvalidXMLException("Minimum length for the 'PubidLiteral' production is 3 characters.");
        }

        int lastIndex = start + length - 1;
        char firstChar = ch[0];
        char lastChar = ch[lastIndex];

        // First and last char: single qoute (apostrophe)
        String otherAllowedChars;
        if (firstChar == '\'') {
            if (lastChar != '\'') {
                throw new InvalidXMLException("First character is '\\'', but the " + "last character is 0x" + Integer.toHexString(lastChar) + '.');
            }
            otherAllowedChars = "-()+,./:=?;!*#@$_%";

            // First and last char: double qoute character
        } else if (firstChar == '"') {
            if (lastChar != '"') {
                throw new InvalidXMLException("First character is '\"', but the " + "last character is 0x" + Integer.toHexString(lastChar) + '.');
            }
            otherAllowedChars = "-'()+,./:=?;!*#@$_%";

            // First character is invalid
        } else {
            throw new InvalidXMLException("First char must either be '\\'' or " + "'\"' instead of 0x" + Integer.toHexString(firstChar) + '.');
        }

        // Check each character
        for (int i = 1; i < length - 1; i++) {
            char c = ch[i];

            if (c != 0x20 && c != 0x0D && c != 0x0A && !isLetter(c) && !isDigit(c) && otherAllowedChars.indexOf(c) < 0) {
                // TODO: Quote character properly, even if it is an apostrophe
                throw new InvalidXMLException("The character '" + c + "' (0x" + Integer.toHexString(c) + ") is not valid for the " + "'PubidLiteral' production.");
            }
        }
    }

    /**
     * Determines if the specified character matches the <em>NameChar</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-NameChar">Definition of NameChar</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isNameChar(char c) {
        return c == '.' || c == '-' || c == '_' || c == ':' || isDigit(c) || isLetter(c) || isCombiningChar(c) || isExtender(c);
    }

    /**
     * Determines if the specified character matches the <em>Letter</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-Letter">Definition of Letter</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isLetter(char c) {
        return isBaseChar(c) || isIdeographic(c);
    }

    /**
     * Determines if the specified character matches the <em>BaseChar</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-BaseChar">Definition of BaseChar</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isBaseChar(char c) {
        int n = c;
        return n >= 0x0041 && n <= 0x005A || n >= 0x0061 && n <= 0x007A || n >= 0x00C0 && n <= 0x00D6 || n >= 0x00D8 && n <= 0x00F6 || n >= 0x00F8 && n <= 0x00FF || n >= 0x0100 && n <= 0x0131 || n >= 0x0134 && n <= 0x013E || n >= 0x0141 && n <= 0x0148 || n >= 0x014A && n <= 0x017E || n >= 0x0180 && n <= 0x01C3 || n >= 0x01CD && n <= 0x01F0 || n >= 0x01F4 && n <= 0x01F5 || n >= 0x01FA && n <= 0x0217 || n >= 0x0250 && n <= 0x02A8 || n >= 0x02BB && n <= 0x02C1 || n == 0x0386 || n >= 0x0388 && n <= 0x038A || n == 0x038C || n >= 0x038E && n <= 0x03A1 || n >= 0x03A3 && n <= 0x03CE || n >= 0x03D0 && n <= 0x03D6 || n == 0x03DA || n == 0x03DC || n == 0x03DE || n == 0x03E0 || n >= 0x03E2 && n <= 0x03F3 || n >= 0x0401 && n <= 0x040C || n >= 0x040E && n <= 0x044F || n >= 0x0451 && n <= 0x045C || n >= 0x045E && n <= 0x0481 || n >= 0x0490 && n <= 0x04C4 || n >= 0x04C7 && n <= 0x04C8 || n >= 0x04CB && n <= 0x04CC || n >= 0x04D0 && n <= 0x04EB || n >= 0x04EE && n <= 0x04F5 || n >= 0x04F8 && n <= 0x04F9 || n >= 0x0531 && n <= 0x0556 || n == 0x0559 || n >= 0x0561 && n <= 0x0586 || n >= 0x05D0 && n <= 0x05EA || n >= 0x05F0 && n <= 0x05F2 || n >= 0x0621 && n <= 0x063A || n >= 0x0641 && n <= 0x064A || n >= 0x0671 && n <= 0x06B7 || n >= 0x06BA && n <= 0x06BE || n >= 0x06C0 && n <= 0x06CE || n >= 0x06D0 && n <= 0x06D3 || n == 0x06D5 || n >= 0x06E5 && n <= 0x06E6 || n >= 0x0905 && n <= 0x0939 || n == 0x093D || n >= 0x0958 && n <= 0x0961 || n >= 0x0985 && n <= 0x098C || n >= 0x098F && n <= 0x0990 || n >= 0x0993 && n <= 0x09A8 || n >= 0x09AA && n <= 0x09B0 || n == 0x09B2 || n >= 0x09B6 && n <= 0x09B9 || n >= 0x09DC && n <= 0x09DD || n >= 0x09DF && n <= 0x09E1 || n >= 0x09F0 && n <= 0x09F1 || n >= 0x0A05 && n <= 0x0A0A || n >= 0x0A0F && n <= 0x0A10 || n >= 0x0A13 && n <= 0x0A28 || n >= 0x0A2A && n <= 0x0A30 || n >= 0x0A32 && n <= 0x0A33 || n >= 0x0A35 && n <= 0x0A36 || n >= 0x0A38 && n <= 0x0A39 || n >= 0x0A59 && n <= 0x0A5C || n == 0x0A5E || n >= 0x0A72 && n <= 0x0A74 || n >= 0x0A85 && n <= 0x0A8B || n == 0x0A8D || n >= 0x0A8F && n <= 0x0A91 || n >= 0x0A93 && n <= 0x0AA8 || n >= 0x0AAA && n <= 0x0AB0 || n >= 0x0AB2 && n <= 0x0AB3 || n >= 0x0AB5 && n <= 0x0AB9 || n == 0x0ABD || n == 0x0AE0 || n >= 0x0B05 && n <= 0x0B0C || n >= 0x0B0F && n <= 0x0B10 || n >= 0x0B13 && n <= 0x0B28 || n >= 0x0B2A && n <= 0x0B30 || n >= 0x0B32 && n <= 0x0B33 || n >= 0x0B36 && n <= 0x0B39 || n == 0x0B3D || n >= 0x0B5C && n <= 0x0B5D || n >= 0x0B5F && n <= 0x0B61 || n >= 0x0B85 && n <= 0x0B8A || n >= 0x0B8E && n <= 0x0B90 || n >= 0x0B92 && n <= 0x0B95 || n >= 0x0B99 && n <= 0x0B9A || n == 0x0B9C || n >= 0x0B9E && n <= 0x0B9F || n >= 0x0BA3 && n <= 0x0BA4 || n >= 0x0BA8 && n <= 0x0BAA || n >= 0x0BAE && n <= 0x0BB5 || n >= 0x0BB7 && n <= 0x0BB9 || n >= 0x0C05 && n <= 0x0C0C || n >= 0x0C0E && n <= 0x0C10 || n >= 0x0C12 && n <= 0x0C28 || n >= 0x0C2A && n <= 0x0C33 || n >= 0x0C35 && n <= 0x0C39 || n >= 0x0C60 && n <= 0x0C61 || n >= 0x0C85 && n <= 0x0C8C || n >= 0x0C8E && n <= 0x0C90 || n >= 0x0C92 && n <= 0x0CA8 || n >= 0x0CAA && n <= 0x0CB3 || n >= 0x0CB5 && n <= 0x0CB9 || n == 0x0CDE || n >= 0x0CE0 && n <= 0x0CE1 || n >= 0x0D05 && n <= 0x0D0C || n >= 0x0D0E && n <= 0x0D10 || n >= 0x0D12 && n <= 0x0D28 || n >= 0x0D2A && n <= 0x0D39 || n >= 0x0D60 && n <= 0x0D61 || n >= 0x0E01 && n <= 0x0E2E || n == 0x0E30 || n >= 0x0E32 && n <= 0x0E33 || n >= 0x0E40 && n <= 0x0E45 || n >= 0x0E81 && n <= 0x0E82 || n == 0x0E84 || n >= 0x0E87 && n <= 0x0E88 || n == 0x0E8A || n == 0x0E8D || n >= 0x0E94 && n <= 0x0E97 || n >= 0x0E99 && n <= 0x0E9F || n >= 0x0EA1 && n <= 0x0EA3 || n == 0x0EA5 || n == 0x0EA7 || n >= 0x0EAA && n <= 0x0EAB || n >= 0x0EAD && n <= 0x0EAE || n == 0x0EB0 || n >= 0x0EB2 && n <= 0x0EB3 || n == 0x0EBD || n >= 0x0EC0 && n <= 0x0EC4 || n >= 0x0F40 && n <= 0x0F47 || n >= 0x0F49 && n <= 0x0F69 || n >= 0x10A0 && n <= 0x10C5 || n >= 0x10D0 && n <= 0x10F6 || n == 0x1100 || n >= 0x1102 && n <= 0x1103 || n >= 0x1105 && n <= 0x1107 || n == 0x1109 || n >= 0x110B && n <= 0x110C || n >= 0x110E && n <= 0x1112 || n == 0x113C || n == 0x113E || n == 0x1140 || n == 0x114C || n == 0x114E || n == 0x1150 || n >= 0x1154 && n <= 0x1155 || n == 0x1159 || n >= 0x115F && n <= 0x1161 || n == 0x1163 || n == 0x1165 || n == 0x1167 || n == 0x1169 || n >= 0x116D && n <= 0x116E || n >= 0x1172 && n <= 0x1173 || n == 0x1175 || n == 0x119E || n == 0x11A8 || n == 0x11AB || n >= 0x11AE && n <= 0x11AF || n >= 0x11B7 && n <= 0x11B8 || n == 0x11BA || n >= 0x11BC && n <= 0x11C2 || n == 0x11EB || n == 0x11F0 || n == 0x11F9 || n >= 0x1E00 && n <= 0x1E9B || n >= 0x1EA0 && n <= 0x1EF9 || n >= 0x1F00 && n <= 0x1F15 || n >= 0x1F18 && n <= 0x1F1D || n >= 0x1F20 && n <= 0x1F45 || n >= 0x1F48 && n <= 0x1F4D || n >= 0x1F50 && n <= 0x1F57 || n == 0x1F59 || n == 0x1F5B || n == 0x1F5D || n >= 0x1F5F && n <= 0x1F7D || n >= 0x1F80 && n <= 0x1FB4 || n >= 0x1FB6 && n <= 0x1FBC || n == 0x1FBE || n >= 0x1FC2 && n <= 0x1FC4 || n >= 0x1FC6 && n <= 0x1FCC || n >= 0x1FD0 && n <= 0x1FD3 || n >= 0x1FD6 && n <= 0x1FDB || n >= 0x1FE0 && n <= 0x1FEC || n >= 0x1FF2 && n <= 0x1FF4 || n >= 0x1FF6 && n <= 0x1FFC || n == 0x2126 || n >= 0x212A && n <= 0x212B || n == 0x212E || n >= 0x2180 && n <= 0x2182 || n >= 0x3041 && n <= 0x3094 || n >= 0x30A1 && n <= 0x30FA || n >= 0x3105 && n <= 0x312C || n >= 0xAC00 && n <= 0xD7A3;
    }

    /**
     * Determines if the specified character matches the <em>Ideographic</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-Ideographic">Definition of Ideographic</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isIdeographic(char c) {
        int n = c;
        return n >= 0x4E00 && n <= 0x9FA5 || n == 0x3007 || n >= 0x3021 && n <= 0x3029;
    }

    /**
     * Determines if the specified character matches the <em>CombiningChar</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-CombiningChar">Definition of CombiningChar</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isCombiningChar(char c) {
        int n = c;
        return n >= 0x0300 && n <= 0x0345 || n >= 0x0360 && n <= 0x0361 || n >= 0x0483 && n <= 0x0486 || n >= 0x0591 && n <= 0x05A1 || n >= 0x05A3 && n <= 0x05B9 || n >= 0x05BB && n <= 0x05BD || n == 0x05BF || n >= 0x05C1 && n <= 0x05C2 || n == 0x05C4 || n >= 0x064B && n <= 0x0652 || n == 0x0670 || n >= 0x06D6 && n <= 0x06DC || n >= 0x06DD && n <= 0x06DF || n >= 0x06E0 && n <= 0x06E4 || n >= 0x06E7 && n <= 0x06E8 || n >= 0x06EA && n <= 0x06ED || n >= 0x0901 && n <= 0x0903 || n == 0x093C || n >= 0x093E && n <= 0x094C || n == 0x094D || n >= 0x0951 && n <= 0x0954 || n >= 0x0962 && n <= 0x0963 || n >= 0x0981 && n <= 0x0983 || n == 0x09BC || n == 0x09BE || n == 0x09BF || n >= 0x09C0 && n <= 0x09C4 || n >= 0x09C7 && n <= 0x09C8 || n >= 0x09CB && n <= 0x09CD || n == 0x09D7 || n >= 0x09E2 && n <= 0x09E3 || n == 0x0A02 || n == 0x0A3C || n == 0x0A3E || n == 0x0A3F || n >= 0x0A40 && n <= 0x0A42 || n >= 0x0A47 && n <= 0x0A48 || n >= 0x0A4B && n <= 0x0A4D || n >= 0x0A70 && n <= 0x0A71 || n >= 0x0A81 && n <= 0x0A83 || n == 0x0ABC || n >= 0x0ABE && n <= 0x0AC5 || n >= 0x0AC7 && n <= 0x0AC9 || n >= 0x0ACB && n <= 0x0ACD || n >= 0x0B01 && n <= 0x0B03 || n == 0x0B3C || n >= 0x0B3E && n <= 0x0B43 || n >= 0x0B47 && n <= 0x0B48 || n >= 0x0B4B && n <= 0x0B4D || n >= 0x0B56 && n <= 0x0B57 || n >= 0x0B82 && n <= 0x0B83 || n >= 0x0BBE && n <= 0x0BC2 || n >= 0x0BC6 && n <= 0x0BC8 || n >= 0x0BCA && n <= 0x0BCD || n == 0x0BD7 || n >= 0x0C01 && n <= 0x0C03 || n >= 0x0C3E && n <= 0x0C44 || n >= 0x0C46 && n <= 0x0C48 || n >= 0x0C4A && n <= 0x0C4D || n >= 0x0C55 && n <= 0x0C56 || n >= 0x0C82 && n <= 0x0C83 || n >= 0x0CBE && n <= 0x0CC4 || n >= 0x0CC6 && n <= 0x0CC8 || n >= 0x0CCA && n <= 0x0CCD || n >= 0x0CD5 && n <= 0x0CD6 || n >= 0x0D02 && n <= 0x0D03 || n >= 0x0D3E && n <= 0x0D43 || n >= 0x0D46 && n <= 0x0D48 || n >= 0x0D4A && n <= 0x0D4D || n == 0x0D57 || n == 0x0E31 || n >= 0x0E34 && n <= 0x0E3A || n >= 0x0E47 && n <= 0x0E4E || n == 0x0EB1 || n >= 0x0EB4 && n <= 0x0EB9 || n >= 0x0EBB && n <= 0x0EBC || n >= 0x0EC8 && n <= 0x0ECD || n >= 0x0F18 && n <= 0x0F19 || n == 0x0F35 || n == 0x0F37 || n == 0x0F39 || n == 0x0F3E || n == 0x0F3F || n >= 0x0F71 && n <= 0x0F84 || n >= 0x0F86 && n <= 0x0F8B || n >= 0x0F90 && n <= 0x0F95 || n == 0x0F97 || n >= 0x0F99 && n <= 0x0FAD || n >= 0x0FB1 && n <= 0x0FB7 || n == 0x0FB9 || n >= 0x20D0 && n <= 0x20DC || n == 0x20E1 || n >= 0x302A && n <= 0x302F || n == 0x3099 || n == 0x309A;
    }

    /**
     * Determines if the specified character matches the <em>Digit</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-Digit">Definition of Digit</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isDigit(char c) {
        int n = c;
        return n >= 0x0030 && n <= 0x0039 || n >= 0x0660 && n <= 0x0669 || n >= 0x06F0 && n <= 0x06F9 || n >= 0x0966 && n <= 0x096F || n >= 0x09E6 && n <= 0x09EF || n >= 0x0A66 && n <= 0x0A6F || n >= 0x0AE6 && n <= 0x0AEF || n >= 0x0B66 && n <= 0x0B6F || n >= 0x0BE7 && n <= 0x0BEF || n >= 0x0C66 && n <= 0x0C6F || n >= 0x0CE6 && n <= 0x0CEF || n >= 0x0D66 && n <= 0x0D6F || n >= 0x0E50 && n <= 0x0E59 || n >= 0x0ED0 && n <= 0x0ED9 || n >= 0x0F20 && n <= 0x0F29;
    }

    /**
     * Determines if the specified character matches the <em>Extender</em> production.
     * See: <a href="http://www.w3.org/TR/REC-xml#NT-Extender">Definition of Extender</a>.
     * 
     * @param c the character to check.
     * @return <code>true</code> if the character matches the production, or <code>false</code> if
     *         it does not.
     */
    private static final boolean isExtender(char c) {
        int n = c;
        return n == 0x00B7 || n == 0x02D0 || n == 0x02D1 || n == 0x0387 || n == 0x0640 || n == 0x0E46 || n == 0x0EC6 || n == 0x3005 || n >= 0x3031 && n <= 0x3035 || n >= 0x309D && n <= 0x309E || n >= 0x30FC && n <= 0x30FE;
    }

    /**
     * Constructs a new <code>XMLChecker</code> object. This constructor is
     * private since no objects of this class should be created.
     */
    private XMLChecker() {
        // empty
    }
}
