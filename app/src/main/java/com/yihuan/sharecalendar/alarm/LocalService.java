package com.yihuan.sharecalendar.alarm;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.aidl.IAlarmControl;
import com.yihuan.sharecalendar.alarm.live.ScreenBroadcastListener;
import com.yihuan.sharecalendar.alarm.live.ScreenManager;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2018/1/17.
 * 本地服务，用来接收来自本地activity的数据， 并操作远程服务
 * 同时兼具进程保活
 */

public class LocalService extends Service {
    public static final String TAG = "LocalService";
    private IAlarmControl mRemoteBinder;
//    private MyBinder myBinder;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteBinder = IAlarmControl.Stub.asInterface(service);
            initAllAlarm();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e(TAG, " - - - - - -  远程服务已断开- - - - - - ");
            mRemoteBinder = null;

            Intent intent1 = new Intent(LocalService.this, AlarmService.class);
            LocalService.this.startService(intent1);
            bindService(intent1, conn, Context.BIND_ABOVE_CLIENT);

        }
    };
    private SetAlarmBroadcast setAlarmBroadcast;


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG, " - - - - - -  onCreate- - - - - - ");
        Intent intent1 = new Intent(this, AlarmService.class);
        LocalService.this.startService(intent1);
        bindService(intent1, conn, Context.BIND_ABOVE_CLIENT);
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_ACTIVE_ALARM);//活动
        intentFilter.addAction(Constants.ACTION_REMIND_ALARM);//定制化提醒
        setAlarmBroadcast = new SetAlarmBroadcast();
        registerReceiver(setAlarmBroadcast, intentFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG, " - - - - - -  onStartCommand- - - - - - ");
//        initLiveActivity();
        return START_STICKY;
    }

    /**
     * 启动保活activity
     */
//    private void initLiveActivity() {
//        final ScreenManager manager = ScreenManager.getInstance(this);
//        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
//        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
//            @Override
//            public void onScreenOn() {
//                manager.finishActivity();
//            }
//
//            @Override
//            public void onScreenOff() {
//                manager.startActivity();
//            }
//        });
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e(TAG, "LocalService - - - - - -  onBind- - - - - - ");
//        if (myBinder == null) {
//            myBinder = new MyBinder();
//        }
//        return myBinder;
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "LocalService - - - - - -  onDestroy- - - - - - ");
        unbindService(conn);
        unregisterReceiver(setAlarmBroadcast);

        //todo 调用服务端进程，重新启动该服务
        try {
            if (mRemoteBinder != null) {
                mRemoteBinder.restart();
                LogUtils.e(TAG, "LocalService - - - - - -  restart- - - - - - ");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtils.e(TAG, "LocalService - - - - - -  RemoteException- - - - - - ");
        }
    }

    class MyBinder extends IAlarmControl.Stub {

        @Override
        public void initAllRemind(List<Alarm> list) throws RemoteException {

        }

        @Override
        public void addRemind(Alarm alarm) throws RemoteException {

        }

        @Override
        public void deleteRemind(Alarm alarm) throws RemoteException {

        }

        @Override
        public void deleteReminds(int[] idList) throws RemoteException {

        }

        @Override
        public void updateRemind(Alarm alarm) throws RemoteException {

        }

        @Override
        public void removeAllRemind() throws RemoteException {

        }

        @Override
        public void restart() throws RemoteException {
//            Intent intent = new Intent(LocalService.this, AlarmService.class);
//            LocalService.this.startService(intent);
//            LocalService.this.bindService(intent, conn, BIND_ABOVE_CLIENT);
        }
    }

    /**
     * 设置闹钟的广播接收器
     */
    class SetAlarmBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.hasExtra("type")) {//0 初始化 1 添加 2 删除 3 修改
                    int type = intent.getIntExtra("type", 0);
                    switch (type) {
                        case 0:
                            initAllAlarm();
                            break;
                        case 1:
                            addAlarm(intent);
                            break;
                        case 2:
                            deleteAlarm(intent);
                            break;
                        case 3:
                            updateAlarm(intent);
                            break;

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改闹钟
     *
     * @param intent
     */
    private void updateAlarm(Intent intent) throws RemoteException {
        Alarm alarm = intent.getParcelableExtra(Constants.INTENT_ALARM_BEAN);
        if (alarm != null && mRemoteBinder != null) {
            mRemoteBinder.updateRemind(alarm);
        }
    }

    /**
     * 添加闹钟
     *
     * @param intent
     */
    private void addAlarm(Intent intent) throws RemoteException {
        Alarm alarm = intent.getParcelableExtra(Constants.INTENT_ALARM_BEAN);
        if (alarm != null && mRemoteBinder != null) {
            mRemoteBinder.addRemind(alarm);
        }
    }

    /**
     * 删除闹钟
     *
     * @param intent
     */
    private void deleteAlarm(Intent intent) throws RemoteException {
        Alarm alarm = intent.getParcelableExtra(Constants.INTENT_ALARM_BEAN);
        if (alarm != null && mRemoteBinder != null) {
            mRemoteBinder.deleteRemind(alarm);
            return;
        }

        int[] ids = intent.getIntArrayExtra(Constants.INTENT_ALARM_IDS);
        if (ids != null && mRemoteBinder != null) {
            mRemoteBinder.deleteReminds(ids);
        }

    }

    //todo 初始化所有闹钟，一般是在获取到个人信息id后再查询数据库，然后发送该广播
    private void initAllAlarm() {
        try {
            List<ActiveBean> list = DBDao.getDao().getAllScheduleBeforeCurrentTime();
            ArrayList<Alarm> alarmList = new ArrayList<>();
            for (ActiveBean b : list) {
                alarmList.add(new Alarm(b));
            }
            if (mRemoteBinder != null) {
                mRemoteBinder.initAllRemind(alarmList);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
