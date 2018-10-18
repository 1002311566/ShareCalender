package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.presenter.SetFriendTagPresenter;
import com.yihuan.sharecalendar.presenter.contract.SetFriendTagContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SetFriendTagRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/10/3.
 * 设置好友标签
 */

public class SetFriendTagActivity extends BaseActivity<SetFriendTagPresenter> implements SetFriendTagContract.View{

    @BindView(R.id.rv_recyclerview)
    RecyclerView recyclerView;

    private List<String> mSelectTagList;//选中的标签
    private String mFriendId;//该好友的id
    private SetFriendTagRvAdapter setFriendTagRvAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new SetFriendTagPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("标签");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                save();
            }
        });
    }

    private void save() {
        List<TagListBean> dataList = setFriendTagRvAdapter.getDataList();
        mSelectTagList.clear();
        for (TagListBean b : dataList){
            if(b.isInGroup()){
                mSelectTagList.add(b.getId()+"");
            }
        }
        mPresenter.moveFriendToTag(mFriendId, mSelectTagList);
    }

    protected int getLayoutId() {
        return R.layout.activity_set_friend_tag;
    }

    @Override
    protected void initView() {
        mSelectTagList = new ArrayList<>();

        mFriendId = getIntent().getStringExtra(Constants.INTENT_USER_ID);

        setFriendTagRvAdapter = new SetFriendTagRvAdapter();
        UIUtils.initRecyclerView(this, recyclerView, setFriendTagRvAdapter, true);
        refreshData();

        setListener();
    }

    private void setListener() {
        setFriendTagRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<SetFriendTagRvAdapter.ItemViewHolder, TagListBean>() {
            @Override
            public void onItemClick(SetFriendTagRvAdapter.ItemViewHolder holder, int position, List<TagListBean> mList) {
//                mSelectTagList.add(mList.get(position));
            }
        });
    }

    public static void start(Activity activity, String friendId){
        Intent intent = new Intent(activity, SetFriendTagActivity.class);
        intent.putExtra(Constants.INTENT_USER_ID, friendId);
        activity.startActivity(intent);
    }

    @Override
    protected void refreshData() {
        mPresenter.getTagList(mFriendId);
    }

    @Override
    public void onMoveFriendToTagOK() {
        finish();
    }

    @Override
    public void onGetTagListOK(List<TagListBean> listBeen) {
        setFriendTagRvAdapter.setDataList(listBeen);
    }
}
