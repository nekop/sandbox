package sandbox;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakRefGC {

    public static void main(String[] args) throws Exception {
        WeakRefGC target = new WeakRefGC();
        ReferenceQueue queue = new ReferenceQueue();
        WeakReference ref = new WeakReference(target, queue);

        target = null;

        System.out.println(ref.get());
        System.gc();
        System.out.println(ref.get());
        System.out.println("Released?: " + ref.isEnqueued());
   }
}
