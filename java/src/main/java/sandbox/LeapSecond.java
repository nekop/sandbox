package sandbox;

import java.util.Date;
import java.text.SimpleDateFormat;

public class LeapSecond {
    public static void main(String[] args) throws Exception {
        String pattern = "yyyy-MM-dd HH:mm:ss z";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date58 = sdf.parse("2008-12-31 23:59:58 UTC");
        Date date59 = sdf.parse("2008-12-31 23:59:59 UTC");
        Date date60 = sdf.parse("2008-12-31 23:59:60 UTC");
        Date date61 = sdf.parse("2008-12-31 23:59:61 UTC");
        System.out.println(date58);
        System.out.println(date58.getTime());
        System.out.println(sdf.format(date58));
        System.out.println(date59);
        System.out.println(date59.getTime());
        System.out.println(sdf.format(date59));
        System.out.println(date60);
        System.out.println(date60.getTime());
        System.out.println(sdf.format(date60));
        System.out.println(date61);
        System.out.println(date61.getTime());
        System.out.println(sdf.format(date61));
    }
}
