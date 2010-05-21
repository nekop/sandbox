package sandbox;

import java.io.File;

// Test to confirm deleteOnExit() works with the addShutdownHook()/halt() methods.
// - No, this doesn't work. The file remains on the disk.
public class DeleteOnExitTest {
    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Runtime.getRuntime().halt(0);
            }
        });
        File file = new File("DeleteOnExitTest.tmp");
        file.createNewFile();
        file.deleteOnExit();
        // Runtime.getRuntime().halt(0);
        System.out.println("sleep for 20 sec, press Ctrl+c");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignore) { }
    }
}
