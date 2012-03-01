package sandbox;

// null
// ctor done
// static done
public class StaticNewInstance {
    private static StaticNewInstance instance = new StaticNewInstance();
    private static Object o = new Object();
    static {
        System.out.println("static done");
    }
    public StaticNewInstance() {
        System.out.println(o);
        System.out.println("ctor done");
    }
    public static void main(String[] args) {
    }
}
