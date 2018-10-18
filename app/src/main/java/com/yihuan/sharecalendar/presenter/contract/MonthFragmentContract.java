package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.sql.Time;
import java.util.List;

/**
 * Created by Ronny on 2017/10/2.
 */

public interface MonthFragmentContract {
    interface View extends BaseView{
        void onGetUserInfoOK(UserBean userBean);
        void onGetScheduleByDayOk(List<ActiveBean> bean);
//        void onGetScheduleByMonthOk(ScheduleListBean bean);
    }

    interface Presenter extends OtherPresenter {
        void getUserInfo();
        void getScheduleByDay(TimeBean date);
//        void getScheduleByMonth(TimeBean date);
    }

}
