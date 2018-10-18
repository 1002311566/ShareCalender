package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;
import com.yihuan.sharecalendar.presenter.NewFriendPrensenter;
import com.yihuan.sharecalendar.presenter.contract.NewFriendsContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.NewFriendsListAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/25.
 * 新的朋友
 */

public class NewFriendListActivity extends BaseActivity<NewFriendPrensenter> implements NewFriendsContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView recyclerview;
    private NewFriendsListAdapter newFriendsListAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new NewFriendPrensenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("好友");
        titleView.setRightText("添加朋友");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo search user
                startActivity(new Intent(NewFriendListActivity.this, UserSearchActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend_list;
    }

    @Override
    protected void initView() {
        recyclerview.setPadding(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20f, getResources().getDisplayMetrics()), 0, 0);
        newFriendsListAdapter = new NewFriendsListAdapter();
        UIUtils.initLRecyclerView(this, recyclerview, newFriendsListAdapter, true);
        setListener();
    }

    private void setListener() {
        newFriendsListAdapter.setOnRvItemClickListener(new OnRvItemClickListener<NewFriendsListAdapter.ItemViewHolder, NewApplyListBean>() {
            @Override
            public void onItemClick(NewFriendsListAdapter.ItemViewHolder holder, int position, List<NewApplyListBean> mList) {
                //todo 好友请求详情
//                if(mList.get(position).getUserId() != App.getInstanse().getUserId()){
                    ApplyDetailsActivity.start(NewFriendListActivity.this, mList.get(position));
//                }
            }
        });

        newFriendsListAdapter.setOnAgreeClickListener(new NewFriendsListAdapter.OnAgreeClickListener() {
            @Override
            public void onAgreeClick(int friendId) {
                mPresenter.agreeApply(friendId, "1");
            }
        });

        recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getNewFriendList(Constants.TYPE_LOADMORE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void refreshData() {
        mPresenter.getNewFriendList(Constants.TYPE_REFRESH);
    }

    @Override
    public void onGetNewFriendListOK(List<NewApplyListBean> list) {
        //todo add to adapter
        newFriendsListAdapter.setDataList(list);
        recyclerview.refreshComplete(Constants.PAGE_SIZE);
    }

    @Override
    public void onAgreeApplyOK() {
        refreshData();
    }
}
