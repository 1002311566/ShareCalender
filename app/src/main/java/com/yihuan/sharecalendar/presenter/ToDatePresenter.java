package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.contract.ToDateContract;
import com.yihuan.sharecalendar.ui.activity.setting.ToDateActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/11/5.
 */

public class ToDatePresenter extends BasePresenter<ToDateActivity> implements ToDateContract.Presenter {


    public ToDatePresenter(ToDateActivity toDateActivity) {
        super(toDateActivity);
    }

    @Override
    public List<Integer> getYearData() {
        return TimeUtils.getYearData();
    }

    @Override
    public List<Integer> getMonthData() {
      return TimeUtils.getMonthData();
    }

    @Override
    public List<Integer> getDayData(int year, int month) {
        return TimeUtils.getDays(year, month);
    }

    @Override
    public List<Integer> getHourData() {
       return TimeUtils.getHourData();
    }

    @Override
    public List<Integer> getMinuteData() {
      return TimeUtils.getMinuteData();
    }

    @Override
    public TimeBean getCurrentTimeBean() {
        return TimeUtils.getCurrentTimeBean();
    }


    @Override
    public int getCurrentYearPosition() {
        return TimeUtils.getCurrentYearPosition();
    }

    @Override
    public int getCurrentMonthPosition() {
        return TimeUtils.getCurrentMonthPosition();
    }

    @Override
    public int getCurrentDayPosition(TimeBean mSelectTime) {
        return TimeUtils.getDayPosition(mSelectTime);
    }

    @Override
    public int getCurrentHourPosition() {
        return TimeUtils.getCurrentHourPosition();
    }

    @Override
    public int getCurrentMinutePosition() {
        return TimeUtils.getCurrentMinutePosition();
    }

    @Override
    public int getCurrentWeekPosition() {
        return TimeUtils.getCurrentWeekPosition();
    }
}
