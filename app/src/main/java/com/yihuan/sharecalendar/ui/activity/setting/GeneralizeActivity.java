package com.yihuan.sharecalendar.ui.activity.setting;

import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.ShareBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareType;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.popwin.ActiveSharePop;
import com.yihuan.sharecalendar.ui.view.popwin.GeneralizePop;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/26.
 * 推广大使
 */

public class GeneralizeActivity extends BaseActivity {

    @BindView(R.id.tv_more)
    TextView tvMore;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("分享大使");
        titleView.setRightText("分享");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                ActiveSharePop activeSharePop = new ActiveSharePop(GeneralizeActivity.this, v);
                activeSharePop.showBottomDefault();
//                activeSharePop.setShareBean(new ShareBean(ShareType.TEXT,"分享大使的分享",null, null, null, null));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_generilize;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void refreshData() {

    }

    @OnClick(R.id.tv_more)
    public void onViewClicked() {
        showMorePop();
    }

    private void showMorePop() {
        GeneralizePop generalizePop = new GeneralizePop(this, tvMore);
        generalizePop.showBottomDefault();
    }
}
