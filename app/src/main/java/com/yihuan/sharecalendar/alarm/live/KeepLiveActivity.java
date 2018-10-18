package com.yihuan.sharecalendar.alarm.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.LogUtils;

/**
 * Created by Ronny on 2018/1/15.
 * 锁屏保活
 */

public class KeepLiveActivity extends Activity {
    public static final String TAG = "KeepLiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty);
        LogUtils.e("KeepLiveActivity - - - - - -  onCreate- - - - - - ");
        Window window = getWindow();
        window.setGravity(Gravity.START|Gravity.TOP);
        WindowManager.LayoutParams attr = window.getAttributes();
        attr.width = 1;
        attr.height = 1;
        attr.x = 0;
        attr.y = 0;
        window.setAttributes(attr);
        ScreenManager.getInstance(this).setActivity(this);
    }

    public static void startSelf(Context context){
        Intent intent = new Intent(context, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        LogUtils.e(TAG, "KeepLiveActivity - - - - - -  onDestroy- - - - - - ");
        super.onDestroy();
    }
}
