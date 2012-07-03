package sandbox;

public class Wait {
    public static void main(String[] args) throws Exception {
        Object o = new Object();
        synchronized (o) {
            o.wait();
        }
    }
}
