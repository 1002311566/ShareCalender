package com.yihuan.sharecalendar.alarm;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.aidl.IAlarmControl;
import com.yihuan.sharecalendar.alarm.live.ScreenBroadcastListener;
import com.yihuan.sharecalendar.alarm.live.ScreenManager;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ronny on 2018/1/9.
 * 设置闹钟的服务
 */

public class AlarmService extends Service {
    public static final String TAG = "AlarmService";
    public static final int FOREGROUND_ID = 0x199;

    private MyBinder myBinder;
    private IAlarmControl mRemoteBinder;

    private CopyOnWriteArrayList<Integer> alarmIdList = new CopyOnWriteArrayList<>();//todo 存储闹钟id，用于取消所有闹钟

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteBinder = IAlarmControl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e(TAG, " - - - - - -  远程服务已断开- - - - - - ");
            mRemoteBinder = null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG, "AlarmService - - - - - -  onCreate- - - - - - ");
//        Intent intent1 = new Intent(this, LocalService.class);
//        startService(intent1);
//        bindService(intent1, conn, Context.BIND_ABOVE_CLIENT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG, "AlarmService - - - - - -  onStartCommand- - - - - - ");
        setForeground();
        initLiveActivity();
        //todo
        return START_STICKY;
    }

    private void setForeground() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(FOREGROUND_ID, new Notification());//启动前台服务
        } else {
            Intent intent = new Intent(this, InnerService.class);
            startService(intent);
            startForeground(FOREGROUND_ID, new Notification());
        }
    }

    /**
     * 启动保活activity
     */
    private void initLiveActivity() {
        final ScreenManager manager = ScreenManager.getInstance(this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                manager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                manager.startActivity();
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e(TAG, "AlarmService - - - - - -  onBind- - - - - - ");
        if (myBinder == null) {
            myBinder = new MyBinder();
        }
//        JPushInterface.resumePush(this);
        //todo 重新设置闹钟
        resetAlarm();
        return myBinder;
    }

    private void resetAlarm() {
        //todo
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlarmManagerUtil.resetAll(AlarmService.this);
//            }
//        }, 60000);
    }

    @Override
    public void onDestroy() {
        LogUtils.e(TAG, "AlarmService - - - - - -  onDestroy- - - - - - ");
        super.onDestroy();
//        unbindService(conn);

        //todo 调用服务端进程，重新启动该服务
//        try {
//            if (mRemoteBinder != null) {
//                mRemoteBinder.restart();
//                LogUtils.e(TAG, "AlarmService - - - - - -  restart- - - - - - ");
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            LogUtils.e(TAG, "AlarmService - - - - - -  RemoteException- - - - - - ");
//        }
    }

    //
    public static class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(AlarmService.FOREGROUND_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    //代理binder
    class MyBinder extends IAlarmControl.Stub {

        @Override
        public void initAllRemind(List<Alarm> list) throws RemoteException {
            for (Alarm alarm : list) {
                AlarmManagerUtil.addRemind(AlarmService.this, alarm);
//                alarmIdList.add(alarm.getId());
            }
            LogUtils.e(TAG, "AlarmService - - - - - -  initAllRemind- - - - - - ");
        }

        @Override
        public void addRemind(Alarm alarm) throws RemoteException {
            AlarmManagerUtil.addRemind(AlarmService.this, alarm);
//            alarmIdList.add(alarm.getId());
            LogUtils.e(TAG, "AlarmService - - - - - -  addRemind- - - - - - ");
        }

        @Override
        public void deleteRemind(Alarm alarm) throws RemoteException {
            AlarmManagerUtil.cancelAlarm(AlarmService.this, alarm.getId());
//            alarmIdList.remove(alarm.getId());
            LogUtils.e(TAG, "AlarmService - - - - - -  RemoteException- - - - - - ");
        }

        @Override
        public void deleteReminds(int[] idList) throws RemoteException {

        }

        @Override
        public void updateRemind(Alarm alarm) throws RemoteException {
            AlarmManagerUtil.updateAlarm(AlarmService.this, alarm);
            LogUtils.e(TAG, "AlarmService - - - - - -  updateRemind- - - - - - ");
        }

        @Override
        public void removeAllRemind() throws RemoteException {
            //todo 当重置闹钟或退出登录时移除所有闹钟
            for (Integer id : alarmIdList){
                AlarmManagerUtil.cancelAlarm(AlarmService.this, id);
            }
            LogUtils.e(TAG, "AlarmService - - - - - -  updateRemind- - - - - - ");
        }

        //重启 LocalService
        @Override
        public void restart() throws RemoteException {
            Intent intent = new Intent(AlarmService.this, LocalService.class);
            AlarmService.this.startService(intent);
//            AlarmService.this.bindService(intent, conn, BIND_ABOVE_CLIENT);
            LogUtils.e(TAG, "AlarmService - - - - - -  restart- - - - - - ");
        }
    }

}
