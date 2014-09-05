package sandbox;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(56543);
        while (true) {
            Socket sock = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            while (true) {
                try {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    bw.write(line + "\n");
                    bw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            sock.close();
        }
    }
}
