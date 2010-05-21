package sandbox;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

class URLClassLoaderSample {
    public static void main(String[] args) throws Exception {
        URL[] urls = new URL[] { new URL("jar:file:///home/nekop/jboss/lib/jboss-common.jar!/") };

        ClassLoader parent = ClassLoader.getSystemClassLoader();
        URLClassLoader cl1 = new URLClassLoader(urls, parent);
        URLClassLoader cl2 = new URLClassLoader(urls, parent);
            
        Class c1 = cl1.loadClass("org.jboss.util.property.PropertyMap");
        Class c2 = cl2.loadClass("org.jboss.util.property.PropertyMap");
        System.out.println(c1.isAssignableFrom(c2));
        System.out.println(c1.isInstance(c2.newInstance()));
    }
}
