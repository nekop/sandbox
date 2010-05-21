package sandbox;

import java.util.HashMap;
import java.util.Random;

public class ConcurrentHashMapAccess {
    public static void main(String[] args) {
        new TestThread().start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignore) { }
        new TestThread().start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignore) { }
        new TestThread().start();
        
    }
    static class TestThread extends Thread {
        private static HashMap<Object, Object> map = new HashMap<Object, Object>();
        private Random random = new Random();
        public void run() {
            int count = 0;
            String key = nextRandomString();
            while (true) {
                count++;
                map.put(key, "foo");
                if (map.get(key) == null) {
                    System.out.println(count + ":" + map);
                    throw new RuntimeException();
                }
                map.remove(key);
            }
        }
        private String nextRandomString() {
            return
                Thread.currentThread().getName() + ":" +
                random.nextInt() + ":" +
                System.currentTimeMillis();
        }
    }
        
}
