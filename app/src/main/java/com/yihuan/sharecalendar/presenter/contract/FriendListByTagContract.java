package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 点击标签-》好友列表
 */

public interface FriendListByTagContract {
    interface View extends BaseView{
        void onGetFriendListByTagOK(List<FriendListBean> listBeen);
    }

    interface Presenter extends OtherPresenter {
        void getFriendListByTag(String tagId);
    }
}
