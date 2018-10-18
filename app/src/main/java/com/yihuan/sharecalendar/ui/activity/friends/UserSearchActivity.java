package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;
import com.yihuan.sharecalendar.presenter.UserSearchPresenter;
import com.yihuan.sharecalendar.presenter.contract.UserSearchContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.UserSearchRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/25.
 * 添加好友》搜索
 */

public class UserSearchActivity extends BaseActivity<UserSearchPresenter> implements TextWatcher, UserSearchContract.View {
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.ll_search_null)
    LinearLayout llSearchNull;
    private UserSearchRvAdapter userSearchRvAdapter;


    @Override
    protected BasePresenter setPresenter() {
        return new UserSearchPresenter(this);
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

        int stateBarHight = UIUtils.getStateBarHight(this);
        llTitle.setPadding(0, stateBarHight, 0, 0);
        showSoftKey();

        userSearchRvAdapter = new UserSearchRvAdapter();
        rvRecyclerview.setAdapter(userSearchRvAdapter);
        rvRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        etSearch.addTextChangedListener(this);

        setListener();
    }

    private void setListener() {
        userSearchRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<UserSearchRvAdapter.ViewHolder, SearchUserBean>() {
            @Override
            public void onItemClick(UserSearchRvAdapter.ViewHolder holder, int position, List<SearchUserBean> mList) {
                //todo 将点击到的用户信息传到下一个activity
                Intent intent = new Intent(UserSearchActivity.this, AddFriendDetailsActivity.class);
                SearchUserBean searchUserBean = null;
                if (mList.size() == 1) {
                    searchUserBean = mList.get(0);
                }
                intent.putExtra(Constants.INTENT_ACCOUNTINFO_SEARCH, searchUserBean);
                startActivityAnim(intent);
                finish();
            }
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
        userSearchRvAdapter.setDataList(null);
    }

    @Override
    public void afterTextChanged(Editable s) {
        test(s);
    }

    private void test(Editable s) {
        mPresenter.searchFriendByPhone(s.toString());
    }

    @Override
    public void onSearchFriendByPhoneOK(SearchUserBean bean) {
        ArrayList<SearchUserBean> list = new ArrayList<>();
        if (bean != null) {
            list.add(bean);
        }else{
            onSearchNull();
        }
        userSearchRvAdapter.setDataList(list);
        llSearchNull.setVisibility(View.GONE);
    }

    @Override
    public void onSearchNull() {
        llSearchNull.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean doExitActivityAnim() {
        return false;
    }
}
