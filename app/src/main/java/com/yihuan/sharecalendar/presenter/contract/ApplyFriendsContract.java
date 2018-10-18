package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface ApplyFriendsContract {
    interface View extends BaseView{
        void onApplyFriendOK();
    }

    interface Presenter extends OtherPresenter {
        void applyFriend(String id, String message);
    }
}
