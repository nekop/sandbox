package sandbox;

public class Mojibake {

    public static final String STRING = "こんにちはこんにちはぼくはまちちゃん";

    public static void main(String[] args) throws Exception {
        String s = null;
        s = roundtrip(STRING, "UTF-8", "UTF-8");

        /* non-UTF-8 bytes mojibake cannot be restored */
        s = roundtrip(STRING, "ISO-8859-1", "UTF-8");
        roundtrip(s, "UTF-8", "ISO-8859-1");
        s = roundtrip(STRING, "Shift_JIS", "UTF-8");
        roundtrip(s, "UTF-8", "Shift_JIS");
        s = roundtrip(STRING, "EUC-JP", "UTF-8");
        roundtrip(s, "UTF-8", "EUC-JP");

        /* UTF-8 bytes mojibake could be restored */
        s = roundtrip(STRING, "UTF-8", "ISO-8859-1");
        roundtrip(s, "ISO-8859-1", "UTF-8"); // we can restore!
        s = roundtrip(STRING, "UTF-8", "Shift_JIS");
        roundtrip(s, "Shift_JIS", "UTF-8"); // almost, but not perfect
        s = roundtrip(STRING, "UTF-8", "EUC-JP");
        roundtrip(s, "EUC-JP", "UTF-8"); // You'll get ????????

    }

    public static String roundtrip(String target, String encode, String decode) throws Exception {
        String result = new String(target.getBytes(encode), decode);
        System.out.printf("%s -> %s -> %s -> %s", target, encode, decode, result);
        System.out.println();
        return result;
    }
}
