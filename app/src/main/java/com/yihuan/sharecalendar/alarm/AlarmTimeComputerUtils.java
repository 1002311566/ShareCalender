package com.yihuan.sharecalendar.alarm;


import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.utils.BeanToUtils;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ronny on 2018/1/20.
 * 闹钟时间计算类
 */

public class AlarmTimeComputerUtils {

    /**
     * 初始化活动闹钟
     *
     * @param alarm
     * @return
     */
    public static Alarm initActiveAlarm(Alarm alarm) {
        if (alarm == null) return null;

        long start_time = alarm.getStart_time();
        long end_time = alarm.getEnd_time();
        long current = System.currentTimeMillis();
        if (current > end_time) return null;//将无用闹钟过滤

        long[] planMinuteList = alarm.getPlanMinuteList();//提前时间点
        if (planMinuteList != null && planMinuteList.length > 0) {
            planMinuteList = ifRepeat(planMinuteList);
            //todo 将计划提醒时间倒序排序
            quickSort(planMinuteList, 0, planMinuteList.length-1);
            long[] times = new long[planMinuteList.length];
            for (int i = 0; i< planMinuteList.length; i++) {
                long m = planMinuteList[i];
                long t = start_time - m * 60 * 1000;
                times[i] = t;
            }
            alarm.setPlanMinuteList(times);// 将计算的时间戳集合重新组装
        }

        return alarm;
    }

    /**
     * 活动时间戳，排过序的集合
     *
     * @param alarm
     * @return
     */
    public static long getActiveTimeMillis(Alarm alarm) {
        if(alarm == null)return 0;
        long current = System.currentTimeMillis();
        long[] planMinuteList = alarm.getPlanMinuteList();//这是排过序的集合
        for (long lt : planMinuteList) {
            if (lt >= current) {
                return lt;
            }
        }
        return 0;
    }

    /**
     * 初始化提醒闹钟
     *
     * @param alarm
     * @return
     */
    public static Alarm initRemindAlarm(Alarm alarm) {
        if (alarm == null) return null;

        long start_time = alarm.getStart_time();
        long end_time = alarm.getEnd_time();
        long current = System.currentTimeMillis();
        if (current > end_time) return null;//闹铃时间已过期

        //todo 将提醒时间点进行毫秒转换
        List<String> hourAndMinuteList = alarm.getHourAndMinuteList();
        long[] hmLong = new long[hourAndMinuteList.size()];
        if (hourAndMinuteList != null && hourAndMinuteList.size() > 0) {
            for (int i = 0; i< hourAndMinuteList.size(); i++) {
                String hm = hourAndMinuteList.get(i);
                if (hm.contains(":")) {
                    String[] split = hm.split(":");
                    long hour = Long.parseLong(split[0]) * 60 * 60 * 1000;
                    long minute = Long.parseLong(split[1]) * 60 * 1000;
                    long ti = hour + minute;
                    hmLong[i] = ti;
                }
            }
            hmLong = ifRepeat(hmLong);
            Arrays.sort(hmLong);
            alarm.setPlanMinuteList(hmLong);
        }
        return alarm;
    }

    /**
     * 提醒时间戳，排过序的集合
     *
     * @param alarm
     * @return
     */
    public static long getRemindTimeMillis(Alarm alarm) {
        if (alarm == null) return 0;
        long start_time = alarm.getStart_time();
        long end_time = alarm.getEnd_time();
        long current = System.currentTimeMillis();
        if (current > end_time) return 0;//闹铃时间已过期

        String dayType = alarm.getDayType();// 1 周 2 月
        String days = alarm.getDays();
        if (dayType != null && dayType.equals("2")) {//todo ------------------------月---------------------
            List<Integer> months = BeanToUtils.parseRemindDays(days);
            Collections.sort(months);
            if(months == null || (months != null && months.size() <= 0))return 0;//没有填写日则不提醒
            long[] planMinuteList = alarm.getPlanMinuteList();
            if (planMinuteList == null || (planMinuteList != null && planMinuteList.length <= 0))
                return 0;//没有选择提醒时间则不提醒
            DateTime currentTime = new DateTime(new Date(current));
            int currentDay = currentTime.getDayOfMonth() - 1;
            for (int i = 0; i< months.size(); i++){
                if(months.get(i).intValue() >= currentDay){
                    int millisOfDay = currentTime.getMillisOfDay();
                    int daySize = months.get(i).intValue() - currentDay;//这个月内今天之后的提醒
                    if(daySize > 0){//todo 如果这个日期不是今天，取这个日期 + 第一个提醒时间点
                        return getIntTime(current) + planMinuteList[0] + daySize * 24 * 60 * 60 * 1000;
                    }
                    //todo 这里是当天有提醒，选择现在之后的时间
                    for (long pl : planMinuteList) {
                        if (pl >= millisOfDay) {//todo 找到大于当前时间的时间点
                            return getIntTime(current) + pl;
                        }
                    }
                }
                //todo 当执行到这里，说明当天已没有符合的时间点。继续选择下一个日期
                if (i == months.size() - 1) {//todo 这里要考虑循环的最后一个，当是最后一个时,取下一月第一个
                    int firstDay = months.get(0).intValue();
                    int lastDay = months.get(months.size() - 1).intValue();
                    int daySize = currentTime.dayOfMonth().getMaximumValue() - currentDay + firstDay;//todo 间隔天数
                    long nextLong = getIntTime(current) + (daySize * 24 * 60 * 60 * 1000) + planMinuteList[0];
                    if (nextLong > end_time) return 0;
                    return nextLong;
                }
            }


        } else {//todo -----------------------周-----------------------------
            List<Integer> week = BeanToUtils.parseRemindDays(days);
            if (week == null || (week != null && week.size() <= 0)) return 0;//没有选择星期则不提醒
            long[] planMinuteList = alarm.getPlanMinuteList();
            if (planMinuteList == null || (planMinuteList != null && planMinuteList.length <= 0))
                return 0;//没有选择提醒时间则不提醒

            DateTime currentTime = new DateTime(new Date(current));
            int currentWeek = currentTime.getDayOfWeek() - 1;//datetime 取得是1-7，week 是0-6
            for (int i = 0; i < week.size(); i++) {
                if (week.get(i).intValue() >= currentWeek) {//todo 说明今天有提醒
                    int millisOfDay = currentTime.getMillisOfDay();
                    int daySize = week.get(i).intValue() -currentWeek;
                    if(daySize > 0){//todo 如果下个提醒日期不是今天，取下个日期 + 第一个提醒时间点
                        return getIntTime(current) + planMinuteList[0] + daySize * 24 * 60 * 60 * 1000;
                    }
                    //todo 下个提醒日期就是今天
                    for (long pl : planMinuteList) {
                        if (pl >= millisOfDay) {//todo 找到大于当前时间的时间点
                            return getIntTime(current) + pl;
                        }
                    }
                }
                //todo 当执行到这里，说明当天已没有符合的时间点。继续选择下一个日期
                if (i == week.size() - 1) {//todo 这里要考虑循环的最后一个，当是最后一个时,取下一周第一个
                    Integer firstWeek = week.get(0);
                    Integer lastWeek = week.get(week.size() - 1);
                    int daySize = 7 - currentWeek + firstWeek;//todo 间隔天数
                    long nextLong = getIntTime(current) + (daySize * 24 * 60 * 60 * 1000) + planMinuteList[0];
                    if (nextLong > end_time) return 0;
                    return nextLong;
                }
            }
        }
        return 0;
    }

    /**
     * 获取整点 00:00
     *
     * @param current
     * @return
     */
    private static long getIntTime(long current) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(current);
        instance.set(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH),
                0, 0);
        return instance.getTimeInMillis();
    }

    /**
     * 去除重复元素
     *
     * @param arr
     * @return
     */
    public static long[] ifRepeat(long[] arr) {
        //用来记录去除重复之后的数组长度和给临时数组作为下标索引
        int t = 0;
        //临时数组
        long[] tempArr = new long[arr.length];
        //遍历原数组
        for (int i = 0; i < arr.length; i++) {
            //声明一个标记，并每次重置
            boolean isTrue = true;
            //内层循环将原数组的元素逐个对比
            for (int j = i + 1; j < arr.length; j++) {
                //如果发现有重复元素，改变标记状态并结束当次内层循环
                if (arr[i] == arr[j]) {
                    isTrue = false;
                    break;
                }
            }
            //判断标记是否被改变，如果没被改变就是没有重复元素
            if (isTrue) {
                //没有元素就将原数组的元素赋给临时数组
                tempArr[t] = arr[i];
                //走到这里证明当前元素没有重复，那么记录自增
                t++;
            }
        }
        //声明需要返回的数组，这个才是去重后的数组
        long[] newArr = new long[t];
        //用arraycopy方法将刚才去重的数组拷贝到新数组并返回
        System.arraycopy(tempArr, 0, newArr, 0, t);
        return newArr;
    }

    /**
     * 降序快拍
     * @param arr
     * @param high 头
     * @param low 尾
     */
    public static  void quickSort(long[] arr,int high,int low){
        int i,j;
        long temp;
        i=high;//高端下标
        j=low;//低端下标
        temp=arr[i];//取第一个元素为标准元素。

        while(i<j){//递归出口是 low>=high
            while(i<j&&temp>arr[j])//后端比temp小，符合降序，不管它，low下标前移
                j--;//while完后指比temp大的那个
            if(i<j){
                arr[i]=arr[j];
                i++;
            }
            while(i<j&&temp<arr[i])
                i++;
            if(i<j){
                arr[j]=arr[i];
                j--;
            }
        }//while完，即第一盘排序
        arr[i]=temp;//把temp值放到它该在的位置。

        if(high<i) //注意，下标值
            quickSort(arr,high,i-1);//对左端子数组递归
        if(i<low)  //注意，下标值
            quickSort(arr,i+1,low);//对右端子数组递归

    }
}
