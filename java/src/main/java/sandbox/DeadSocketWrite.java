package sandbox;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.String;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class DeadSocketWrite {

    public static void main(String[] args) throws Exception {
        new Server().start();
        new Client().start();
    }

    static class Server extends Thread {
        public Server() { super("Server"); }
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(54444);
                Socket s = ss.accept();
                System.out.println("accepted");
                Thread.sleep(Integer.MAX_VALUE);
            } catch (Exception ignore) { }
        }
    }

    static class Client extends Thread {
        public Client() { super("Client"); }
        public void run() {
            try {
                Socket s = new Socket("localhost", 54444);
                s.setSoTimeout(1000);
                BufferedOutputStream out =
                    new BufferedOutputStream(s.getOutputStream());
                byte[] bytes = new byte[1024 * 1024];
                Arrays.fill(bytes, (byte)1);
                int i = 0;
                while (true) {
                    System.out.println(i);
                    out.write(bytes);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
