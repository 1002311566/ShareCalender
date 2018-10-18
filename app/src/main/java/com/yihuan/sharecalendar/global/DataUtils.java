package com.yihuan.sharecalendar.global;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.utils.SPUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Ronny on 2017/9/10.
 * 数据保存获取工具类
 */

public class DataUtils {

    /**
     * 保存用户登录token
     *
     * @param token
     */
    public static void saveToken(String token) {
        App.getInstanse().setToken(token);
        if(token != null){
            SPUtils.put(App.getInstanse(), Constants.SP_TOKEN, token);
        }
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public static String getToken() {
        String token = App.getInstanse().getToken();
        if (token == null) {
            token = (String) SPUtils.get(App.getInstanse(), Constants.SP_TOKEN, "");
            App.getInstanse().setToken(token);
        }
        return token;
    }

    //保持当前用户id
    public static void saveMyId(Integer myId) {
        SPUtils.put(App.getInstanse(), Constants.SP_MY_ID, myId);
    }
    //取出当前用户id
    public static Integer getMyId() {
        return (Integer) SPUtils.get(App.getInstanse(), Constants.SP_MY_ID, -1);
    }

    //如果是第一次进入App ， 则在更新数据后将其置为true
    public static void setIsFirst(Boolean isFirst){
        SPUtils.put(App.getInstanse(), Constants.SP_IS_FIRST, isFirst);
    }
    // 是否第一次进入app
    public static Boolean isFirst(){
        return (Boolean) SPUtils.get(App.getInstanse(), Constants.SP_IS_FIRST, true);
    }

    /**
     * 获取软件当前版本号
     */
    public static String getAppVersion() {
        PackageInfo packageInfo;
        try {
            packageInfo = App.getInstanse().getPackageManager().getPackageInfo(App.getInstanse().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getThemePath() {
        String path = App.getInstanse().getThemePath();
        if(TextUtils.isEmpty(path)){
            return null;
        }
        return Urls.IMG_HEAD + App.getInstanse().getThemePath();
    }

    /**
     * 清空用户数据
     **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void clearUserCache() {
        App.getInstanse().setToken(null);
        App.getInstanse().setUser(null);
        App.getInstanse().setThemePath(null);
        SPUtils.remove(App.getInstanse(), Constants.SP_TOKEN);
        SPUtils.remove(App.getInstanse(), Constants.SP_MY_ID);
        ScheduleManager.getInstance().resetAll();
        JPushInterface.stopPush(App.getInstanse());
    }
}
