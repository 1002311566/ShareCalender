package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface AddRemindContract {
    interface View extends BaseView{
        void onAddRemindOK();
        void onGetRemindDetails(RemindDetails remindDetails);
    }

    interface Presenter extends OtherPresenter {
        TimeBean getCurrentTimeBean();
        TimeBean getNextDayTimeBean();
        void addRemind(RemindBean remindBean);
        void getRemindDetails(Integer taskReminderId);//获取定制化提醒详情
    }
}
