package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/25.
 * 好友》添加朋友》搜索点击后的用户详情
 */

public class AddFriendDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.iv_mood)
    ImageView ivMood;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.btn_add_friend)
    Button btnAddFriend;

    private SearchUserBean user;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("详细资料");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friends_details;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if(intent != null){
            user = (SearchUserBean) intent.getSerializableExtra(Constants.INTENT_ACCOUNTINFO_SEARCH);
           if(user != null){
               UIUtils.displayHeader(this, user.getHeaderImage(), ivHeader, R.mipmap.logo);
               tvNickname.setText(StringUtils.nullToStr(user.getNickname()));
               ivSex.setImageResource(BeanToUtils.getSexResouceId(user.getSex()));
               //todo 心情
               ivMood.setImageResource(BeanToUtils.getMoodMinResouceId(user.getCurrentMood()));
               tvAddress.setText(new StringBuffer().append(StringUtils.nullToStr(user.getProvince())).
                       append(" ").append(StringUtils.nullToStr(user.getCity()).toString()));
               tvSignature.setText(BeanToUtils.getSignature(user.getSignature()));
               if(user.isHasFriend()){
                   btnAddFriend.setText("已添加");
                   btnAddFriend.setEnabled(false);
               }
           }
        }
    }

    @Override
    protected void refreshData() {

    }

    @OnClick(R.id.btn_add_friend)
    public void onViewClicked() {
        if(user == null){
            return;
        }
        if(user.getId() == App.getInstanse().getUserId()){
            showToast("不能添加自己为好友！");
            return;
        }
        //todo add
        Intent intent = new Intent(this, ApplyFriendActivity.class);
        intent.putExtra(Constants.INTENT_USER_ID, user.getId()+"");
        startActivity(intent);
    }
}
