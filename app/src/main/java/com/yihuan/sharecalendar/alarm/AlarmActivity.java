package com.yihuan.sharecalendar.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.activity.setting.AddRemindActivity;
import com.yihuan.sharecalendar.ui.activity.setting.AutoRemindActivity;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronny on 2018/1/11.
 * 闹铃界面---子进程
 */

public class AlarmActivity extends AutoLayoutActivity {

    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_enter)
    TextView tvEnter;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_active_title)
    TextView tvActiveTitle;

    private Ringtone mAudioPlayer;
    private Vibrator mVibrator;

    private Alarm mAlarm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_active_alarm);
        ButterKnife.bind(this);

        //设置宽高
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (UIUtils.getScreenWidth(this) * 668 / 750);
        params.height = (int) (UIUtils.getScreenHeight(this) * 800 / 1334);
        getWindow().setAttributes(params);

        //锁屏、解锁、保持常亮、打开屏幕
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        //调用系统响铃
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (defaultUri == null) return;
        mAudioPlayer = RingtoneManager.getRingtone(this, defaultUri);
        mAudioPlayer.play();
        //震动
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{1000, 500, 1000, 500}, 0);//间隔1m震动2m， 角标0开始重复执行

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mVibrator != null && mVibrator.hasVibrator()){
                    mVibrator.cancel();
                }
            }
        }, 10000);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("bundle")){
            Bundle bundle = intent.getBundleExtra("bundle");
            mAlarm = bundle.getParcelable(Constants.INTENT_ALARM_BEAN);
        }

        refreshUI();
    }

    private void refreshUI() {
        if(mAlarm == null)return;
        tvActiveTitle.setText(StringUtils.nullToStr(mAlarm.getTitle()));
        tvStartTime.setText("本条开始时间:"+ StringUtils.nullToStr(new DateTime(mAlarm.getStart_time()).toString("yyyy年MM月dd日 HH:mm")));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //按电源键后，再次启动时继续亮屏
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        if (!pm.isScreenOn()) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire();
            wl.release();
        }

        if(intent != null){
            Bundle bundle = intent.getBundleExtra("bundle");
            if(bundle != null){
                mAlarm = bundle.getParcelable(Constants.INTENT_ALARM_BEAN);
            }
        }
        refreshUI();
    }

    @Override
    public void finish() {
        mAudioPlayer.stop();
        if(mVibrator != null && mVibrator.hasVibrator()){
            mVibrator.cancel();
        }
        super.finish();
    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick({R.id.tv_des, R.id.tv_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_des:
                toDes();
                break;
            case R.id.tv_enter:
                finish();
                break;
        }
    }

    private void toDes() {

        Integer myId = DataUtils.getMyId();
        if(myId == null){
            toMain();
            finish();
            return;
        }
        toMain();

        if(mAlarm == null){
            finish();
            return;
        }
        boolean isAutoRemind = mAlarm.isAutoRemind();
        int publsh_user_id = mAlarm.getPublsh_user_id();
        int id = mAlarm.getId();
        //todo 对定制化提醒和活动分开处理
        if(!isAutoRemind){
            //todo-----------活动---------------------------------------
            if(publsh_user_id == myId.intValue()){
                //todo 去发起者活动详情
                ActiveDetailsActivity_Publish.start(this, id);
            }else{
                ActiveDetailsActivity_Receiver.start(this, id);
            }
        }else{
            //todo-----------定制化提醒-----------------------------------
            Intent intent = new Intent(AlarmActivity.this, AddRemindActivity.class);
            intent.putExtra(AddRemindActivity.REMIND_ID, id - 10000);
            startActivity(intent);
        }
        finish();
    }
}
