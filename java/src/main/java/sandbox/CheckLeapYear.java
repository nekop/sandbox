package sandbox;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class CheckLeapYear {
    public static void main(String[] args) throws Exception {
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date d = df.parse("2012/02/29");
            System.out.println(d);
            System.out.println(d.getTime());
            System.out.println(d.getTime() - new Date().getTime());
        }

        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date d = df.parse("1995/12/31 23:59:60");
            System.out.println(d);
            System.out.println(d.getTime());
        }
    }
}
