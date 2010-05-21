package sandbox;

import java.util.Hashtable;

public class HashtableLeak {
    private static Hashtable table = new Hashtable();

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            table.put("TSThread:" + i, i);
            if (i % 2000 == 0) {
                System.gc();
                System.out.println(i + ":" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            }
        }
    }
}
