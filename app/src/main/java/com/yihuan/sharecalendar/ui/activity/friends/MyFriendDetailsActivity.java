package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.presenter.MyFriendDetailsPresenter;
import com.yihuan.sharecalendar.presenter.contract.MyFriendDetiailsContract;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.activity.active.CreateActiveActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.MyFriendDetailsRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.dialog.AdvertisingDialog;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/27.
 * 我的好友详情
 */

public class MyFriendDetailsActivity extends BaseActivity<MyFriendDetailsPresenter> implements MyFriendDetiailsContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView rvRecyclerview;
    @BindView(R.id.btn_look_mood)
    Button btnLookMood;
    @BindView(R.id.btn_look_state)
    Button btnLookState;
    private MyFriendDetailsRvAdapter myFriendDetailsRvAdapter;

    private String mFriendId;
    private MyFriendDetailBean myFriendDetailBean;
    @Override
    protected BasePresenter setPresenter() {
        return new MyFriendDetailsPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("好友详情");
        titleView.setRightText("设置");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                MyFriendSettingActivity.start(MyFriendDetailsActivity.this, myFriendDetailBean);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend_details;
    }

    @Override
    protected void initView() {
        myFriendDetailsRvAdapter = new MyFriendDetailsRvAdapter();
        UIUtils.initLRecyclerView(this, rvRecyclerview, myFriendDetailsRvAdapter, true);

        mFriendId = getIntent().getStringExtra(Constants.INTENT_USER_ID);
        setListener();
    }

    private void setListener() {
        rvRecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        myFriendDetailsRvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 去创建活动
                startActivityAnim(new Intent(MyFriendDetailsActivity.this, CreateActiveActivity.class));
            }
        });

        myFriendDetailsRvAdapter.setItemClickListeners(new OnRvItemClickListeners<MyFriendDetailBean.ActivityListBean>() {
            @Override
            public void onItemClick(int position, List<MyFriendDetailBean.ActivityListBean> mList) {
                //todo 去活动详情
                if(mList.get(position).getUserId() == App.getInstanse().getUserId()){
                    ActiveDetailsActivity_Publish.start(MyFriendDetailsActivity.this, mList.get(position).getId());
                }else{
                    ActiveDetailsActivity_Receiver.start(MyFriendDetailsActivity.this, mList.get(position).getId());
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public static void start(Activity activity, String friendId){
        Intent intent = new Intent(activity, MyFriendDetailsActivity.class);
        intent.putExtra(Constants.INTENT_USER_ID, friendId);
        activity.startActivity(intent);
    }

    @Override
    protected void refreshData() {
        mPresenter.getFriendDetails(mFriendId);
    }


    @OnClick({R.id.btn_look_mood, R.id.btn_look_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_look_mood://todo 查看好友心情指数，需要做查看权限判断
                lookFriendMood();
                break;
            case R.id.btn_look_state://todo 查看好友空闲状态
                FriendCalendarActivity.startSelf(this, mFriendId);
                break;
        }
    }

    private void lookFriendMood() {
        mPresenter.queryFriendMood(mFriendId);
    }

    @Override
    public void onGetFriendDetailsOK(MyFriendDetailBean bean) {
        rvRecyclerview.refreshComplete(20);
        myFriendDetailsRvAdapter.setMyFriendDetailBean(myFriendDetailBean = bean);
    }

    /**
     * 无权限访问
     */
    @Override
    public void onQueryFialure() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_no_permission,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.setView(view).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(UIUtils.getScreenWidth(this)*4/5, WindowManager.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onQueryOK() {
        LookFriendMoodActivity.startForResult(this, mFriendId, Constants.REQUEST_CODE_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_CANCELED){
            //todo ===---------------去掉心情指数广告
//            AdvertisingDialog dialog = new AdvertisingDialog(MyFriendDetailsActivity.this);
//            dialog.show("2","2","1");
        }
    }
}
