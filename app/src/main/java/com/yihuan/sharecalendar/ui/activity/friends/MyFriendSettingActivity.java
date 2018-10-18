package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.presenter.MyFriendSettingPresenter;
import com.yihuan.sharecalendar.presenter.contract.MyFriendSettingContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/29.
 * 我的好友详情-》好友设置
 */

public class MyFriendSettingActivity extends BaseActivity<MyFriendSettingPresenter> implements MyFriendSettingContract.View {
    @BindView(R.id.tv_set_tag)
    TextView tvSetTag;
    @BindView(R.id.tv_backup_brithday)
    TextView tvBackupBrithday;
    @BindView(R.id.btn_delete_my_friend)
    Button btnDeleteMyFriend;

    private MyFriendDetailBean myFriendDetailBean;
    private String friendId= "";

    public static void start(Activity activity, MyFriendDetailBean myFriendDetailBean){
        Intent intent = new Intent(activity, MyFriendSettingActivity.class);
        intent.putExtra(Constants.INTENT_MY_FRIEND_DETAILS, myFriendDetailBean);
        activity.startActivity(intent);
    }

    @Override
    protected BasePresenter setPresenter() {
        return new MyFriendSettingPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("好友设置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend_setting;
    }

    @Override
    protected void initView() {
        myFriendDetailBean = (MyFriendDetailBean) getIntent().getSerializableExtra(Constants.INTENT_MY_FRIEND_DETAILS);
        if(myFriendDetailBean != null){
            friendId = myFriendDetailBean.getFriendId()+"";
        }
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.tv_set_tag, R.id.tv_backup_brithday, R.id.btn_delete_my_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_set_tag://todo 添加标签
                SetFriendTagActivity.start(this, friendId);
                break;
            case R.id.tv_backup_brithday://todo 备注生日
                SetFriendBirthdayActivity.start(this, myFriendDetailBean);
                break;
            case R.id.btn_delete_my_friend://todo 删除好友
                deleteFriend();
                break;
        }
    }

    private void deleteFriend() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_delete_friend, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(view).create();
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 取消
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 确定
                mPresenter.deleteFriend(friendId);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onDeleteOK() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
