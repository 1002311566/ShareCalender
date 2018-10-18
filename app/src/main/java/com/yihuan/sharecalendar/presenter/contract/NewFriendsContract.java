package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 新的朋友
 */

public interface NewFriendsContract {
    interface View extends BaseView{
        void onGetNewFriendListOK(List<NewApplyListBean> list);
        void onAgreeApplyOK();
    }

    interface Presenter extends OtherPresenter {
        void getNewFriendList(int typeRefresh);
//        处理状态, 0: 未处理, 1: 接受, 2: 拒绝
        void agreeApply(int userId, String proStatus);
    }
}
