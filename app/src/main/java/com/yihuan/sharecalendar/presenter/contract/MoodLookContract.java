package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 心情权限设置
 */

public interface MoodLookContract {
    interface View extends BaseView{
        void onAddLookFriendListOK();
    }

    interface Presenter extends OtherPresenter {
        void addLookFriendList(List<FriendListBean> selectFriendList);
    }
}
