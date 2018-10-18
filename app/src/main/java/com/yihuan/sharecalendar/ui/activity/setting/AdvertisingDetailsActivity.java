package com.yihuan.sharecalendar.ui.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.ui.activity.active.CreateActiveActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.AdvertisingDetailsRvAdapter;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2018/1/15.
 * 广告详情
 */

public class AdvertisingDetailsActivity extends BaseActivity {

    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.ll_add_to_active)
    LinearLayout llAddToActive;
    private AdvertisingDetailsRvAdapter adapter;
    private AdvertisingBean bean;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("广告详情");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertising_details;
    }

    @Override
    protected void initView() {
        adapter = new AdvertisingDetailsRvAdapter();
        UIUtils.initRecyclerView(this, rvRecyclerview, adapter, false);
        if (getIntent() != null && getIntent().hasExtra("bean")) {
            bean = (AdvertisingBean) getIntent().getSerializableExtra("bean");
            if (bean != null) {
                adapter.setDataList(bean.getAdImages());
            }
        }

    }

    @Override
    protected void refreshData() {

    }

    public static void startSelf(Activity activity, AdvertisingBean bean) {
        Intent intent = new Intent(activity, AdvertisingDetailsActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ll_add_to_active)
    public void onViewClicked() {
        //todo 添加到活动
        if(bean != null){
            CreateActiveActivity.startSelf(this, BeanToUtils.advertisingToActive(bean));
        }
    }

    @Override
    protected boolean doStartActivityAnim() {
        return true;
    }
}
