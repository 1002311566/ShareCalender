package com.yihuan.sharecalendar.alarm.live;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yihuan.sharecalendar.alarm.AlarmService;
import com.yihuan.sharecalendar.alarm.LocalService;

/**
 * Created by Ronny on 2018/1/17.
 */

public class StartBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 启动应用即开启服务
        context.startService(new Intent(context, AlarmService.class));
        context.startService(new Intent(context, LocalService.class));
    }
}
