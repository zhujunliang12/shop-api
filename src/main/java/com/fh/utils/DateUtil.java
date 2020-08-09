package com.fh.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String time(Date time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    public static String yyyyMMdd(Date time) {

        return new SimpleDateFormat("yyyyMMddHHmmss").format(time);
    }

    //当前时间  后面的 分钟
    public static Date addMinutes(Date date, int minutes) {
        Calendar instance = Calendar.getInstance( );
        instance.setTime(date);
        instance.add(Calendar.MINUTE, minutes);
        Date time = instance.getTime( );
        return time;
    }


}
