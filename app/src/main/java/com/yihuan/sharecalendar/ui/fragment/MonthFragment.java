package com.yihuan.sharecalendar.ui.fragment;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BaseFragment;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.MonthFragmentPresenter;
import com.yihuan.sharecalendar.presenter.contract.MonthFragmentContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.MonthRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.calendar.OnCalendarClickListener;
import com.yihuan.sharecalendar.ui.view.calendar.month.MonthCalendarView;
import com.yihuan.sharecalendar.ui.view.calendar.schedule.ScheduleLayout;
import com.yihuan.sharecalendar.ui.view.calendar.schedule.ScheduleRecyclerView;
import com.yihuan.sharecalendar.ui.view.calendar.week.WeekCalendarView;
import com.yihuan.sharecalendar.utils.DateUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Ronny on 2017/9/7.
 */

public class MonthFragment extends BaseFragment<MonthFragmentPresenter> implements MonthFragmentContract.View {

    @BindView(R.id.mcvCalendar)
    MonthCalendarView mcvCalendar;
    @BindView(R.id.wcvCalendar)
    WeekCalendarView wcvCalendar;
    @BindView(R.id.rvScheduleList)
    ScheduleRecyclerView rvScheduleList;
    @BindView(R.id.slSchedule)
    ScheduleLayout slSchedule;
    @BindView(R.id.tv_lunar)
    TextView tvLunar;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.ll_month_root_layout)
    LinearLayout llRootLayout;

    private MonthRvAdapter monthRvAdapter;
    private OnCalendarClickListener listener;
    private TimeBean mTimeBean;

    @Override
    protected BasePresenter setPresenter() {
        return new MonthFragmentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_month;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        //todo 第一次进入时获取当天的数据
        mTimeBean = TimeUtils.getCurrentTimeBean();
        showLunar();
        //todo 获取个人信息，将信息保存全局
        mPresenter.getUserInfo();
        monthRvAdapter = new MonthRvAdapter();
        UIUtils.initRecyclerView(getActivity(), rvScheduleList, monthRvAdapter, false);
        setViewListener();
        loadDay();
//        mPresenter.getScheduleByMonth(mTimeBean);
    }

    protected void toDate(TimeBean timeBean) {
        if (timeBean == null || mTimeBean.equalsYMD(timeBean)) return;
        slSchedule.initData(timeBean.getYear(), timeBean.getMonth(), timeBean.getDay());
    }

    @Override
    public void onResume() {
        super.onResume();
        //todo 刷新日期
        toDate(App.getInstanse().getCurrentTime());
        mPresenter.getUserInfo();
    }

    //todo 同步标题日期 & 获取当日日程
    private void loadDay() {
        ((MainActivity) getActivity()).showTitleDate(mTimeBean);
        mPresenter.getScheduleByDay(mTimeBean);
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

    private void setViewListener() {
        slSchedule.setOnCalendarClickListener(new OnCalendarClickListener() {
            @Override
            public void onClickDate(int year, int month, int day) {
                mTimeBean.setYMD(year, month, day);
                App.getInstanse().setCurrentTime(mTimeBean);
                showLunar();
                loadDay();
            }

            @Override
            public void onPageChange(int year, int month, int day) {
//                mTimeBean.setYMD(year, month, day);
//                mPresenter.getScheduleByMonth(mTimeBean);
//                loadDay();
            }
        });

        monthRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MonthRvAdapter.ItemViewHolder, ActiveBean>() {
            @Override
            public void onItemClick(MonthRvAdapter.ItemViewHolder holder, int position, List<ActiveBean> mList) {
                ActiveBean activeBean = mList.get(position);
                if (activeBean != null) {
                    //todo 根据活动发起者id来判断去哪个页面
                    int userId = mList.get(position).getPublish_user_id();
                    int activeId = mList.get(position).getActive_id();
                    if (userId == App.getInstanse().getUserId()) {
                        //todo 发起者界面
                        ActiveDetailsActivity_Publish.start(getActivity(), activeId);
                    } else {
                        //todo 接受者界面
                        ActiveDetailsActivity_Receiver.start(getActivity(), activeId);
                    }

                }
            }
        });
    }


    @Override
    protected void refreshData() {

    }

    @Override
    protected void refreshHomeData() {
        loadDay();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onGetUserInfoOK(UserBean userBean) {
        if (userBean == null) return;

        App.getInstanse().setUser(userBean);
        App.getInstanse().setThemePath(userBean.getSkinPath());
        UIUtils.showTheme(getActivity(), llRootLayout);
        ((MainActivity) getActivity()).showMyMood(userBean.getCurrentMood());
        //todo 保持实时刷新日程， 防止突发无网络不显示小点
        ScheduleManager.getInstance().resetCurrentMonth(mTimeBean.toYM());
        slSchedule.refreshUI();//todo 刷新点
        refreshHomeData();
    }

    /**
     * 获取到指定日期日程
     */
    @Override
    public void onGetScheduleByDayOk(List<ActiveBean> beanList) {
        monthRvAdapter.setDataList(beanList);
    }


}
