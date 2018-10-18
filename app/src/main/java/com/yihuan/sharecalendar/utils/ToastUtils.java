package com.yihuan.sharecalendar.utils;

import android.widget.Toast;

import com.yihuan.sharecalendar.global.App;

/**
 * Created by Ronny on 2017/9/12.
 */

public class ToastUtils {
    public static ToastUtils instance;
    private Toast mToast;

    public static ToastUtils getInstance(){
        if(instance == null){
            synchronized (ToastUtils.class){
                if(instance == null){
                    instance = new ToastUtils();
                }
            }
        }
        return instance;
    }

    public void showShort(String msg){
        if(mToast == null){
            mToast = Toast.makeText(App.getInstanse(), msg, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }
}
