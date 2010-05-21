package sandbox;

// https://jira.jboss.org/browse/JBMESSAGING-1682
public class DuplicateID {
    public static void main(String[] args) throws Exception {
        long nodeId = 1;
        long now = System.currentTimeMillis();
        short count = (short)(Short.MAX_VALUE - 2);
        long id1 = nodeId << 54;
        long id2 = now << 14;
        System.out.println(Long.toBinaryString(id1 | id2 | count));
        System.out.println(Long.toBinaryString(id1 | id2 | count + 1));
        System.out.println(Long.toBinaryString(id1 | id2 | count + 2));
        System.out.println(Long.toBinaryString(id1 | id2 | count + 3));
        System.out.println(Long.toBinaryString(id1 | id2 | count + 4));
    }
}
