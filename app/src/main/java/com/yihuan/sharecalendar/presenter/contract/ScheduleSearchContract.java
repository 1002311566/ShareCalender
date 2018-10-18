package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 新建活动
 */

public interface ScheduleSearchContract {
    interface View extends BaseView{
        void onSearchScheduleOK(List<ActiveBean> bean);
    }

    interface Presenter extends OtherPresenter {
        void searchSchedule(String title);
    }
}
