package com.yihuan.sharecalendar.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.utils.LogUtils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Ronny on 2018/1/9.
 * 被触发提醒的广播
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, AlarmActivity.class);
        context.startActivity(intent);

        LogUtils.e(TAG, "- - -- - - -- - -- -onReceive - - -- - - -- - - - -");
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle == null) return;
        Alarm alarm = bundle.getParcelable(Constants.INTENT_ALARM_BEAN);
        if (alarm == null) return;
        LogUtils.e(TAG, "title-------" + alarm.toString());

        long timeLong = 0;
        //todo 根据类型做不同的处理
        boolean autoRemind = alarm.isAutoRemind();
        if (autoRemind) {
            timeLong = AlarmTimeComputerUtils.getRemindTimeMillis(alarm);
        } else {
            timeLong = AlarmTimeComputerUtils.getActiveTimeMillis(alarm);
        }
        LogUtils.e("---- timelong="+timeLong);

        if (timeLong == 0) return;//当时间戳为0 也过滤掉
        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("bundle", bundle);
        // todo 再次设置闹钟
        AlarmManagerUtil.setAlarmTime(context, alarm.getId(), timeLong, i);
        LogUtils.e("----- -- - -- - - -- - 闹钟时间:"+ new DateTime(new Date(timeLong)).toString("yyyy-MM-dd HH:mm")+"---- timelong="+timeLong);
    }
}
