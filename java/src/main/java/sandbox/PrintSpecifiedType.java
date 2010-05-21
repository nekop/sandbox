package sandbox;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PrintSpecifiedType<E> {

    public static void main(String[] args) throws Exception {
        class Foo {
            private PrintSpecifiedType<String> bar;
        }

        Field field = Foo.class.getDeclaredField("bar");
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        Object o = type.getActualTypeArguments()[0];
        System.out.println(o);
        System.out.println(String.class.equals(o));
    }

}
