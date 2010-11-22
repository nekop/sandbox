package sandbox;

public class SynchronizedNull {
    public static void main(String[] args) throws Exception {
        Object o = null;
        // Yes, it's NullPointerException
        synchronized (o) {
            return;
        }
    }
}
