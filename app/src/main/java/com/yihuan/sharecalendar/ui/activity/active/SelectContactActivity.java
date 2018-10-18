package com.yihuan.sharecalendar.ui.activity.active;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
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
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SelectContactSearchRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/12.
 * 选择联系人
 * todo 所有好友 & 输入关键字搜索好友
 */

public class SelectContactActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_recyclerview)
    RecyclerView recyclerView;//todo 所有好友列表
    @BindView(R.id.tv_tag)
    TextView tvTag;//todo 通过标签搜索
    @BindView(R.id.rv_recyclerview_input)
    RecyclerView rvInput;//todo 通过输入过滤的好友
    @BindView(R.id.tv_all)
    TextView tvAllBtn;

    private SelectContactRvAdapter selectContactRvAdapter;//todo 所有好友适配器
    private ArrayList<FriendListBean> mSelectList;//todo 之前选择过的好友,通过intent传来
    private SelectContactSearchRvAdapter selectContactSearchRvAdapter;//todo 输入关键字搜索适配器
    private ArrayList<FriendListBean> allFriendList;//todo 所有好友,将引用给了两个适配器，保持对数据直接操作


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("选择联系人");
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
                //todo 确认选择
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST, allFriendList);
                intent.setAction(Constants.ACTION_SELECT_FRIEND);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact;
    }

    @Override
    protected void initView() {
        mSelectList = new ArrayList<>();
        //todo 全部好友列表
        selectContactRvAdapter = new SelectContactRvAdapter();
        UIUtils.initRecyclerView(this, recyclerView, selectContactRvAdapter, false);

        //todo 输入搜索
        selectContactSearchRvAdapter = new SelectContactSearchRvAdapter();
        UIUtils.initRecyclerView(this, rvInput, selectContactSearchRvAdapter, true);

        allFriendList = (ArrayList<FriendListBean>) DBDao.getDao().getFriendListFromLetterSort();

        if (getIntent() != null && getIntent().hasExtra("selectFriend")) {
            mSelectList = getIntent().getParcelableArrayListExtra("selectFriend");
            selectContactRvAdapter.setData(allFriendList, mSelectList);
        } else {
            selectContactRvAdapter.setDataList(allFriendList);
        }

        setListener();

    }

    private void setListener() {
        etSearch.addTextChangedListener(this);
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.tv_tag,R.id.tv_all})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_tag:
                //todo 通过标签选择
                SelectContactBySearchActivity.startSelfForResult(this, allFriendList, Constants.REQUEST_CODE_1);
                break;
            case R.id.tv_all:
                selectAll();

                break;
        }

    }

    private void selectAll() {
        if(rvInput.getVisibility() == View.GONE){
            selectContactRvAdapter.selectAll();
        }else{
            selectContactSearchRvAdapter.selectAll();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK) {
            this.finish();
        }
    }

    /**
     * 记录已选择的好友
     *
     * @param activity
     * @param friendList
     * @param requestCode
     */
    public static void startSelfForResult(Activity activity, List<FriendListBean> friendList, int requestCode) {
        Intent intent = new Intent(activity, SelectContactActivity.class);
        intent.putParcelableArrayListExtra("selectFriend", (ArrayList) friendList);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //todo 监听输入搜索
        if (s.length() <= 0) {
            if (recyclerView.getVisibility() != View.VISIBLE) {
                recyclerView.setVisibility(View.VISIBLE);
                selectContactRvAdapter.notifyDataSetChanged();
                rvInput.setVisibility(View.GONE);
            }
            return;
        }
        //todo 通过数据库搜索
        List<FriendListBean> listBeen = DBDao.getDao().queryFriendListByNickname(s.toString());
        selectContactSearchRvAdapter.setData(listBeen, allFriendList);
        if (rvInput.getVisibility() != View.VISIBLE) {
            rvInput.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
