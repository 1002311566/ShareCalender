package com.yihuan.sharecalendar.alarm;

import android.content.Context;
import android.content.Intent;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.global.Constants;

/**
 * Created by Ronny on 2018/1/20.
 */

public class AlarmSendBroadcastUtils {
    //0 初始化 1 添加 2 删除 3 修改
    /**
     * 初始化闹钟
     * @param context
     */
    public static void sendInitAllActiveAlarm(Context context){
        Intent intent = new Intent(Constants.ACTION_ACTIVE_ALARM);
        intent.putExtra("type", 0);
        context.sendBroadcast(intent);
    }

    /**
     * 删除某个闹钟
     * @param context
     * @param alarm
     */
    public static void sendDeleteActiveAlarm(Context context, Alarm alarm){
        Intent intent = new Intent(Constants.ACTION_ACTIVE_ALARM);
        intent.putExtra("type",2);
        intent.putExtra(Constants.INTENT_ALARM_BEAN, alarm);
        context.sendBroadcast(intent);
    }

    /**
     * 删除一组闹钟
     * @param context
     * @param ids
     */
    public static void sendDeleteAlarms(Context context, int[] ids){
        Intent intent = new Intent(Constants.ACTION_ACTIVE_ALARM);
        intent.putExtra("type",2);
        intent.putExtra(Constants.INTENT_ALARM_IDS, ids);
        context.sendBroadcast(intent);
    }

    /***
     * 添加一个闹钟
     * @param context
     * @param alarm
     */
    public static void sendAddAlarm(Context context, Alarm alarm){
        Intent intent = new Intent(Constants.ACTION_ACTIVE_ALARM);
        intent.putExtra("type",1);
        intent.putExtra(Constants.INTENT_ALARM_BEAN, alarm);
        context.sendBroadcast(intent);
    }

    /**
     * 更改一个闹钟
     * @param context
     * @param alarm
     */
    public static void sendUpdateActiveAlarm(Context context, Alarm alarm){
        Intent intent = new Intent(Constants.ACTION_ACTIVE_ALARM);
        intent.putExtra("type",3);
        intent.putExtra(Constants.INTENT_ALARM_BEAN, alarm);
        context.sendBroadcast(intent);
    }
}
