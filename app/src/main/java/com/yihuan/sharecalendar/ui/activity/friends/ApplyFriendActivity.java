package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.ApplyFriendPresenter;
import com.yihuan.sharecalendar.presenter.contract.ApplyFriendsContract;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/25.
 * 填写申请好友验证
 */

public class ApplyFriendActivity extends BaseActivity<ApplyFriendPresenter> implements ApplyFriendsContract.View {
    @BindView(R.id.et_content)
    EditText et_content;

    private String userId;

    @Override
    protected BasePresenter setPresenter() {
        return new ApplyFriendPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("朋友验证");
        titleView.setRightText("发送");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo send
                send();
            }
        });
    }

    private void send() {
        String content = et_content.getText().toString().trim();
        mPresenter.applyFriend(userId, content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_friend;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        userId = intent.getStringExtra(Constants.INTENT_USER_ID);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onApplyFriendOK() {
        finish();
    }

    @Override
    protected boolean doExitActivityAnim() {
        return false;
    }
}
