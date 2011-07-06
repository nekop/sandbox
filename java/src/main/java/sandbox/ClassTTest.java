package sandbox;

public class ClassTTest {
    public static void main(String[] args) throws Exception {
        Object o = "s";
        String s = get(o, String.class);
        System.out.println(s);
    }

    public static <T> T get(Object o, Class<T> expectedReturnType) {
        return expectedReturnType.cast(o);
    }
}
