package jp.programmers.examples.ejb3.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.MessageDrivenContext;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;

public class HelloDDMDB implements MessageListener {
    @Resource MessageDrivenContext context;
    public void onMessage(Message message) {
        System.out.println("HelloDDMDB#onMessage(Message)");
        if (message == null) {
           System.out.println("message is null");
            return;
        }
        String s = message.toString();
        String text = null;
        if (message instanceof TextMessage) {
            try {
                text = ((TextMessage)message).getText();
                s += ": text=" + text;
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(s);

        // sleep if long value is passed
        try {
            long sleep = Long.parseLong(text);
            Thread.sleep(sleep);
        } catch (Exception ignore) { }

        // raise exception if requested
        if (text.equalsIgnoreCase("exception")) {
            throw new RuntimeException("Exception requested.");
        }

        // call setRollbackOnly()
        if (text.equalsIgnoreCase("rollback")) {
            context.setRollbackOnly();
        }

    }
}
