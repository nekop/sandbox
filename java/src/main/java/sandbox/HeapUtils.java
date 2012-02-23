package sandbox;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class HeapUtils {
    public static void dumpHeap(String filename) throws Exception {
        boolean liveOnly = true;
        dumpHeap(filename, liveOnly);
    }

    public static void dumpHeap(String filename, boolean liveOnly) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name =
            ObjectName.getInstance("com.sun.management:type=HotSpotDiagnostic");
        String operation = "dumpHeap";
        Object[] params = { filename, liveOnly };
        String[] sig = { String.class.getName(), boolean.class.getName() };
        server.invoke(name, operation, params, sig);
    }
}
