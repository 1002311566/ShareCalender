package com.yihuan.sharecalendar.ui.view.calendar.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

public class CalendarUtil {

    /**
     * 根据ViewPager position 得到对应年月
     *
     * @param position
     * @return
     */
    public static int[] positionToDate(int position, int startY, int startM) {
        int year = position / 12 + startY;
        int month = position % 12 + startM;

        if (month > 12) {
            month = month % 12;
            year = year + 1;
        }

        return new int[]{year, month};
    }

    /**
     * 根据年月得到ViewPager position
     *
     * @param year
     * @param month
     * @return
     */
    public static int dateToPosition(int year, int month, int startY, int startM) {
        return (year - startY) * 12 + month - startM;
    }

    public static int[] strToArray(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArray = str.split("\\.");
            int[] result = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                result[i] = Integer.valueOf(strArray[i]);
            }
            return result;
        }
        return null;
    }

    public static int getPxSize(Context context, int size) {
        return size * context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getTextSize1(Context context, int size) {
        return (int) (size * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int getTextSize(Context context, int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, context.getResources().getDisplayMetrics());

    }
}
