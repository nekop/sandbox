providerURL = "localhost:1099";
connectionFactoryJNDIName = "ConnectionFactory";
destinationType = "queue";
destinationName = "queue/testQueue";
//destinationType = "topic";
//destinationName = "topic/testTopic";
if (java.util.Arrays.asList(args).contains("-s")) {
  isSender = true;
} else {
  isSender = false;
}
message = "hello";
appendCountToMessage = false;
count = 30;
sendInterval = 1000;
receiveWait = 5000;

loop = 1;
loopInterval = 0;

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
    if (destinationType.equals("queue")) {
        conn = cf.createQueueConnection();
        try {
            conn.setClientID("hoge");
            conn.setExceptionListener(new ExceptionListener(conn));
            conn.start();
            session = 
                conn.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            if (isSender) {
                sender = session.createSender(destination);
                for (int i = 0; i < count; i++) {
                    System.out.println("send:" + i);
                    if (appendCountToMessage) {
                        sender.send(session.createTextMessage(message + i));
                    } else {
                        sender.send(session.createTextMessage(message));
                    }
                    if (sendInterval > 0) {
                        Thread.sleep(sendInterval);
                    }
                }
            } else {
                receiver = session.createReceiver(destination);
                for (int i = 0; i < count; i++) {
                    System.out.println("receive:" + i);
                    message = receiver.receive(receiveWait);
                    println(message);
                }
                receiver.close();
            }
            session.close();
        } finally {
            try {
                println("Sleep 30 sec before close connection");
                Thread.sleep(30000);
                conn.close();
                println("Sleep 30 sec after close connection");
                Thread.sleep(30000);
            } catch (Exception ignore) { }
        }
    } else if (destinationType.equals("topic")) {
        conn = cf.createTopicConnection();
        try {
            conn.setExceptionListener(new ExceptionListener(conn));
            conn.start();
            session = 
                conn.createTopicSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
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
                subscriber = session.createSubscriber(destination);
                for (int i = 0; i < count; i++) { 
                    System.out.println("receive:" + i);
                    message = subscriber.receive(receiveWait);
                    println(message);
                }
                subscriber.close();
            }
            session.close();
        } finally {
            try {
                conn.close();
            } catch (Exception ignore) { }
        }
    }
}
run.call();

//1.times {
//Thread.start {
//for (int i = 0; i < loop; i++) {
//    System.out.println("loop:" + i);
//    try {
//        run.call();
//    } catch (Exception ignore) { }
//    if (loopInterval > 0) {
//        Thread.sleep(loopInterval);
//    }
//}
//Thread.sleep(10);
//}
//}
