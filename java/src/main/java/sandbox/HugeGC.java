package sandbox;

import java.util.List;
import java.util.ArrayList;

public class HugeGC {

    private static List list = new ArrayList();

    public static void main(String[] args) throws Exception {
        System.gc();
        for (int i = 0; i < 100; i++) {
            byte[] data1g = new byte[1024 * 1024 * 1024];
            list.add(data1g);
        }
        System.gc();
    }
}
