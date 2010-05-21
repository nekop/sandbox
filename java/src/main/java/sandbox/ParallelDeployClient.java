package sandbox;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.net.URL;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import javax.naming.InitialContext;

/*
#!/bin/sh

JBOSS_HOME=/path/to/jboss
CLASSPATH=.
CLASSPATH=$CLASSPATH:$JBOSS_HOME/client/jbossall-client.jar

javac ParallelDeployClient.java
ls war/* | java -classpath $CLASSPATH ParallelDeployClient

*/
public class ParallelDeployClient {
    public static void main(String[] args) throws Exception {
        new ParallelDeployClient().execute();
    }

    public void execute() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        long start = System.currentTimeMillis();
        BufferedReader reader = null;
        try {
            reader =
                new BufferedReader(
                    new InputStreamReader(System.in, "UTF-8"));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                File file = new File(line);
                URL url = file.toURI().toURL();
                System.out.println(url);
                executor.execute(new SingleDeploy(url));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                executor.shutdown();
                reader.close();
            } catch (Exception ignore) { }
        }
        executor.awaitTermination(20, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.printf("FINISHED: (%d ms)", end-start);
        System.out.println();
    }

    class SingleDeploy implements Runnable {
        URL url;

        public SingleDeploy(URL url) {
            this.url = url;
        }

        public void run() {
            try {
                InitialContext context = getInitialContext();
                MBeanServerConnection server =
                    (MBeanServerConnection)context.lookup("jmx/invoker/RMIAdaptor");
                String mainDeployer = "jboss.system:service=MainDeployer";
                ObjectName oname = new ObjectName(mainDeployer);
                String operationName = "redeploy";
                Object[] param = new Object[] { url };
                String[] signature = new String[] { URL.class.getName() };
                long start = System.currentTimeMillis();
                System.out.println("Deploy: " + url.toString());
                server.invoke(oname, operationName, param, signature);
                long end = System.currentTimeMillis();
                System.out.printf("End (%d ms): %s", end-start, url.toString());
                System.out.println();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        protected InitialContext getInitialContext() throws Exception {
            Properties props = new Properties();
            // props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
            //           "org.jnp.interfaces.NamingContextFactory");
            props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                      "org.jboss.security.jndi.JndiLoginInitialContextFactory");
            props.put(javax.naming.Context.URL_PKG_PREFIXES,
                      "jboss.naming:org.jnp.interfaces");
            props.put(javax.naming.Context.PROVIDER_URL, "localhost:1099");
            props.put(javax.naming.Context.SECURITY_PRINCIPAL, "admin");
            props.put(javax.naming.Context.SECURITY_CREDENTIALS, "admin");
            InitialContext context = new InitialContext(props);
            return context;
        }
    }
}
