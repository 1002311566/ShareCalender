package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 好友搜索
 */

public interface FriendSearchContract {
    interface View extends BaseView{
        void onSearchFriendByNickNameOK(List<FriendListBean> list);
    }

    interface Presenter extends OtherPresenter {
        void searchFriendByNickName(String nickname);
    }
}
