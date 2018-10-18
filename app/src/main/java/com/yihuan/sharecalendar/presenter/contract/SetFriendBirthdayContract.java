package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface SetFriendBirthdayContract {
    interface View extends BaseView{
        void onSetFriendBirthdayOK();
    }

    interface Presenter extends OtherPresenter {
        void setFriendBirthday(String friendId, String birthdayStr, String cnCalendarBirthdayStr, String isPermenentRemind);
    }
}
