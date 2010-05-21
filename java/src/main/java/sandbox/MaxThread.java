package sandbox;

public class MaxThread {
    public static void main(String[] args) throws Exception {
        int threads = 600;
        try {
            threads = Integer.parseInt(args[0]);
        } catch (Exception ignore) { }
        for (int i = 0; i < threads; i++) {
            new SleepThread().start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) { }
        System.out.println("OK");
        System.exit(0);
    }

    private static class SleepThread extends Thread {
        public void run() {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignore) { }
        }
    }
}
