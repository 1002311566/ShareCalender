package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface FriendCalendarContract {
    interface View extends BaseView{
        void onGetFriendCalendarOK(List<String> list);
    }

    interface Presenter extends OtherPresenter {
        void getFriendCalendar(String acceptUser, String ym);
    }
}
