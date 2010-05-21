package sandbox;

public class DualLock implements Runnable {
    private Object lockobj1, lockobj2;
    public DualLock(Object o1, Object o2){
        lockobj1 = o1; lockobj2 = o2;
    }
    public void run(){ lock1(); }
    private void lock1(){
        synchronized(lockobj1){ lock2(); }
    }
    private void lock2(){
        synchronized(lockobj2){
            try { lockobj2.wait(); }catch(InterruptedException e){}
        }
    }
    public static void main(String[] args){
        Object o1 = new Object();
        Object o2 = new Object();
        new Thread(new DualLock(o1, o2), "Thread1").start();
        new Thread(new DualLock(o1, o2), "Thread2").start();
    }
} 
