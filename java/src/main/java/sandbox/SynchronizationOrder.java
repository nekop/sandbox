package sandbox;

public class SynchronizationOrder {

    private Object monitor = new Object();
    private boolean flag = true;

    public static void main(String[] args) throws Exception {
        new SynchronizationOrder().start();
    }

    public void start() throws Exception {
        new WaitThread().start();
        new NotifyThread().start();
        new FlipThread().start();
    }

    private class NotifyThread extends Thread {
        public void run() {
            synchronized (monitor) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) { }
                System.out.println("notifyAll()");
                flag = false;
                monitor.notifyAll();
            }
        }
    }
    private class FlipThread extends Thread {
        public void run() {
            synchronized (monitor) {
                flag = true;
            }
        }
    }
    private class WaitThread extends Thread {
        public void run() {
            synchronized (monitor) {
                try {
                    if (flag) {
                        System.out.println("wait() start");
                        monitor.wait();
                        System.out.println("wait() end");
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println(flag);
            }
        }
    }
}
