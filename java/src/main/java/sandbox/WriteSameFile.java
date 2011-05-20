package sandbox;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class WriteSameFile {
    public static void main(String[] args) throws Exception {
        final File f = new File("WriteSameFile.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        new Thread() {
            public void run() {
                try {
                    OutputStream out = new FileOutputStream(f);
                    out.write('a');
                    out.flush();
                    Thread.sleep(20000);
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) { }
        OutputStream out = new FileOutputStream(f);
        out.write('b');
        out.close();
    }
}
