package sandbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Pid {
    public static void main(String[] args) {
        System.out.println(Pid.get());
    }

    /**
     * Get pid of this java process. Returns -1 if it fails.
     */
    public static int get() {
        String[] cmd = {"bash", "-c", "echo $PPID"};
        Process p = null;
        BufferedReader reader = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            reader =
                new BufferedReader(
                    new InputStreamReader(p.getInputStream(), "UTF-8"));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                try {
                    return Integer.parseInt(line);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ignore) { }
            p.destroy();
        }
        return -1;
    }
}
