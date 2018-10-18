package com.yihuan.sharecalendar.ui.fragment;

import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseFragment;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.presenter.SchedulePresenter;
import com.yihuan.sharecalendar.presenter.contract.ScheduleContract;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ScheduleRvAdater;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/19.
 * 日程
 */

public class ScheduleFragment extends BaseFragment<SchedulePresenter> implements ScheduleContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView recyclerView;
    private ScheduleRvAdater scheduleRvAdater;
    @BindView(R.id.ll_schedule_root_layout)
    LinearLayout llRootLayout;

    @Override
    protected BasePresenter setPresenter() {
        return new SchedulePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initView() {

        scheduleRvAdater = new ScheduleRvAdater();
        UIUtils.initLRecyclerView(getActivity(), recyclerView, scheduleRvAdater, false);
        setListener();

    }

    private void setListener() {
        scheduleRvAdater.setOnScheduleClickListener(new OnScheduleClickListener() {
            @Override
            public void onScheduleClick(int userId, int activeId) {
                //todo 根据活动发起者id来判断去哪个页面
                if(userId == App.getInstanse().getUserId()){
                    //todo 发起者界面
                    ActiveDetailsActivity_Publish.start(getActivity(), activeId);
                }else{
                    //todo 接受者界面
                    ActiveDetailsActivity_Receiver.start(getActivity(), activeId);
                }
            }
        });

        setLrecyclerView();
    }

    private void setLrecyclerView() {
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHomeData();
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getScheduleList(Constants.TYPE_LOADMORE);
            }
        });
    }


    @Override
    public void onNoMore() {
        recyclerView.setNoMore(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshHomeData();
        UIUtils.showTheme(getActivity(), llRootLayout);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void refreshHomeData() {
        recyclerView.setNoMore(false);
        mPresenter.getScheduleList(Constants.TYPE_REFRESH);
    }

    @Override
    public void onGetScheduleListOK(List<ActiveBean> bean) {
        if(bean == null)return;
        scheduleRvAdater.setDataList(bean);
        recyclerView.refreshComplete(10);
    }

    @Override
    public void onDeleteScheduleOK() {
        refreshHomeData();
    }

//    @Override
//    public void onCloseRefresh() {
//        super.onCloseRefresh();
//        recyclerView.refreshComplete(0);
//    }
}
