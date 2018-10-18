package com.yihuan.sharecalendar.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.yihuan.sharecalendar.modle.bean.JPushBean;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Ronny on 2017/12/13.
 */

public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action == null)return;

        if(action.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){//todo 通知消息
            Bundle extras = intent.getExtras();
            String message = extras.getString(JPushInterface.EXTRA_MESSAGE);//message字段
            String json = extras.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json数据，键值对
            String title = extras.getString(JPushInterface.EXTRA_TITLE);


        }else if(action.equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {//todo 自定义消息
            Bundle extras = intent.getExtras();
            String message = extras.getString(JPushInterface.EXTRA_MESSAGE);//message字段
            String json = extras.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json数据，键值对
            String title = extras.getString(JPushInterface.EXTRA_TITLE);

        }else if(action.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {//todo 点击通知消息的处理
            Bundle extras = intent.getExtras();
            String message = extras.getString(JPushInterface.EXTRA_MESSAGE);//message字段
            String json = extras.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json数据，键值对
            JPushBean jPushBean = new Gson().fromJson(json, JPushBean.class);
            if(jPushBean != null){
                String type = jPushBean.getType();
                String activityId = jPushBean.getActivityId();
                if(type == null ){
                    Intent in = new Intent(context, MainActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                    return;
                }

                switch (type){
                    case "activity":
                        Intent i1 = new Intent(context, ActiveDetailsActivity_Receiver.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i1.putExtra("activeId", Integer.parseInt(activityId));
                        context.startActivity(i1);
                        break;
                }
            }
        }

    }
}
