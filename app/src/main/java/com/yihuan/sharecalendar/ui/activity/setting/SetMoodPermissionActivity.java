package com.yihuan.sharecalendar.ui.activity.setting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.SetMoodPermissionPrensenter;
import com.yihuan.sharecalendar.presenter.contract.MoodLookContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectContactActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareFriendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/26.
 * 心情指数查看--权限设置
 */

public class SetMoodPermissionActivity extends BaseActivity<SetMoodPermissionPrensenter> implements MoodLookContract.View {

    @BindView(R.id.rv_recyclerview)
    RecyclerView recyclerView;
    private ShareFriendRvAdapter moodLookRvAdapter;
    private SelectFriendReceiver receiver;

    private List<FriendListBean> mSelectList;//添加的好友对象

    @Override
    protected BasePresenter setPresenter() {
        return new SetMoodPermissionPrensenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("查看权限设置");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                save();
            }
        });
    }

    /**
     * 提交添加的好友
     */
    private void save() {
        //todo
        mPresenter.addLookFriendList(mSelectList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mood_look;
    }

    @Override
    protected void initView() {
        mSelectList = new ArrayList<>();
        registSelectFriendReceiver();

        moodLookRvAdapter = new ShareFriendRvAdapter();
        recyclerView.setAdapter(moodLookRvAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        if(getIntent()  != null && getIntent().hasExtra("friendList")){
            mSelectList = getIntent().getParcelableArrayListExtra("friendList");
            moodLookRvAdapter.setDataList(mSelectList);
        }
        setListener();
    }

    private void registSelectFriendReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SELECT_FRIEND);
        receiver = new SelectFriendReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void setListener() {
        moodLookRvAdapter.setOnRvItemClickListener3(new OnRvItemAddAndDeleteListener<FriendListBean>() {
            @Override
            public void onAddClick() {
                SelectContactActivity.startSelfForResult(SetMoodPermissionActivity.this, mSelectList, Constants.REQUEST_CODE_1);
            }

            @Override
            public void onDeleteClick(List<FriendListBean> mList, int position) {
                mSelectList.remove(position);
                moodLookRvAdapter.setDataList(mSelectList);
            }
        });
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onAddLookFriendListOK() {
        finish();
    }

    public class SelectFriendReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.INTENT_FRIEND_LIST)) {
                List<FriendListBean> listBeen = intent.getParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST);
                if (listBeen != null && listBeen.size() > 0) {
                    mSelectList.clear();
                    for (FriendListBean bean : listBeen) {
                        if (bean.isSelect) {
                            mSelectList.add(bean);
                        }
                    }
                    moodLookRvAdapter.setDataList(mSelectList);
                }
            }
        }
    }

    public static void startSelf(Activity activity, ArrayList<FriendListBean> beanList){
        Intent intent = new Intent(activity, SetMoodPermissionActivity.class);
        intent.putParcelableArrayListExtra("friendList", beanList);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
