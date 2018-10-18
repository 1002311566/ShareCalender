package com.yihuan.sharecalendar.modle.data;

import android.content.res.TypedArray;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.ui.view.calendar.CalendarUtils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ronny on 2017/11/5.
 * 提供数据
 */

public class TimeUtils {

    public static final int MAX_YEAR_COUNT = 50;//可选择的年


    /**
     * 取得 从今年开始往后推30年
     *
     * @return
     */
    public static List<Integer> getYearData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        ArrayList<Integer> list = new ArrayList<>();

        //后推100年
        for (int i = 0; i < MAX_YEAR_COUNT; i++) {
            list.add(year + i);
        }
        //前推100年
        for(int i= MAX_YEAR_COUNT; i> 0; i--){
            list.add(year - i );
        }
        return list;
    }

    /**
     * 取得 12个月
     *
     * @return
     */
    public static List<Integer> getMonthData() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 取得农历月
     */
    public static List<Object> getLunarMonth() {
        ArrayList<Object> list = new ArrayList<>();
        String[] strs = {"一月","二月", "三月", "四月","五月","六月", "七月","八月","九月", "十月","十一月", "十二月"};
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }
        return list;
    }

    public static List<Object> getLunarDay() {
        ArrayList<Object> list = new ArrayList<>();
        String[] strs = {"初一","初二", "初三", "初四","初五","初六", "初七","初八","初九", "初十","十一", "十二",
                "十三","十四", "十五", "十六","十七","十八", "十九","二十","廿一", "廿二", "廿三","廿四","廿五","廿六",
                "廿七","廿八","廿九", "三十"};
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }
        return list;
    }

    /**
     * 获取某年某月的所有日期
     *
     * @param year
     * @param month
     * @return
     */
    public static List<Integer> getDays(int year, int month) {
        int monthDays = CalendarUtils.getMonthDays(year, month);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= monthDays; i++) {
            list.add(i);
        }
        return list;
    }


    /**
     * 取得 24小时
     *
     * @return
     */
    public static List<Integer> getHourData() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 取得 60分钟
     *
     * @return
     */
    public static List<Integer> getMinuteData() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add(i);
        }
        return list;
    }

    //----------------------获取当前时间------------------------------------

    /**
     * 当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 当前日
     *
     * @return
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前小时
     *
     * @return
     */
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 当前分钟
     *
     * @return
     */
    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 当前星期
     *
     * @return
     */
    public static int getCurrentWeek() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 当前时间 yyyy.MM.dd hh:mm
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = simpleDateFormat.format(new Date());
        return time;
    }

    public static String getNextHourTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public static TimeBean getCurrentTimeBean() {
        int currentYear = getCurrentYear();
        int currentMonth = getCurrentMonth() - 1;
        int currentDay = getCurrentDay();
        int currentHour = getCurrentHour();
        int currentMinute = getCurrentMinute();
//        if(currentMinute  != 0 && currentHour != 23){
//            currentHour +=1;
//            currentMinute = 0;
//        }
        int currentWeek = getCurrentWeek();
        return new TimeBean(currentYear, currentMonth,
                currentDay, currentHour, currentMinute, currentWeek);
//        TimeBean timeBean = new TimeBean(DateTime.now().getMillis());
    }

    public static TimeBean getNextDayTimeBean(TimeBean startTimeBean) {
        TimeBean b = new TimeBean(startTimeBean.toTime());
        Calendar c = Calendar.getInstance();
        c.set(b.getYear(), b.getMonth(), b.getDay(), b.getHour(), b.getMinute());
        c.add(Calendar.DAY_OF_MONTH, 1);
        b.setDay(c.get(Calendar.DAY_OF_MONTH));
        return b;
    }

    public static TimeBean getNextHourTimeBean(TimeBean startTimeBean) {
        TimeBean b = new TimeBean(startTimeBean.toTime());
        Calendar c = Calendar.getInstance();
        c.set(b.getYear(), b.getMonth(), b.getDay(), b.getHour(), b.getMinute());
        c.add(Calendar.HOUR_OF_DAY, 1);
        b.setHour(c.get(Calendar.HOUR_OF_DAY));
        return b;
    }
//---------------------------------------------------------------------------

    public static int getCurrentYearPosition() {
        return 0;
    }

    public static int getYearPosition(int year) {
        List<Integer> yearData = getYearData();
        if(yearData.contains(year)){
            return yearData.indexOf(year);
        }
        return 0;
    }

    public static int getCurrentMonthPosition() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getMonthPosition(int month) {
        List<Integer> monthData = getMonthData();
        if(monthData.contains(month+1)){
            return monthData.indexOf(month+1);//todo 代码月比实际小1
        }
        return Calendar.getInstance().get(Calendar.MONTH);
    }


    public static int getCurrentDayPosition() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 2;
    }

    public static int getDayPosition(TimeBean timeBean) {
        if(timeBean == null)return 0;
        List<Integer> days = getDays(timeBean.getYear(), timeBean.getMonth());
        int pos = 0;
        if(days.contains(timeBean.getDay())){
            pos = days.indexOf(timeBean.getDay());
        }
        if(pos != -1){
            //todo 当天数是奇数时需要对中间以上数据做处理
            if(days.size() % 2 == 1){
                //todo 只有当当前日期不等于中间数+1时才减 : 比如 29天  则 15号（角标14）不能减
                if(timeBean.getDay() > days.size()/2+1){
                    return pos - 1;
                }
            }
            return pos;
        }
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 2;
    }

    public static int getCurrentHourPosition() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getHourPosition(int hour) {
        List<Integer> hourData = getHourData();
        int pos = 0;
        if(hourData.contains(hour)){
            pos = hourData.indexOf(hour);
        }
        if(pos != -1)return pos;
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinutePosition() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    public static int getMinutePosition(int minute) {
        List<Integer> minuteData = getMinuteData();
        int pos = 0;
        if(minuteData.contains(minute)){
            pos = minuteData.indexOf(minute);
        }
        if(pos != -1)return pos;
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getCurrentWeekPosition() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }


    //---------------------------获取心情图标id-----------------------
    public static int[] getFaceMood(int arrayId) {
        TypedArray ta = App.getInstanse().getResources().obtainTypedArray(arrayId);
        int[] ids = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            ids[i] = ta.getResourceId(i, 0);
        }
        return ids;
    }

    //--------------------------获取00:00 至 23:00-----------------------
    public static List<String> getDayTimes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("全天");
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                list.add("0" + i + ":00");
                continue;
            }
            list.add(i + ":00");
        }
        return list;
    }

    public static List<String> getCycle() {
        String[] peroidList = App.getInstanse().getResources().getStringArray(R.array.create_active_period);
        ArrayList<String> list = new ArrayList<>();
        for (String s : peroidList) {
            list.add(s);
        }
        return list;
    }

    public static List<String> getRemind() {
        String[] remindList = App.getInstanse().getResources().getStringArray(R.array.create_active_remind_time);
        ArrayList<String> list = new ArrayList<>();
        for (String s : remindList) {
            list.add(s);
        }
        return list;
    }

    public static long getNext50Year(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, 50);
        return instance.getTimeInMillis();
    }

    public static long getLast50Year(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, -50);
        return instance.getTimeInMillis();
    }
}
