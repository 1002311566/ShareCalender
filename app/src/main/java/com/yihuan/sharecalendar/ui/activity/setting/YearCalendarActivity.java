package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.ui.activity.active.SelectTimeActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.calendar.year.YearCalendarView;

import org.joda.time.DateTime;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/10/9.
 * 年视图
 */

public class YearCalendarActivity extends BaseActivity {
    @BindView(R.id.year_calendar)
    YearCalendarView yearCalendar;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText(new DateTime().getYear()+"");
        titleView.setRightText("今天");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                Intent intent = new Intent();
                intent.putExtra(SelectTimeActivity.SELECT_TIME, TimeUtils.getCurrentTimeBean());
                setResult(RESULT_OK, intent);
                sendHomeRefreshBroadCast();
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_year_calendar;
    }

    @Override
    protected void initView() {
        setListener();
    }

    private void setListener() {
        yearCalendar.setOnYearViewPageChangeListener(new YearCalendarView.OnYearViewPageChangeListener() {
            @Override
            public void onPage(int currentYear) {
                getTitleView().setCenterText(currentYear+"");
            }
        });
    }

    @Override
    protected void refreshData() {

    }

}
