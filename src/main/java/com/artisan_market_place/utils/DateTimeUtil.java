package com.artisan_market_place.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static Date getCurrentUTCDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long currentMs = cal.getTimeInMillis();
        long offSetMs = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
        long gmtMs = currentMs - offSetMs;
        return new Date(gmtMs);
    }
}
