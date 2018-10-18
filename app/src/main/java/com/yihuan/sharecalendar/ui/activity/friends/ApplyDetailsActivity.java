package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;
import com.yihuan.sharecalendar.presenter.ApplyDetailsPresenter;
import com.yihuan.sharecalendar.presenter.ApplyFriendPresenter;
import com.yihuan.sharecalendar.presenter.contract.ApplyDetailsContract;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/12/9.
 */

public class ApplyDetailsActivity extends BaseActivity<ApplyDetailsPresenter> implements ApplyDetailsContract.View {
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.ll_btn_layout)
    LinearLayout llBtnLayout;

    private NewApplyListBean friendBean;

    @Override
    protected BasePresenter setPresenter() {
        return new ApplyDetailsPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("好友申请");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_details;
    }

    @Override
    protected void initView() {
        friendBean = (NewApplyListBean) getIntent().getSerializableExtra("friend");
        if(friendBean != null){
            UIUtils.displayHeader(this, friendBean.getHeaderImage(), ivHeader, R.mipmap.logo);
            tvNickname.setText(StringUtils.nullToStr(friendBean.getNickname()));
            tvDes.setText(StringUtils.nullToStr(friendBean.getAttachMessage()));
            if(friendBean.getProStatus() != null && friendBean.getProStatus().equals("0") && !BeanToUtils.isMeId(friendBean.getUserId())){
                llBtnLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void refreshData() {

    }

    public static void start(Activity activity, NewApplyListBean friend){
        Intent intent = new Intent(activity, ApplyDetailsActivity.class);
        intent.putExtra("friend",friend) ;
        activity.startActivity(intent);
    }

    @OnClick({R.id.tv_refuse, R.id.tv_agree})
    public void onViewClicked(View view) {
        String s = "";
        switch (view.getId()) {
            case R.id.tv_refuse:
                s = "2";
                break;
            case R.id.tv_agree:
                s = "1";
                break;
        }
        if(friendBean != null){
            mPresenter.agreeApply(friendBean.getUserId()+"", s);
        }
    }

    @Override
    public void onAgreeApplyOK() {
        finish();
    }
}
