package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.CreateTagPresenter;
import com.yihuan.sharecalendar.presenter.contract.CreateTagContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectContactActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareFriendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/25.
 * 新建标签 & 编辑标签
 */

public class CreateTagActivity extends BaseActivity<CreateTagPresenter> implements CreateTagContract.View {
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.et_tag_name)
    EditText etTagName;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.iv_clean)
    ImageView ivClean;

    private List<FriendListBean> mSelectList;//添加的好友对象
    private CreateTagReceiver receiver;
    private ShareFriendRvAdapter shareFriendRvAdapter;
    private Integer tagId;

    @Override
    protected BasePresenter setPresenter() {
        return new CreateTagPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("新建标签");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo save
                String tagName = etTagName.getText().toString().trim();
                if (tagId != null && tagId != -1) {
                    mPresenter.editTag(tagId, tagName, mSelectList);
                } else {
                    //todo add friendIdList
                    mPresenter.save(tagName, mSelectList);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_tag;
    }

    public static void startSelf(String tagName, Integer tagId, Activity activity) {
        Intent intent = new Intent(activity, CreateTagActivity.class);
        intent.putExtra("tagName", tagName);
        intent.putExtra("tagId", tagId);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {

        showSoftKey();
        mSelectList = new ArrayList<>();

        shareFriendRvAdapter = new ShareFriendRvAdapter();
        rvRecyclerview.setAdapter(shareFriendRvAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvRecyclerview.setLayoutManager(gridLayoutManager);

        //todo 编辑标签
        if (getIntent() != null) {
            String tagName = getIntent().getStringExtra("tagName");
            tagId = getIntent().getIntExtra("tagId", -1);
            if (tagId != null && tagId != -1) {
                getTitleView().setCenterText("编辑标签");
                btnDelete.setVisibility(View.VISIBLE);
            }
            etTagName.setText(StringUtils.nullToStr(tagName));
            //todo 获取标签下的好友列表
            mPresenter.getFriendsByTag(tagId);
        }

        setListener();
        registSelectFriendReceiver();
    }

    private void registSelectFriendReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SELECT_FRIEND);
        receiver = new CreateTagReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void setListener() {
        //todo 添加共享好友监听
        shareFriendRvAdapter.setOnRvItemClickListener3(new OnRvItemAddAndDeleteListener<FriendListBean>() {
            @Override
            public void onAddClick() {
                SelectContactActivity.startSelfForResult(CreateTagActivity.this, mSelectList, Constants.REQUEST_CODE_1);
            }

            @Override
            public void onDeleteClick(List<FriendListBean> mList, int position) {
                mSelectList.remove(position);
                shareFriendRvAdapter.setDataList(mSelectList);
            }
        });
    }

    @Override
    protected void refreshData() {

    }


    @OnClick({R.id.btn_delete, R.id.iv_clean})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_delete:
                mPresenter.deleteTag(tagId);
                break;
            case R.id.iv_clean:
                etTagName.setText("");
                break;
        }
    }

    @Override
    public void onSaveOK() {
        finish();
    }

    /**
     * 标签下的所有好友
     */
    @Override
    public void onGetFriendsByTagOK(List<FriendListBean> listBeen) {
        mSelectList.addAll(listBeen);//todo 编辑标签下的好友
        shareFriendRvAdapter.setDataList(mSelectList);
    }

    @Override
    public void onDeleteTagOK() {
        this.finish();
    }

    @Override
    public void onEditTagOK() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class CreateTagReceiver extends BroadcastReceiver {

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
                    shareFriendRvAdapter.setDataList(mSelectList);
                }
            }
        }
    }
}
