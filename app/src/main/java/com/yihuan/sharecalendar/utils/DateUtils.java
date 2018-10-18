package com.yihuan.sharecalendar.utils;

import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Ronny on 2018/1/10.
 */

public class DateUtils {

    public static int gapDay(TimeBean startTime, TimeBean endTime) {
        if (startTime == null) return 0;

        DateTime start = new DateTime(startTime.getYear(), startTime.getMonth() + 1, startTime.getDay(), 0, 0);
        DateTime end = null;
        if (endTime == null) {
            end = new DateTime(TimeUtils.getCurrentYear(), TimeUtils.getCurrentMonth(), TimeUtils.getCurrentDay(), 0, 0);
        } else {
            end = new DateTime(endTime.getYear(), endTime.getMonth() + 1, endTime.getDay(), 0, 0);
        }
        Days days = Days.daysBetween(start, end);
//        int f = startTime.toYMD().compareTo(endTime.toYMD()) > 0 ? -1 : 1;
        return days.getDays();
    }
}
