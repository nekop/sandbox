package sandbox;

public class ThreadStackSize {
    public static void main(String[] args) {
        try {
        for (int i = 0; i < 1000; i++) {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException ignore) { }
                }
            }.start();
            if (i % 10 == 0) {
                System.out.println(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignore) { }
            }
        }
        } catch (Throwable t) { t.printStackTrace(); System.exit(-1); }
        
    }
}
