package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface DayFragmentContract {
    interface View extends BaseView{
        void onGetScheduleListByDayOK(List<ActiveBean> bean);
    }

    interface Presenter extends OtherPresenter {
        void getScheduleListByDay(TimeBean timeBean);
    }
}
