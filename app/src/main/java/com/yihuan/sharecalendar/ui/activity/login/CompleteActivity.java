package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/7.
 * 注册&修改密码等完成界面
 */

public class CompleteActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;
    private String type;
    private TitleView titleView;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        this.titleView = titleView;
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra(Constants.INTENT_COMPELETED_TYPE);
        }
    }

    @Override
    protected void initView() {
        if (type != null) {
            if (type.equals(Constants.INTENT_COMPELETED_TYPE_REGISTER)) {
                titleView.setCenterText("注册");
                tvInfo.setText("恭喜你，注册成功！");
            } else if (type.equals(Constants.INTENT_COMPELETED_TYPE_SET_NEWPASWORD)) {
                titleView.setCenterText("设置新密码");
                tvInfo.setText("恭喜你，设置成功！");
            }
            titleView.setRightText("完成");
            titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
                @Override
                public void onRightListener(View v) {
                    finish();
                }
            });
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete;
    }

    @Override
    protected void refreshData() {

    }

}
