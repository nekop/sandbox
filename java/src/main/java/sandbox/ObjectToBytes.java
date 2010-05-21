package sandbox;

import java.util.*;
import java.io.*;

public class ObjectToBytes {
    public static void main(String[] args) throws Exception {
        Object target = "hoge";
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(target);
        oos.flush();
        oos.close();
        byte[] result = baos.toByteArray();
        result = new byte[] {-84, -19, 0, 5, 116, 0, 4, 104, 111, 103, 101};
        System.out.println(result);
        System.out.println(Arrays.toString(result));
    }
}
