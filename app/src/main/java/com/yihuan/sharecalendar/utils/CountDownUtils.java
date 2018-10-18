package com.yihuan.sharecalendar.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;


/**
 * Created by Ly on 2016/5/22.
 */
public class CountDownUtils extends CountDownTimer {
    private boolean isRunning = false;
    private TextView mView;

    /**
     * @param millisInFuture    倒计时总时长---毫秒数
     * @param countDownInterval 倒计时间隔长---毫秒数
     * @param mView             倒计时要显示的View---目前为TextView
     */
    public CountDownUtils(long millisInFuture, long countDownInterval, TextView mView) {
        super(millisInFuture, countDownInterval);
        this.mView = mView;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onTick(long l) {
        isRunning = true;
        mView.setEnabled(false);
        mView.setText((l / 1000) + "s可重发");
        mView.setTextColor(mView.getResources().getColor(R.color.color_text_black_999));
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onFinish() {
        isRunning = false;
        mView.setEnabled(true);
        mView.setClickable(true);
        mView.setText("获取验证码");
        mView.setTextColor(mView.getResources().getColor(R.color.color_blue));
    }

    public void IsFinish(String Tips) {
        cancel();
        isRunning = false;
        mView.setEnabled(true);
        mView.setClickable(true);
        mView.setText(Tips);
    }

    public void stop(){
        this.cancel();
        onFinish();
    }
}
