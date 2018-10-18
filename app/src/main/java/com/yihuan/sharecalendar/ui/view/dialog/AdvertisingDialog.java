package com.yihuan.sharecalendar.ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.AdvertisingParams;
import com.yihuan.sharecalendar.ui.activity.setting.AdvertisingDetailsActivity;
import com.yihuan.sharecalendar.utils.ToastUtil;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/12/28.
 */

public class AdvertisingDialog extends Dialog implements View.OnClickListener {

    TextView tvTime;
    LinearLayout llClose;
    FrameLayout fl_layout;
    private Context context;
    private View.OnClickListener listener;
    private CallBack observer;

    private int time = 3;
    private AdvertisingBean bean;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time <= 0) {
                AdvertisingDialog.this.dismiss(false);
                handler.removeCallbacksAndMessages(null);
                return;
            }
            tvTime.setText(--time + "");
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };


    public AdvertisingDialog(@NonNull Context context) {
        this(context, R.style.dialog);
        this.context = context;
    }

    public AdvertisingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AdvertisingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_advertising);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llClose = (LinearLayout) findViewById(R.id.ll_close);
        fl_layout = (FrameLayout) findViewById(R.id.fl_layout);

        llClose.setOnClickListener(this);
        fl_layout.setOnClickListener(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = UIUtils.getScreenHeight(context) * 8 / 9;
        params.width = UIUtils.getScreenWidth(context);
        getWindow().setAttributes(params);
        getWindow().setGravity(Gravity.TOP);
        setCancelable(false);
    }

    /**
     * @param playType 播放时机
     * @param exeType  执行方式
     * @param adType   广告类型
     */
    public void show(String playType, String exeType, String adType, String exeLocation) {
        AdvertisingParams bean = new AdvertisingParams();
        bean.playType = playType;
        bean.exeType = exeType;
        bean.adType = adType;
        bean.exeLocation = exeLocation;

        observer = new CallBack();
        observer.setBind(true);
        Api.getAdvertising(bean, 1, null, observer);
    }

    /**
     * @param hasAction 是否有其它意图，有则不执行onclick方法
     */
    public void dismiss(boolean hasAction) {
        handler.removeCallbacksAndMessages(null);
        super.dismiss();
        if (listener != null && !hasAction) {
            listener.onClick(null);
        }
        if (observer != null) {
            observer.setBind(false);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_close) {
            //todo 关闭广告
            this.dismiss(false);
        } else if (v.getId() == R.id.fl_layout) {
            //todo 点击图片
            if (bean != null) {
                AdvertisingDetailsActivity.startSelf((Activity) context, bean);
                dismiss(true);
            }
        }
    }

    class CallBack extends MyObserver<List<AdvertisingBean>> {

        @Override
        protected void onFailure(int code, String msg) {
            ToastUtil.showShort(getContext(), msg);
        }

        @Override
        protected void onSucceed(List<AdvertisingBean> beanList) {
            if (beanList != null && beanList.size() > 0) {
                AdvertisingDialog.super.show();
                AdvertisingBean b1 = beanList.get(0);
                AdvertisingDialog.this.bean = b1;
                if (b1 != null && b1.getAdImages() != null && b1.getAdImages().size() > 0) {
                    AdvertisingBean.AdImagesBean adImagesBean = b1.getAdImages().get(0);
                    if (adImagesBean != null) {
                        UIUtils.displayHeader(getContext(), adImagesBean.getImage(), fl_layout, R.mipmap.icon_advertising);
                    }
                }
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                //todo 当没有数据时，为不影响功能，直接跳转
                AdvertisingDialog.this.dismiss(false);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
