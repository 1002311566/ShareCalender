package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 跳转到指定日期
 */

public interface ToDateContract {
    interface View extends BaseView{

    }

    interface Presenter extends OtherPresenter {
        List<Integer> getYearData();
        List<Integer> getMonthData();
        List<Integer> getDayData(int year, int month);
        List<Integer> getHourData();
        List<Integer> getMinuteData();

        TimeBean getCurrentTimeBean();

        int getCurrentYearPosition();
        int getCurrentMonthPosition();
        int getCurrentDayPosition(TimeBean mSelectTime);
        int getCurrentHourPosition();
        int getCurrentMinutePosition();
        int getCurrentWeekPosition();
    }
}
