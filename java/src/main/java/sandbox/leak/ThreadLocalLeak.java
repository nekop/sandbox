package sandbox.leak;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;

public class ThreadLocalLeak {
    private ThreadLocal tl = new ThreadLocal();

    public void add(Object key, Object value) {
        tl.set(new KeyValuePair(key, value));
    }

    public Object getKey() {
        return ((KeyValuePair)tl.get()).key;
    }

    public Object getValue() {
        return ((KeyValuePair)tl.get()).value;
    }

    private class KeyValuePair {
        public Object key;
        public Object value;
        public KeyValuePair(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadLocalLeak target = new ThreadLocalLeak();
        target.add("foo", "bar");
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference ref = new PhantomReference(target, queue);
        target = null;
        System.gc();
        System.out.println("Phantom Queued: " + ref.isEnqueued());
   }
}
