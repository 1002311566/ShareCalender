package com.yihuan.sharecalendar.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.utils.LogUtils;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Ronny on 2018/1/9.
 */

public class AlarmManagerUtil {

    /**
     * 设置闹钟
     *
     * @param context
     * @param timeInMillis
     * @param intent
     */
    public static void setAlarmTime(Context context, int id, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, id,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
            am.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, timeInMillis, sender);
        }
    }

    /**
     * 取消闹钟
     *
     * @param context
     * @param id
     */
    public static void cancelAlarm(Context context, int id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }


    public static void updateAlarm(Context context,Alarm alarm) {
        //todo 先取消闹钟
        cancelAlarm(context, alarm.getId());
        //todo 添加闹钟
        addRemind(context, alarm);
    }


    /**
     * 添加提醒
     *
     * @param alarm1
     * @param context
     */
    public static void addRemind(Context context, Alarm alarm1) {
        if(alarm1 == null)return;

        long timeLong = 0;
        Alarm alarm = null;
        //todo 根据类型做不同的处理
        boolean autoRemind = alarm1.isAutoRemind();
        if(autoRemind){
            alarm = AlarmTimeComputerUtils.initRemindAlarm(alarm1);
            timeLong = AlarmTimeComputerUtils.getRemindTimeMillis(alarm);
        }else{
            alarm = AlarmTimeComputerUtils.initActiveAlarm(alarm1);
            timeLong = AlarmTimeComputerUtils.getActiveTimeMillis(alarm);
        }

        LogUtils.e("---- timelong="+timeLong);
        if(timeLong == 0)return;//当时间戳为0 也过滤掉

        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_ALARM_BEAN, alarm);
        intent.putExtra("bundle", bundle);
        setAlarmTime(context, alarm.getId(), timeLong, intent);

        LogUtils.e("----- -- - -- - - -- - 闹钟时间:"+ new DateTime(new Date(timeLong)).toString("yyyy-MM-dd HH:mm")+"---- timelong="+timeLong);
    }



    //todo 重新设置所有闹钟
    public static void resetAll(Context context, List<ActiveBean> list) {
        if (list == null) {
            LogUtils.e(" - - - - - -  重置闹钟失败，查询数据为空- - - - - - ");
            return;
        }
        LogUtils.e(" - - - - - -  开始" + list.size() + "重设闹钟- - - - - - ");
        int i = 0;
        for (ActiveBean b : list) {
            addRemind(context, new Alarm(b));
            LogUtils.e(" - - - - - -  设置" + (++i) + "个闹钟- - - - - - ");
        }
        LogUtils.e(" 结束- - - - - -  设置" + i + "个闹钟- - - - - - ");

    }
}
