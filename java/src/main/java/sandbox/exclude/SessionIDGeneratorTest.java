package sandbox;

import org.jboss.web.tomcat.service.session.SessionIDGenerator;
import java.util.Random;
import java.security.MessageDigest;
import java.util.Arrays;

// JBOSS_HOME=/path/to/jboss
// javac -classpath .:$JBOSS_HOME/lib/jboss-common.jar:$JBOSS_HOME/server/default/deploy/jboss-web.deployer/jbossweb-service.jar SessionIDGeneratorTest.java && java -classpath .:$JBOSS_HOME/lib/jboss-common.jar:$JBOSS_HOME/server/default/deploy/jboss-web.deployer/jbossweb-service.jar SessionIDGeneratorTest
public class SessionIDGeneratorTest {
    public static void main(String[] args) {
        SessionIDGenerator instance = new SessionIDGenerator();
        // byte[] bytes = new byte[128];
        // System.out.println(Arrays.toString(bytes));
        // instance.getRandom().nextBytes(bytes);
        // System.out.println(Arrays.toString(bytes));
        // bytes = instance.getDigest().digest(bytes);
        // System.out.println(Arrays.toString(bytes));
        // System.out.println(bytes.length);
        // System.out.println(instance.encode(bytes));

        //for (int i = 0; i < Integer.MAX_VALUE; i++) {
        for (int i = 0; i < 1000; i++) {
            String id = removeLastTwoAsterisks(instance.getSessionId());
            System.out.println(id);
            if (id.indexOf("*") != -1) {
                System.out.println(id);
            }
        }
    }

    static String removeLastTwoAsterisks(String id) {
        if (id.endsWith("**")) {
            return id.substring(0, id.length() -2);
        }
        return id;
    }

    static class SessionIDGenerator2 extends SessionIDGenerator {
        public MessageDigest getDigest() {
            return super.getDigest();
        }
        public Random getRandom() {
            return super.getRandom();
        }
        public String encode(byte[] data) {
            return super.encode(data);
        }
    }
    static class CustomSessionIDGenerator extends SessionIDGenerator {
        protected final static int SESSION_ID_BYTES = 24; // SessionIDGenerator uses 16, this class uses 18
        protected synchronized String generateSessionId() {
            if (this.digest == null) {
                this.digest = getDigest();
            }
            if (this.random == null) {
                this.random = getRandom();
            }
            byte[] bytes = new byte[SESSION_ID_BYTES];
            this.random.nextBytes(bytes);
            bytes = this.digest.digest(bytes);
            System.out.println(bytes.length);
            return encode(bytes);
        }
    }
}
