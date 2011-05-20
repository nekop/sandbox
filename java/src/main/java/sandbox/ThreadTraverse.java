package sandbox;

public class ThreadTraverse {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread());
        System.out.println(Thread.currentThread().getThreadGroup());
        System.out.println(Thread.currentThread().getThreadGroup().activeCount());
        System.out.println(Thread.currentThread().getThreadGroup().getParent());
        System.out.println(Thread.currentThread().getThreadGroup().getParent().activeCount());
        System.out.println(Thread.currentThread().getThreadGroup().getParent().getParent());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) { }
        

    }
}
