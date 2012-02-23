package sandbox;

import java.util.Map;
import java.util.HashMap;

public class Overload<K, V> {

    private String privField = "this is private!";
    
    public static void main(String[] args) {
        new Overload<String, Object>().foo();
        new Overload<String, Object>().put("foo", 1);
        new Overload<String, Object>().put(new HashMap<String, Object>() {{put("foo", 1);}});
    }
    public void foo() {
        System.out.println("foo!");
    }

    public void put(K key, V value) {
        System.out.println(key + ":" + value);
    }

    public void put(Map<K, V> map) {
        System.out.println(map);
    }

    public static Object getInstance() {
        System.out.println("This is static");
        return new Overload<Object, Object>();
    }
}
