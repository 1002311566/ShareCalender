package com.yihuan.sharecalendar.global;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yihuan.sharecalendar.alarm.AlarmService;
import com.yihuan.sharecalendar.alarm.LocalService;
import com.yihuan.sharecalendar.alarm.live.ScreenManager;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.ui.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Ronny on 2017/9/5.
 */

public class App extends MultiDexApplication {

    public static final String WX_APP_ID = "wxd0f0eacef4bf4925";
    public static IWXAPI api;

    private static App instanse;
    private int mainId;//todo 主线程id
    //用户相关
    private String token; //todo 登录token
    private UserBean user;//todo 用户信息
    private int userId;//todo 用户id
    private String themePath;
    private TimeBean currentTime;//todo 当前日期
    private MainActivity mainActivity;//todo 因为MainActivity是app栈底的活动，并且是单任务模式，在这里保存没有影响

    @Override
    public void onCreate() {
        super.onCreate();
        instanse = this;
        mainId = android.os.Process.myTid();
        PgyCrashManager.register(this);
        initWXAPI();
        initBaiDu();
        startServer();
    }

    private void startServer() {
        //todo 启动应用即开启服务
        startService(new Intent(this, AlarmService.class));
        startService(new Intent(this, LocalService.class));
    }

    private void initBaiDu() {
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initWXAPI() {
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        api.registerApp(WX_APP_ID);
    }

    public static App getInstanse(){
        return instanse;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
        if(user == null){
            JPushInterface.setAlias(this, 1, "");
            return;
        }
        //todo 设置极光推送
        JPushInterface.setAlias(this, 1, user.getId()+"");
        JPushInterface.resumePush(this);
        DataUtils.saveMyId(user.getId());//缓存用户id
    }


    public int getUserId() {
        if(user != null){
            return user.getId();
        }
        return -1;
    }

    public TimeBean getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(TimeBean currentTime) {
        this.currentTime = currentTime;
    }

    public void saveMainActivity(MainActivity activity){
        this.mainActivity = activity;
    }

    public void removeMainActivity(){
        this.mainActivity = null;
    }

    public MainActivity getMainActivity(){
        return mainActivity;
    }

    public String getThemePath() {
        return themePath;
    }

    public void setThemePath(String themePath) {
        this.themePath = themePath;
    }
}
