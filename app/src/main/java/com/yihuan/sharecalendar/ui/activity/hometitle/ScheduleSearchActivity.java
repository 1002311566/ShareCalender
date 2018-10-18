package com.yihuan.sharecalendar.ui.activity.hometitle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.presenter.SearchSchedulePressenter;
import com.yihuan.sharecalendar.presenter.contract.ScheduleSearchContract;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ScheduleRvAdater;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/21.
 * 日程搜索（日程关键字）
 */

public class ScheduleSearchActivity extends BaseActivity<SearchSchedulePressenter> implements TextWatcher, ScheduleSearchContract.View{
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.iv_search_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerview)
    LRecyclerView recyclerView;
    private ScheduleRvAdater adpater;

    private String mSearchContent;

    @Override
    protected BasePresenter setPresenter() {
        return new SearchSchedulePressenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setVisibility(View.GONE);
        int stateBarHight = UIUtils.getStateBarHight(this);
        llTitle.setPadding(0, stateBarHight, 0, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        showSoftKey();
        etSearch.addTextChangedListener(this);
        adpater = new ScheduleRvAdater();
        UIUtils.initLRecyclerView(this, recyclerView, adpater, true);
        setListener();

        mPresenter.searchSchedule("");
    }

    private void setListener() {
        adpater.setOnScheduleClickListener(new OnScheduleClickListener() {
            @Override
            public void onScheduleClick(int userId, int activeId) {
                //todo 根据活动发起者id来判断去哪个页面
                if(userId == App.getInstanse().getUserId()){
                    //todo 发起者界面
                    ActiveDetailsActivity_Publish.start(ScheduleSearchActivity.this, activeId);
                }else{
                    //todo 接受者界面
                    ActiveDetailsActivity_Receiver.start(ScheduleSearchActivity.this, activeId);
                }
            }
        });

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.refreshComplete(10);
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.setNoMore(true);
                recyclerView.refreshComplete(10);
            }
        });
    }


    @Override
    protected void refreshData() {
        recyclerView.refreshComplete(10);
    }


    @OnClick(R.id.iv_search_back)
    public void onViewClicked() {
        finish();
    }


    //-------------------------text监听类---------------------------------------
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.searchSchedule(mSearchContent = s.toString());
    }
//--------------------------------------------------------------
    @Override
    public void onSearchScheduleOK(List<ActiveBean> bean) {
        //todo 搜索成功
        adpater.setDataList(bean);
    }
}
