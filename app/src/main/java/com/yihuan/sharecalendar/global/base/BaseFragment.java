package com.yihuan.sharecalendar.global.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ronny on 2017/9/5.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    Unbinder unbinder;
    private View rootView;//内容View
    protected P mPresenter;
    private ViewStub mViewStub;//不常用布局
    private View mLoaddingView;//加载框
    private FrameLayout rootLayout;//顶级rootview
    private RefreshBroadCastReceiver refreshReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        rootLayout = new FrameLayout(container.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.addView(rootView, params);//主要视图
        mViewStub = new ViewStub(container.getContext(), R.layout.layout_viewstub);
        rootLayout.addView(mViewStub, params);//添加错误加载布局
        mLoaddingView = inflater.inflate(R.layout.layout_loadding, container, false);
        rootLayout.addView(mLoaddingView, params);//添加加载框
        mLoaddingView.setVisibility(View.GONE);

        mPresenter = (P) setPresenter();
        unbinder = ButterKnife.bind(this, rootLayout);
        initView();
        registeRefreshReceiver();//todo 注册刷新广播
        return rootLayout;
    }

    private void registeRefreshReceiver() {
        refreshReceiver = new RefreshBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_REFRESH);
        intentFilter.addAction(Constants.ACTION_REFRESH_HOME);
        getActivity().registerReceiver(refreshReceiver, intentFilter);
    }

    protected abstract BasePresenter setPresenter();

    /**
     * @return 获取跟布局
     */
    public View getRootView() {
        return rootLayout;
    }


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


    @Override
    public void showLoadFeilureView() {

    }

    /**
     * 显示没有登录的视图
     */
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
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            //绑定View
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (mPresenter != null) {
//            //解绑View
//            mPresenter.dettachView();
//        }
    }

    /**
     * 用来关闭recycleview下拉刷新等
     */
    public void onCloseRefresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            //解绑View
            mPresenter.dettachView();
        }
        getActivity().unregisterReceiver(refreshReceiver);
        unbinder.unbind();
    }

    public void startActivityAnim(Intent intent) {
        this.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    /**
     * 界面刷新的广播
     */
    private class RefreshBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isRefreshReceiver()) {
                switch (intent.getAction()) {
                    case Constants.ACTION_REFRESH:
                        refreshData();
                        break;
                    case Constants.ACTION_REFRESH_HOME:
//                        refreshData();
                        refreshHomeData();
                        break;
                }
            }
        }
    }

    protected void refreshHomeData() {

    }

    /**
     * 是否接受刷新广播
     *
     * @return
     */
    protected boolean isRefreshReceiver() {
        return true;
    }

    protected void sendHomeRefreshBroadCast() {
        getActivity().sendBroadcast(new Intent(Constants.ACTION_REFRESH_HOME));
    }

    protected void sendRefreshBroadCast() {
        getActivity().sendBroadcast(new Intent(Constants.ACTION_REFRESH));
    }

    /**
     * 没有更多数据时调用
     */
    @Override
    public void onNoMore() {

    }
}
