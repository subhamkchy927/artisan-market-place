package com.artisan_market_place.utils;

import com.artisan_market_place.constants.ApplicationConstants;

import java.text.SimpleDateFormat;
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

    public static String formateDate(Date date, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String formatedDate = formater.format(date);
        return formatedDate;
    }
}
