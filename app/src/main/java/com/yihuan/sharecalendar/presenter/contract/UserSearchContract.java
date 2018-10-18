package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface UserSearchContract {
    interface View extends BaseView{
        void onSearchFriendByPhoneOK(SearchUserBean list);
        void onSearchNull();
    }

    interface Presenter extends OtherPresenter {
        void searchFriendByPhone(String bindPhone);
    }
}
