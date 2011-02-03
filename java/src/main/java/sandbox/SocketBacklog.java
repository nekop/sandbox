package sandbox;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketBacklog {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(33322, 1);
        for (int i = 0; i < 5; i++) {
            new Socket("localhost", 33322);
        }
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException ignore) { }
        

    }
}
