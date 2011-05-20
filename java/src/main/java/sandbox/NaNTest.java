package sandbox;

public class NaNTest {
    public static void main(String[] args) throws Exception {
        System.out.println(Double.NaN == Double.NaN);
        System.out.println(Double.NaN > Double.POSITIVE_INFINITY);
        System.out.println(Double.NaN < Double.POSITIVE_INFINITY);
        System.out.println(Double.NaN > Double.NEGATIVE_INFINITY);
        System.out.println(Double.NaN < Double.NEGATIVE_INFINITY);
        System.out.println(Double.compare(Double.NaN, Double.POSITIVE_INFINITY));
        System.out.println(Double.compare(Double.NaN, Double.NEGATIVE_INFINITY));
        System.out.println(Double.compare(Double.NaN, 1.0d));
    }
}
