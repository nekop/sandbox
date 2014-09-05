package sandbox;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Socket sock = new Socket("localhost", 56543);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        for (int i = 0; i < 100000; i++) {
            bw.write("abc\n");
            bw.flush();
            String line = br.readLine();
            if (line == null) {
                break;
            }
        }
        sock.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
