package sandbox;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemory {
    public static void main(String[] args) {
        List list = new ArrayList();
        while (true) {
            list.add(new byte[1024 * 1024]);
        }
    }
}
