package com.yihuan.sharecalendar.utils;

import android.provider.Settings;

import com.yihuan.sharecalendar.global.App;

/**
 * Created by Ronny on 2018/1/4.
 */

public class SystemUtils {

    /**
     * 获取设备号
     * AndroidId + Serial Number
     * @return
     */
    public static String getDeviceId() {
        String ANDROID_ID = Settings.System.getString(App.getInstanse().getContentResolver(), Settings.System.ANDROID_ID);
        String SerialNumber = android.os.Build.SERIAL;
        return MD5Utils.GetMD5Code(ANDROID_ID + SerialNumber);
    }

    /**
     * 清除缓存
     */
    public static void cleanCache(){

    }
}
