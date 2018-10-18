package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.FriendListByTagPresenter;
import com.yihuan.sharecalendar.presenter.contract.FriendListByTagContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactSearchRvAdapter;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/28.
 * 某个标签下的所有好友
 */

public class FriendListByTagtActivity extends BaseActivity<FriendListByTagPresenter> implements FriendListByTagContract.View {


    @BindView(R.id.rv_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_all)
    TextView tvAll;//todo 全选


    private SelectContactSearchRvAdapter selectContactSearchRvAdapter;
    private String tagId;
    private ArrayList<FriendListBean> allFriendList;

    @Override
    protected BasePresenter setPresenter() {
        return new FriendListByTagPresenter(this);
    }

    public static void startSelfForResult(Activity activity, String tagName, String tagId, ArrayList<FriendListBean> allFriendList, int requestCode) {
        Intent intent = new Intent(activity, FriendListByTagtActivity.class);
        intent.putExtra(Constants.INTENT_TAG_NAME, tagName);
        intent.putExtra(Constants.INTENT_TAG_ID, tagId);
        intent.putParcelableArrayListExtra("allFriendList", allFriendList);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        String tagName = getIntent().getStringExtra(Constants.INTENT_TAG_NAME);
        tagId = getIntent().getStringExtra(Constants.INTENT_TAG_ID);

        titleView.setCenterText(tagName);

        titleView.setLeftText("取消");
        titleView.setRightText("确定");
        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });

        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST, allFriendList);
                intent.setAction(Constants.ACTION_SELECT_FRIEND);
                sendBroadcast(intent);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friendlist_by_tag;
    }

    @Override
    protected void initView() {
        selectContactSearchRvAdapter = new SelectContactSearchRvAdapter();
        UIUtils.initRecyclerView(this, recyclerView, selectContactSearchRvAdapter, true);

        if (getIntent() != null && getIntent().hasExtra("allFriendList")) {
            allFriendList = getIntent().getParcelableArrayListExtra("allFriendList");
        }
        refreshData();
    }


    @Override
    protected void refreshData() {
        mPresenter.getFriendListByTag(tagId);
    }

    @Override
    public void onGetFriendListByTagOK(List<FriendListBean> listBeen) {
        selectContactSearchRvAdapter.setData(listBeen, allFriendList);
    }

    @OnClick({R.id.tv_all})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.tv_all://todo 全选
                selectAll();
                break;
        }
    }

    private void selectAll() {
        selectContactSearchRvAdapter.selectAll();
//        tvAll.setText(selectContactSearchRvAdapter.isAll() ? "取消全选" : "全选");
    }
}
