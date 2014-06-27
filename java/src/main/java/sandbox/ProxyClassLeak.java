package sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyClassLeak {
    interface Foo {}
    static class NopInvocationHandler implements InvocationHandler {
        public Object invoke(Object proxy,
                             Method method,
                             Object[] args) throws Throwable {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            Proxy.newProxyInstance(null,
                                   new Class[] {Cloneable.class},
                                   new NopInvocationHandler());
            Proxy.newProxyInstance(Foo.class.getClassLoader(),
                                   new Class[] {Foo.class},
                                   new NopInvocationHandler());
        }
    }
}
