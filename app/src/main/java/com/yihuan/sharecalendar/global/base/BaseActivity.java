package com.yihuan.sharecalendar.global.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.ui.activity.login.LoginActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.ToastUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ronny on 2017/9/5.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AutoLayoutActivity implements BaseView {

    TitleView titleView;
    LinearLayout mContentView;
    ViewStub mViewStub;
    View mLoaddingView;
    protected P mPresenter;

    private Unbinder unbinder;
    private RefreshBroadCastReceiver receiver;
    private TextView mTvLoaddingTextView;
    private LinearLayout llRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (doStartActivityAnim()) {
            openAnim();
        }
        super.onCreate(savedInstanceState);
        //设置全屏,透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_base);
        llRootView = (LinearLayout) findViewById(R.id.ll_rootview);
        titleView = (TitleView) findViewById(R.id.title_view);
        mContentView = (LinearLayout) findViewById(R.id.fl_content);
        mViewStub = (ViewStub) findViewById(R.id.vs_status_view);
        mLoaddingView = findViewById(R.id.layout_loadding);
        mTvLoaddingTextView = (TextView) findViewById(R.id.tv_loading_text);

        titleView.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        int stateBarHight = UIUtils.getStateBarHight(this);
        View view = new View(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, stateBarHight);
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(getResources().getColor(R.color.color_gray_split));
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(view);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, stateBarHight, 0, 0);
        titleView.setLayoutParams(layoutParams);

        if (getLayoutId() != 0) {
            View childView = LayoutInflater.from(this).inflate(getLayoutId(), mContentView, false);
            if (childView != null) {
                mContentView.addView(childView);
            }
        }

        mPresenter = (P) setPresenter();
        unbinder = ButterKnife.bind(this);

        initTitleView(titleView);
        initView();
        registeRefreshReceiver();
    }

    private void registeRefreshReceiver() {
        receiver = new RefreshBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_REFRESH);
        intentFilter.addAction(Constants.ACTION_REFRESH_HOME);
        registerReceiver(receiver, intentFilter);
    }

    public TitleView getTitleView() {
        return titleView;
    }


    /**
     * @return 设置对应的presenter
     */
    protected abstract BasePresenter setPresenter();

    /**
     * @return 获取跟布局
     */
    public View getRootView() {
        return mContentView;
    }


    /**
     * 初始化标题栏
     *
     * @param titleView
     */
    protected abstract void initTitleView(TitleView titleView);


    /**
     * @return 要插入的视图
     */
    protected abstract int getLayoutId();

    /**
     * 初始化操作
     */
    protected abstract void initView();

    /**
     * 重新加载数据
     */
    protected abstract void refreshData();


    @Override
    public void showLoaddingView(boolean show) {
        mLoaddingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showLoaddingView(boolean show, String text) {
        showLoaddingView(show);
        mTvLoaddingTextView.setVisibility(show ? View.VISIBLE : View.GONE);
        mTvLoaddingTextView.setText(text);
    }


    @Override
    public void showLoadFeilureView() {

    }

    @Override
    public void showNotLoginView() {

    }

    @Override
    public void showNoDataView() {
        mViewStub.setLayoutResource(R.layout.layout_viewstub);
        View view = mViewStub.inflate();
        view.findViewById(R.id.tv_no_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }

    @Override
    public void showNotNetView() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.getInstance().showShort(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            //绑定View
            mPresenter.attachView(this);
        }
    }

    /**
     * 用来关闭recycleview下拉刷新等
     */
    protected void onCloseLoadding() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            //解绑View
            mPresenter.dettachView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unbinder.unbind();
    }

    public void startActivityAnim(Intent intent) {
        this.startActivity(intent);
        openAnim();
    }

    public void startActivityForResultAnim(Intent intent, int requestCode) {
        this.startActivityForResult(intent, requestCode);
        openAnim();
    }

    public void openAnim() {
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        if (doExitActivityAnim()) {
            openAnim();
        }
    }

    /**
     * 默认执行退出动画
     *
     * @return
     */
    protected boolean doExitActivityAnim() {
        return true;
    }

    protected boolean doStartActivityAnim() {
        return false;
    }

    private class RefreshBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isRefreshReceiver()) {
                switch (intent.getAction()) {
                    case Constants.ACTION_REFRESH:
                        refreshData();
                        break;
                    case Constants.ACTION_REFRESH_HOME:
                        refreshHomeData();
                        break;
                }
            }
        }
    }

    protected void refreshHomeData() {

    }

    private boolean isRefreshReceiver() {
        return true;
    }

    public void toLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
//        LoginActivity.startSelfForResult(this, Constants.REQUEST_CODE_LOGIN, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //todo 处理登录界面
        if (requestCode == Constants.REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            this.refreshData();
        } else if (requestCode == Constants.REQUEST_CODE_LOGIN && resultCode == RESULT_CANCELED) {
            this.finish();
        }
    }

    protected void sendHomeRefreshBroadCast() {
        sendBroadcast(new Intent(Constants.ACTION_REFRESH_HOME));
    }

    protected void sendRefreshBroadCast() {
        sendBroadcast(new Intent(Constants.ACTION_REFRESH));
    }

    /**
     * 没有更多数据时调用
     */
    @Override
    public void onNoMore() {

    }

    /**
     * 检测权限
     */
    protected void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                    ) {
                //todo request permission
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION}, 1111);
            }
        }

    }

    protected boolean checkPermission(int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, requestCode);
                    return false;
                }else{
                    return true;
                }
            }
        }
        return true;
    }

    protected void showSoftKey() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
