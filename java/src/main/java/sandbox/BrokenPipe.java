package sandbox;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;

public class BrokenPipe {
    public static void main(String[] args) throws Exception {
        new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(33322);
                    ss.accept().close();
                } catch (Exception ignore) {
                }
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) { }
        Socket s = new Socket("localhost", 33322);
        OutputStream out = s.getOutputStream();
        for (int i = 0; i < 1024; i++) {
            System.out.println(i);
            out.write(i);
        }
        out.flush();
        s.close();
    }
}
