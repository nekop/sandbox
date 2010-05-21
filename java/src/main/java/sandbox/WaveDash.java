package sandbox;

public class WaveDash {

    public static final String WAVE_DASH = "\u301c";
    public static final String FULLWIDTH_TILDE = "\uff5e";

    public static void main(String[] args) throws Exception {
        roundtrip(WAVE_DASH, "Windows-31J", "Windows-31J");
        roundtrip(WAVE_DASH, "Shift_JIS", "Shift_JIS");
        roundtrip(WAVE_DASH, "Windows-31J", "Shift_JIS");
        roundtrip(WAVE_DASH, "Shift_JIS", "Windows-31J");
        roundtrip(FULLWIDTH_TILDE, "Windows-31J", "Windows-31J");
        roundtrip(FULLWIDTH_TILDE, "Shift_JIS", "Shift_JIS");
        roundtrip(FULLWIDTH_TILDE, "Windows-31J", "Shift_JIS");
        roundtrip(FULLWIDTH_TILDE, "Shift_JIS", "Windows-31J");
    }

    public static String roundtrip(String target, String encode, String decode) throws Exception {
        String result = new String(target.getBytes(encode), decode);
        System.out.printf("%s -> %s -> %s -> %s", toName(target), encode, decode, toName(result));
        System.out.println();
        return result;
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
