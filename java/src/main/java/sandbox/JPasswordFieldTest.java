package sandbox;

import java.awt.*;
import javax.swing.*;

public class JPasswordFieldTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JTextField(30));
        frame.getContentPane().add(new JPasswordField(30));
        frame.pack();
        frame.show();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) { }
        System.exit(0);
        
    }
    
}
