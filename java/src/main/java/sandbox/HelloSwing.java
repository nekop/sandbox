package sandbox;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloSwing {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel("こんにちは"));
        frame.pack();
        frame.show();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) { }
        System.exit(0);
    }
}
