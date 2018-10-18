package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.FriendCalendarPresenter;
import com.yihuan.sharecalendar.presenter.contract.FriendCalendarContract;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.calendar.OnCalendarClickListener;
import com.yihuan.sharecalendar.ui.view.calendar.month.MonthCalendarView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/12/16.
 * 好友空闲时间
 */

public class FriendCalendarActivity extends BaseActivity<FriendCalendarPresenter> implements FriendCalendarContract.View {

    @BindView(R.id.mcvCalendar)
    MonthCalendarView mcvCalendar;

    private TimeBean mTimeBean;
    private TitleView titleView;

    private String friendId;

    @Override
    protected BasePresenter setPresenter() {
        return new FriendCalendarPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        this.titleView = titleView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_calendar;
    }

    @Override
    protected void initView() {
        //todo 第一次进入时获取当天的数据
        mTimeBean = TimeUtils.getCurrentTimeBean();
        showTitleYM(mTimeBean.getYear(), mTimeBean.getMonth(), mTimeBean.getDay());
        setListener();
        if(getIntent() != null && getIntent().hasExtra("friendId")){
            friendId = getIntent().getStringExtra("friendId");
        }
        refreshData();
    }

    private void setListener() {
        mcvCalendar.setOnCalendarClickListener(new OnCalendarClickListener() {
            @Override
            public void onClickDate(int year, int month, int day) {
                showTitleYM(year, month, day);
            }

            @Override
            public void onPageChange(int year, int month, int day) {
                mTimeBean.setYMD(year,month,day);
                refreshData();
            }
        });
    }

    private void showTitleYM(int year, int month, int day) {
        mTimeBean.setYMD(year, month, day);
        titleView.setCenterText(mTimeBean.toYM());
    }

    public static void startSelf(Activity activity, String friendId){
        Intent intent = new Intent(activity, FriendCalendarActivity.class);
        intent.putExtra("friendId", friendId);
        activity.startActivity(intent);
    }

    @Override
    protected void refreshData() {
        if(friendId == null)return;

        mPresenter.getFriendCalendar(friendId, mTimeBean.toYM());
    }

    @Override
    public void onGetFriendCalendarOK(List<String> list) {
        mcvCalendar.setFriendCalendar(list);
    }
}
