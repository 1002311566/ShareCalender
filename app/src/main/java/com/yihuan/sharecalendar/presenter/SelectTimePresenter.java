package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.contract.SelectTimeContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectTimeActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/11/5.
 */

public class SelectTimePresenter extends BasePresenter<SelectTimeActivity> implements SelectTimeContract.Presenter {

    public SelectTimePresenter(SelectTimeActivity selectTimeActivity) {
        super(selectTimeActivity);
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
    public int getYearPosition(int year) {
        return TimeUtils.getYearPosition(year);
    }


    @Override
    public int getMonthPosition(int month) {
        return TimeUtils.getMonthPosition(month);
    }

    @Override
    public int getDayPosition(TimeBean timeBean) {
        return TimeUtils.getDayPosition(timeBean);
    }

    @Override
    public int getHourPosition(int hour) {
        return TimeUtils.getHourPosition(hour);
    }

    @Override
    public int getMinutePosition(int minute) {
        return TimeUtils.getMinutePosition(minute);
    }

    @Override
    public int getCurrentWeekPosition() {
        return TimeUtils.getCurrentWeekPosition();
    }
}
