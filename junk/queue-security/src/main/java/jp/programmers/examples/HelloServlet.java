package jp.programmers.examples;

import java.io.IOException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/plain");
        System.out.println(req.isUserInRole("guest"));
        System.out.println(req.getUserPrincipal());
        Connection conn = null;
        try {
            InitialContext context = new InitialContext();
            //ConnectionFactory cf = (ConnectionFactory)context.lookup("java:JmsXA");
            ConnectionFactory cf = (ConnectionFactory)context.lookup("java:ConnectionFactory");
            Destination destination = (Destination)context.lookup("queue/testQueue");
            conn = cf.createConnection();
            boolean transacted = false;
            Session sess = conn.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = sess.createProducer(destination);
            producer.send(sess.createTextMessage("hello"));
        } catch (Exception ex) {
            
        } finally {
            try {
                conn.close();
            } catch (Exception ignore) {
                
            }
        }
        System.out.println(req.getUserPrincipal());
        res.getWriter().println("Hello world!");
    }

    public void onMessage(Message m) {
        try {
            System.out.println(m);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
