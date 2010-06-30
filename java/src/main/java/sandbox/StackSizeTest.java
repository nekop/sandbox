package sandbox;

public class StackSizeTest {
    private static int count = 0;
    public static void main(String[] args) {
        try {
            recur();
        } catch (Throwable t) {
            System.out.println(count);
        }
    }
    public static void recur() {
        count++;
        recur();
    }
}
