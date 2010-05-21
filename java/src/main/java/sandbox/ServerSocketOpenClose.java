package sandbox;

import java.net.ServerSocket;
import java.net.InetSocketAddress;

public class ServerSocketOpenClose {
    private static int errorOnBind = 0;
    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress("localhost", 10999);
        for (int i = 0; i < 50000; i++) {
            final ServerSocket ss = new ServerSocket();
            try {
                try {
                    ss.bind(address);
                } catch (Exception ignore) {
                    errorOnBind++;
                }
                new Thread() {
                    public void run() {
                        try {
                            ss.accept();
                        } catch (Exception ignore) { }
                    }
                }.start();
            } finally {
                ss.close();
            }
        }
        System.out.println("errorOnBind: "+ errorOnBind);
    }
}
