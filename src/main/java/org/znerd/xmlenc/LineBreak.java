// See the COPYRIGHT.txt file for copyright and license information
package org.znerd.xmlenc;

/**
 * Enumeration type for line breaks.
 *
 * @since XMLenc 0.35
 */
public final class LineBreak {

    /**
     * Constructs a new <code>LineBreak</code> that consists of the specified characters.
     *
     * @param lineBreak the characters the line break consists of.
     */
    private LineBreak(String lineBreak) {
        _lineBreak = lineBreak;
        _lineBreakChars = lineBreak.toCharArray();
    }

    /**
     * The characters this line break consists of. This field is initialized by the constructor.
     */
    private final String _lineBreak;

    /**
     * A character array containing the characters this line break consists of. This field is
     * initialized by the constructor.
     */
    final char[] _lineBreakChars;

    /**
     * Empty line break. This is equivalent to using no line breaks.
     */
    public static final LineBreak NONE = new LineBreak("");

    /**
     * Unix and MacOS/X line break. This represents the string <code>"\n"</code>.
     */
    public static final LineBreak UNIX = new LineBreak("\n");

    /**
     * DOS and Windows line break. This represents the string <code>"\r\n"</code>.
     */
    public static final LineBreak DOS = new LineBreak("\r\n");

    /**
     * MacOS line break. This represents the string <code>"\r"</code>.
     * <p>
     * This applies to all MacOS versions before MacOS/X. Use {@link #UNIX} as the MacOS/X line
     * break.
     */
    public static final LineBreak MACOS = new LineBreak("\r");

    @Override
    public String toString() {
        return _lineBreak;
    }
}
