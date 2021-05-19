package jmu.shijh.community_system.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Times {
    public static final Long SEC = 1000L;
    public static final Long MIN = SEC * 60;
    public static final Long HOUR = MIN * 60;
    public static final Long DAY = HOUR * 24;
    public static final Long WEEK = DAY * 7;

    public static Date now() {
        return now(0);
    }

    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static Date sub(Date d1, Date d2) {
        return new Date(d1.getTime() - d2.getTime());
    }

    public static synchronized Date now(long after) {
        return new Date(System.currentTimeMillis() + after);
    }
}
