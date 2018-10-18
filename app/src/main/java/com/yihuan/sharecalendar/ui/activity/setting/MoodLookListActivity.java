package com.yihuan.sharecalendar.ui.activity.setting;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.MoodLookListPresenter;
import com.yihuan.sharecalendar.presenter.contract.MoodLookListContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactRvAdapter;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/12/14.
 * 心情指数查看 --- 已通过权限的好友列表
 */

public class MoodLookListActivity extends BaseActivity<MoodLookListPresenter> implements MoodLookListContract.View {
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    private SelectContactRvAdapter selectContactRvAdapter;
    private List<FriendListBean> mSelectFriend;


    @Override
    protected BasePresenter setPresenter() {
        return new MoodLookListPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("查看权限");
        titleView.setRightText("编辑");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                SetMoodPermissionActivity.startSelf(MoodLookListActivity.this, (ArrayList<FriendListBean>) mSelectFriend);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moodlook_list;
    }

    @Override
    protected void initView() {
        selectContactRvAdapter = new SelectContactRvAdapter(true);
        UIUtils.initRecyclerView(this, rvRecyclerview, selectContactRvAdapter, false);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPermissionFriend();
    }

    @Override
    public void onGetPermissionFriendOK(List<FriendListBean> listBeen) {
         Collections.sort(listBeen, new Comparator<FriendListBean>() {
            @Override
            public int compare(FriendListBean o1, FriendListBean o2) {
                String s1 = o1.getNicknameInitial();
                String s2 = o2.getNicknameInitial();
                s1 = s1.equals("#") ? "Z#" : s1;
                s2 = s2.equals("#") ? "Z#" : s2;
                return s1.compareTo(s2);
            }
        });
        selectContactRvAdapter.setDataList(mSelectFriend = listBeen);
    }
}
