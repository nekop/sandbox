package sandbox;

public class MaxThread {
    public static void main(String[] args) throws Exception {
        int threads = 4000;
        try {
            threads = Integer.parseInt(args[0]);
        } catch (Exception ignore) { }
        for (int i = 0; i < threads; i++) {
            new SleepThread(i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) { }
        System.out.println("OK");
        System.gc();
        System.exit(0);
    }

    private static class SleepThread extends Thread {
        int i;
        public SleepThread(int i) {
            this.i = i;
        }
        public void run() {
            if (i % 10 == 0) {
                System.out.println(i);
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignore) { }
        }
    }
}
