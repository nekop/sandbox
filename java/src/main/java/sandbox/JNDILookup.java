package sandbox;

import javax.naming.InitialContext;
import javax.naming.Context;
import java.util.Properties;

// JBOSS_HOME=/path/to/jboss
// javac -classpath .:$JBOSS_HOME/client/jbossall-client.jar JNDILookup.java && java -classpath .:$JBOSS_HOME/client/jbossall-client.jar JNDILookup
public class JNDILookup {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                  "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES,
                  "jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "localhost:1099");
        InitialContext context = new InitialContext(props);
        System.out.println(context.lookup("ConnectionFactory"));
    }
}
