package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/26.
 * 我的好友》详情》设置
 */

public interface MyFriendSettingContract {
    interface View extends BaseView{
        void onDeleteOK();
    }

    interface Presenter extends OtherPresenter {
        void deleteFriend(String friendId);
    }
}
