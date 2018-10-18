package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.FriendSearchPresenter;
import com.yihuan.sharecalendar.presenter.contract.FriendSearchContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.FriendSearchRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/25.
 * 我的好友搜索
 */

public class FriendSearchActivity extends BaseActivity<FriendSearchPresenter> implements TextWatcher, FriendSearchContract.View {
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    private FriendSearchRvAdapter friendSearchRvAdapter;


    @Override
    protected BasePresenter setPresenter() {
        return new FriendSearchPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void initView() {
        etSearch.setHint("请输入昵称搜索");
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT);

        int stateBarHight = UIUtils.getStateBarHight(this);
        llTitle.setPadding(0, stateBarHight, 0, 0);
        showSoftKey();

        friendSearchRvAdapter = new FriendSearchRvAdapter();
        rvRecyclerview.setAdapter(friendSearchRvAdapter);
        rvRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        etSearch.addTextChangedListener(this);

        setListener();
    }

    private void setListener() {
        friendSearchRvAdapter.setOnRvItemClickListener(
                new OnRvItemClickListener<FriendSearchRvAdapter.ItemViewHolder, FriendListBean>() {
                    @Override
                    public void onItemClick(FriendSearchRvAdapter.ItemViewHolder holder, int position, List<FriendListBean> mList) {
                        Intent intent = new Intent(FriendSearchActivity.this, MyFriendDetailsActivity.class);
                        //todo 我的好友详情
                        intent.putExtra(Constants.INTENT_USER_ID, mList.get(position).getFriendId() + "");
                        startActivity(intent); }
                });
    }

    @Override
    protected void refreshData() {

    }


    @OnClick(R.id.iv_left_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.searchFriendByNickName(s.toString());
    }

    @Override
    public void onSearchFriendByNickNameOK(List<FriendListBean> listBeen) {
        friendSearchRvAdapter.setDataList(listBeen);
    }
}
