package sandbox;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JBoss4xJmxClient {

    public static void main(String[] args) throws Exception {
        InitialContext context = createInitialContext();
        MBeanServerConnection conn =
            (MBeanServerConnection)context.lookup("jmx/rmi/RMIAdaptor");
        ObjectName objectName = ObjectName.getInstance("jboss.system:type=ServerInfo");
        String attributeName = "JavaVMVersion";
        Object result = conn.getAttribute(objectName, attributeName);

        System.out.println(result);
    }

    private static InitialContext createInitialContext() throws Exception {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                  "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        props.put(Context.URL_PKG_PREFIXES,
                  "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "localhost:1099");
        props.put(Context.SECURITY_PRINCIPAL, "admin");
        props.put(Context.SECURITY_CREDENTIALS, "admin");
        return new InitialContext(props);
    }
}
