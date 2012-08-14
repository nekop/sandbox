package sandbox;

import java.util.Formatter;
import java.math.BigInteger;

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
        // Should we do this? I'm not sure.
        // for (int i = 0; i < s.length(); i++) {
        //     formatter.format("\\u%04X", Character.codePointAt(s, i));
        // }
        return sb.toString();
    }
    
    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    public static void main(String[] args) {
        String s = "\uD842\uDF9F"; // surrogate pair
        System.out.println(toUnicodeEscape("こんにちは"));
        System.out.println(toUnicodeEscape(s));
    }
}
