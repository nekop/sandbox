package sandbox;

public class Deadlock {
    public static void main(String[] args) {
        final Object o1 = new Object();
        final Object o2 = new Object();
        new Thread() {
            public void run() {
                synchronized (o1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) { }
                    synchronized (o2) {
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                synchronized (o2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) { }
                    synchronized (o1) {
                    }
                }
            }
        }.start();
    }
}
