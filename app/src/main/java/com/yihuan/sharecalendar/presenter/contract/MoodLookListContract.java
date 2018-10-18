package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface MoodLookListContract {
    interface View extends BaseView{
        void onGetPermissionFriendOK(List<FriendListBean> listBeen);
    }

    interface Presenter extends OtherPresenter {
        void getPermissionFriend();
    }
}
