package sandbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerialize {

    public static Object deserialize(byte[] bytes) {
        Object result = null;
        try {
            ObjectInputStream oos =
                new ObjectInputStream(new ByteArrayInputStream(bytes));
            result = oos.readObject();
            oos.close();
        } catch (Exception ignore) { }
        return result;
    }

    public static byte[] serialize(Object o) {
        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();
            result = baos.toByteArray();
            oos.close();
        } catch (Exception ignore) { }
        return result;
    }

    public static Object deepClone(Object o) {
        return deserialize(serialize(o));
    }

}
