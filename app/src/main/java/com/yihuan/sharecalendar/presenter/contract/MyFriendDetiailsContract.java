package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/7.
 */

public interface MyFriendDetiailsContract {

    interface View extends BaseView{
        void onGetFriendDetailsOK(MyFriendDetailBean bean);
        void onQueryFialure();
        void onQueryOK();
    }

    interface Presenter extends OtherPresenter {
        void getFriendDetails(String friendId);
        void queryFriendMood(String friendId);
    }

}
