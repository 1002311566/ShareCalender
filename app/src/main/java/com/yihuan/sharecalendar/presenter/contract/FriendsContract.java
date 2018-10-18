package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface FriendsContract {
    interface View extends BaseView{
        void onGetFriendListOK(List<FriendListBean> list);
        void onGetNewFriendApplyCountOK(Integer count);
        void onGetNewMsgCount(Integer count);
    }

    interface Presenter extends OtherPresenter {
        void getFriendList();
        void getNewFriendApplyCount();
        void getNewMsgCount();
    }
}
