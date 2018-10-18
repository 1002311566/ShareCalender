package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface SelectTimeContract {
    interface View extends BaseView{

    }

    interface Presenter extends OtherPresenter {
        List<Integer> getYearData();
        List<Integer> getMonthData();
        List<Integer> getDayData(int year, int month);
        List<Integer> getHourData();
        List<Integer> getMinuteData();

        TimeBean getCurrentTimeBean();

        int getYearPosition(int year);
        int getMonthPosition(int month);
        int getDayPosition(TimeBean timeBean);
        int getHourPosition(int hour);
        int getMinutePosition(int minute);
        int getCurrentWeekPosition();
    }
}
