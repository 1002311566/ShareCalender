package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.List;


/**
 * Created by Ronny on 2017/9/8.
 * 活动详情-发起者
 */

public interface ActiveDetailsContract_Publish {
    interface View extends BaseView{
        void onGetActiveDetailsOK(ActiveDetailsBean bean);
        void onDeleteScheduleOK();
        void onResendBellOK();
        void onResendMsgOK();
        void onAddFriendsOK();
        void onCancleFriendByActiveOK();
        void onRefuseApplyOK();
        void onAgreeApplyOK();
        void get_active_share_id_OK(Integer id);
    }

    interface Presenter extends OtherPresenter {
        void getActiveDetails(int activityId);
        void deleteSchedule(ActiveBean activeId);
        void resendBell(int activityId);
        void resendMsgAll(int activityId);
        void resendMsg(int activityId, String inviteUser);
        void addFriendsByActive(ActiveBean id, List<FriendListBean> listBeen);
        void cancleFriendByActive(ActiveBean activeBean, int inviteUser);
        void refuseApply(String inviteId);
        void agreeApply(String inviteId);
        void get_active_share_id(Integer activeId);
    }
}
