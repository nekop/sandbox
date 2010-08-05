package sandbox;

public class FullGC {
    public static void main(String[] args) throws Exception {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) { }
            System.gc();
        }
    }
}
