package sandbox;

public class BusyWait {
    public static void main(String[] args) throws Exception {
        long msec = Long.MAX_VALUE;
        if (args.length != 0) {
            try {
                msec = Long.parseLong(args[0]);
            } catch (Exception ignore) { }
        }
        Object monitor = new Object();
        synchronized(monitor) {
            while(true) monitor.wait(msec);
        }
    }
}
