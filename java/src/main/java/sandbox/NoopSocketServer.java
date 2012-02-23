package sandbox;

import java.net.ServerSocket;
import java.net.Socket;

public class NoopSocketServer {

    public static void main(String[] args) throws Exception {
        int port = 9999;
        if (args.length != 0) {
            try {
                port = Integer.parseInt(args[0]);  
            } catch (NumberFormatException ignore) { }
        }
        new Server(port).start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    static class Server extends Thread {
        ServerSocket ss;
        public Server(int port) throws Exception {
            super("Server");
            ss = new ServerSocket(port);
        }
        public void run() {
            while (true) {
                try {
                    Socket s = ss.accept();
                    System.out.println("accepted");
                } catch (Exception ignore) { }
            }
        }
    }
}
