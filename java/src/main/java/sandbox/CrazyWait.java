package sandbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

public class CrazyWait {
    public static final int THREAD_NUM = 400;
    public static final int MONITOR_TIMEOUT = 1;
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
        WaitTask<Void> task = new WaitTask<Void>();
        for (int i = 0; i < THREAD_NUM; i++) {
            executor.submit(task);
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignore) { }
        System.out.println("**********" + task.count.get());
        System.exit(0);
    }

    static class WaitTask<V> implements Callable<V> {
        public AtomicLong count = new AtomicLong();
        public V call() {
            Object monitor = new Object();
            synchronized (monitor) {
                while (true) {
                    try {
                        monitor.wait(CrazyWait.MONITOR_TIMEOUT);
                        count.incrementAndGet();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
            
}
