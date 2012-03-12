/*
 * groovy -classpath $JBOSS_HOME/client/jbossall-client.jar jms_client.groovy
 */

providerURL = "localhost:1099";
connectionFactoryJNDIName = "ConnectionFactory";
destinationName = "queue/testQueue";
clientID = "jms_client";
isSender = java.util.Arrays.asList(args).contains("-s");

message = "hello";
appendCountToMessage = true;
count = 2000;
sendInterval = 0;
receiveWait = 5000;

class ExceptionListener implements javax.jms.ExceptionListener {
    private javax.jms.Connection conn = null;
    public ExceptionListener(javax.jms.Connection conn) {
        this.conn = conn;
    }
    public void onException(javax.jms.JMSException ex) {
        println("ExceptionListener#onException(): " + Thread.currentThread().getName());
        ex.printStackTrace();
        try {
            conn.close();
        } catch (Exception ignore) { }
    }
}

run = {
    props = new java.util.Properties();
    props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
              "org.jnp.interfaces.NamingContextFactory");
    props.put(javax.naming.Context.URL_PKG_PREFIXES,
              "jboss.naming:org.jnp.interfaces");
    props.put(javax.naming.Context.PROVIDER_URL, providerURL);
    context = new javax.naming.InitialContext(props);
    cf = context.lookup(connectionFactoryJNDIName);
    destination = context.lookup(destinationName);

    conn = cf.createConnection();
    try {
        conn.setClientID(clientID);
        conn.setExceptionListener(new ExceptionListener(conn));
        session =
            conn.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        if (isSender) {
            producer = session.createProducer(destination);
            for (int i = 0; i < count; i++) {
                System.out.println("send:" + i);
                if (appendCountToMessage) {
                    producer.send(session.createTextMessage(message + i));
                } else {
                    producer.send(session.createTextMessage(message));
                }
                if (sendInterval > 0) {
                    Thread.sleep(sendInterval);
                }
            }
            producer.close();
        } else {
            conn.start();
            consumer = session.createConsumer(destination);
            for (int i = 0; i < count; i++) {
                System.out.println("receive:" + i);
                message = consumer.receive(receiveWait);
                println(message);
            }
            consumer.close();
        }
        session.close();
    } finally {
        try {
            conn.close();
        } catch (Exception ignore) { }
    }
}
run.call();

