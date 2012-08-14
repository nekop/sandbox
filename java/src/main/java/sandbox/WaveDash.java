package sandbox;

import java.math.BigInteger;

public class WaveDash {
    // Shift_JIS WAVE_DASH is 0x8160
    // 0x3F is '?'
    public static final String WAVE_DASH = "\u301c";
    public static final String FULLWIDTH_TILDE = "\uff5e";

    public static void main(String[] args) throws Exception {
        roundtrip(WAVE_DASH, "Windows-31J", "Windows-31J");
        roundtrip(WAVE_DASH, "Shift_JIS", "Shift_JIS");
        roundtrip(WAVE_DASH, "Cp943c", "Cp943c");
        roundtrip(WAVE_DASH, "Windows-31J", "Shift_JIS");
        roundtrip(WAVE_DASH, "Windows-31J", "Cp943c");
        roundtrip(WAVE_DASH, "Shift_JIS", "Windows-31J");
        roundtrip(WAVE_DASH, "Shift_JIS", "Cp943c");
        roundtrip(WAVE_DASH, "Cp943c", "Windows-31J");
        roundtrip(WAVE_DASH, "Cp943c", "Shift_JIS");
        roundtrip(FULLWIDTH_TILDE, "Windows-31J", "Windows-31J");
        roundtrip(FULLWIDTH_TILDE, "Shift_JIS", "Shift_JIS");
        roundtrip(FULLWIDTH_TILDE, "Cp943c", "Cp943c");
        roundtrip(FULLWIDTH_TILDE, "Windows-31J", "Shift_JIS");
        roundtrip(FULLWIDTH_TILDE, "Windows-31J", "Cp943c");
        roundtrip(FULLWIDTH_TILDE, "Shift_JIS", "Windows-31J");
        roundtrip(FULLWIDTH_TILDE, "Shift_JIS", "Cp943c");
        roundtrip(FULLWIDTH_TILDE, "Cp943c", "Windows-31J");
        roundtrip(FULLWIDTH_TILDE, "Cp943c", "Shift_JIS");
        System.out.println(toHex("?".getBytes("Windows-31J")));
        System.out.println(toHex("?".getBytes("Shift_JIS")));
        System.out.println(toHex("?".getBytes("Cp943c")));
    }

    public static String roundtrip(String target, String encode, String decode) throws Exception {
        byte[] b = target.getBytes(encode);
        String result = new String(b, decode);
        System.out.printf("%15s -> %11s -> %6s -> %11s -> %15s", toName(target), encode, toHex(b), decode, toName(result));
        System.out.println();
        return result;
    }

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("0x%0" + (bytes.length << 1) + "X", bi);
    }

    public static String toName(String s) {
        if (s.equals(WAVE_DASH)) {
            return "WAVE_DASH";
        } else if (s.equals(FULLWIDTH_TILDE)) {
            return "FULLWIDTH_TILDE";
        }
        return s; // unknown
    }
}
