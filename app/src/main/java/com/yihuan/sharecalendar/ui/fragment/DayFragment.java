package com.yihuan.sharecalendar.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BaseFragment;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.DayFragmentPresenter;
import com.yihuan.sharecalendar.presenter.contract.DayFragmentContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.DayRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;
import com.yihuan.sharecalendar.ui.view.NoScrollRecyclerView;
import com.yihuan.sharecalendar.ui.view.calendar.OnCalendarClickListener;
import com.yihuan.sharecalendar.ui.view.calendar.week.WeekCalendarView;
import com.yihuan.sharecalendar.utils.DateUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/6.
 */

public class DayFragment extends BaseFragment<DayFragmentPresenter> implements OnRvItemClickListener, DayFragmentContract.View {

    @BindView(R.id.wcvCalendar)
    WeekCalendarView wcvCalendar;
    @BindView(R.id.recyclerview)
    LRecyclerView recyclerView;
    @BindView(R.id.rv_fullday)
    NoScrollRecyclerView rvFullDay;
    @BindView(R.id.tv_lunar)
    TextView tvLunar;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.ll_day_root_layout)
    LinearLayout llRootLayout;

    private DayRvAdapter dayRvAdapter;


    private List<ScheduleListBean.ActivityListBean> mList;
    private TimeBean mTimeBean;

    @Override
    protected BasePresenter setPresenter() {
        return new DayFragmentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        //todo 第一次进入时获取当天的数据
        mTimeBean = TimeUtils.getCurrentTimeBean();
        showLunar();
        recyclerView.setPullRefreshEnabled(false);
        dayRvAdapter = new DayRvAdapter();
        UIUtils.initLRecyclerView(getActivity(), recyclerView, dayRvAdapter, false);
        setListener();

        loadDay();
    }

    @Override
    public void onResume() {
        super.onResume();
        //todo 刷新日期
        toDate(App.getInstanse().getCurrentTime());
        //todo 暂时在这里先显示皮肤
        UIUtils.showTheme(getActivity(), llRootLayout);
    }


    protected void toDate(TimeBean timeBean) {
        if (timeBean == null) return;
        if (mTimeBean.equalsYMD(timeBean)) return;
        wcvCalendar.toYMD(timeBean.getYear(), timeBean.getMonth(), timeBean.getDay());
    }

    private void setListener() {
        wcvCalendar.setOnCalendarClickListener(new OnCalendarClickListener() {
            @Override
            public void onClickDate(int year, int month, int day) {
                mTimeBean.setYMD(year, month, day);
                App.getInstanse().setCurrentTime(mTimeBean);
                showLunar();
                loadDay();
            }

            @Override
            public void onPageChange(int year, int month, int day) {
                mTimeBean.setYMD(year, month, day);
                loadDay();
            }
        });

        dayRvAdapter.setOnRvItemClickListener(new OnScheduleClickListener() {
            @Override
            public void onScheduleClick(int userId, int activeId) {
                //todo 根据活动发起者id来判断去哪个页面
                if (userId == App.getInstanse().getUserId()) {
                    //todo 发起者界面
                    ActiveDetailsActivity_Publish.start(getActivity(), activeId);
                } else {
                    //todo 接受者界面
                    ActiveDetailsActivity_Receiver.start(getActivity(), activeId);
                }
            }
        });
    }

    private void showLunar() {
        if (mTimeBean == null) return;
        tvLunar.setText(mTimeBean.getLunar().toString());
        int days = DateUtils.gapDay(mTimeBean, null);
        if (days == 0) {
            tvDays.setText("今天");
            return;
        }
        tvDays.setText(days > 0 ? Math.abs(days) + "天前" : Math.abs(days) + "天后");
    }

    private void loadDay() {
        ((MainActivity) getActivity()).showTitleDate(mTimeBean);
        mList.clear();
        mPresenter.getScheduleListByDay(mTimeBean);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void refreshHomeData() {
        loadDay();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position, List mList) {
        showToast(position + "项被点击");
    }

    @Override
    public void onGetScheduleListByDayOK(List<ActiveBean> bean) {
        if (bean == null) return;
        dayRvAdapter.setDataList(bean);
    }

}
