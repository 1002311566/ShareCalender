package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 日程
 */

public interface ScheduleContract {
    interface View extends BaseView{
        void onGetScheduleListOK(List<ActiveBean> bean);
        void onDeleteScheduleOK();
    }

    interface Presenter extends OtherPresenter {
        void getScheduleList(int type);
        void deleteSchedule(int activeId);
    }
}
