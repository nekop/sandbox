package sandbox;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AllocateDirectOOME {
    public static void main(String[] args) throws Exception {
        List list = new ArrayList();
        // list.add(ByteBuffer.allocateDirect(Integer.MAX_VALUE));
        // list.add(ByteBuffer.allocateDirect(Integer.MAX_VALUE));
        // list.add(ByteBuffer.allocateDirect(Integer.MAX_VALUE));
        // list.add(ByteBuffer.allocateDirect(Integer.MAX_VALUE));
        list.add(new char[Integer.MAX_VALUE]);
        list.add(new char[Integer.MAX_VALUE]);
        list.add(new char[Integer.MAX_VALUE]);
        list.add(new char[Integer.MAX_VALUE]);
    }
}
