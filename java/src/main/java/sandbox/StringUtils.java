package sandbox;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;

public class StringUtils {

    public static String format(String format, Object... args) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format(format, args);
        return sb.toString();
    }
    
    public static String toUnicodeEscape(String s) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            formatter.format("\\u%04X", (int)chars[i]);
        }
        return sb.toString();
    }
    
    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    public static String truncateByByteLength(String s, int byteLength, String encoding) throws UnsupportedCharsetException, CharacterCodingException {
        if (s == null || s.length() == 0) {
            return s;
        }
        Charset charset = Charset.forName(encoding);
        CharsetEncoder encoder = charset.newEncoder();
        // return if it's obviously short enough
        if (encoder.maxBytesPerChar() * s.length() <= byteLength) {
            return s;
        }
        // encode it with limited "out" max bytes
        CharBuffer in = CharBuffer.wrap(s);
		ByteBuffer out = ByteBuffer.allocate(byteLength);
		encoder.encode(in, out, true);
        // get chars from CharBuffer between 0 to current position
        // out is not used here, it's just used as a limit of encode() 
        return in.flip().toString();
    }

    public static void main(String[] args) {
        String s = "\uD842\uDF9F"; // surrogate pair
        System.out.println(toUnicodeEscape("こんにちは"));
        System.out.println(toUnicodeEscape(s));
    }

}
