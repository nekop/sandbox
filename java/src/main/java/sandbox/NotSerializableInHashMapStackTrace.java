package sandbox;

import java.util.HashMap;

public class NotSerializableInHashMapStackTrace {
    public static void main(String[] args) throws Exception {
        try {
            HashMap map = new HashMap();
            map.put("foo", new Object());
            ObjectSerialize.serialize(map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            HashMap map = new HashMap();
            Parent p = new Parent();
            p.child = new Object();
            map.put("foo", p);
            ObjectSerialize.serialize(map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            map2.put("foo", new Object());
            map.put("foo", map2);
            ObjectSerialize.serialize(map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static class Parent implements java.io.Serializable {
        public Object child;
    }
    
}
