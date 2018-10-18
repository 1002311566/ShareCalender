package com.yihuan.sharecalendar.ui.activity.active;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.presenter.SelectContactBySearchPresenter;
import com.yihuan.sharecalendar.presenter.contract.SelectContactBySearchContract;
import com.yihuan.sharecalendar.ui.activity.friends.FriendListByTagtActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactBySearchAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactSearchRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/28.
 * 选择联系人2——》通过标签选择
 */

public class SelectContactBySearchActivity extends BaseActivity<SelectContactBySearchPresenter> implements TextWatcher, SelectContactBySearchContract.View {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_tag_tab)
    TextView tvTab;
    @BindView(R.id.rv_recyclerview_tag)
    RecyclerView recyclerViewTag;//标签列表

    private SelectContactBySearchAdapter adpaterTag;
    private ArrayList<FriendListBean> allFriendList;

    @Override
    protected BasePresenter setPresenter() {
        return new SelectContactBySearchPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("选择联系人");
        titleView.setLeftText("取消");
        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact_by_search;
    }

    @Override
    protected void initView() {
        adpaterTag = new SelectContactBySearchAdapter();
        recyclerViewTag.setAdapter(adpaterTag);
        recyclerViewTag.setLayoutManager(new GridLayoutManager(this, 4));
        etSearch.addTextChangedListener(this);

        if(getIntent() != null && getIntent().hasExtra("allFriendList")){
            allFriendList = getIntent().getParcelableArrayListExtra("allFriendList");
        }
        refreshData();
        setListener();
    }

    private void setListener() {
        adpaterTag.setOnRvItemClickListener(
                new OnRvItemClickListener<SelectContactBySearchAdapter.ItemViewHolder, TagListBean>() {
                    @Override
                    public void onItemClick(SelectContactBySearchAdapter.ItemViewHolder holder, int position, List<TagListBean> mList) {
                        //todo 跳转到标签页
                        FriendListByTagtActivity.startSelfForResult(SelectContactBySearchActivity.this,
                                mList.get(position).getGroupName(),mList.get(position).getId()+"",
                                allFriendList, Constants.REQUEST_CODE_1);
                    }
                });

    }


    @Override
    protected void refreshData() {
        mPresenter.getTagList();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() <= 0) {
            mPresenter.getTagList();
            return;
        }
        mPresenter.searchTag(s.toString());

    }

    @Override
    public void onGetTagListOK(List<TagListBean> listBeen) {
        adpaterTag.setDataList(listBeen);
    }

    @Override
    public void onSearchFriendByNicknameOK(List<FriendListBean> listBeen) {
//        adapterList.setDataList(listBeen);
    }

    @Override
    public void onSearchTagOK(List<TagListBean> listBeen) {
        adpaterTag.setDataList(listBeen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            this.finish();
        }
    }

    public static void startSelfForResult(Activity activity, ArrayList<FriendListBean> allFriendList, int requestCode) {
        Intent intent = new Intent(activity, SelectContactBySearchActivity.class);
        intent.putParcelableArrayListExtra("allFriendList", allFriendList);
        activity.startActivityForResult(intent, requestCode);
    }
}
