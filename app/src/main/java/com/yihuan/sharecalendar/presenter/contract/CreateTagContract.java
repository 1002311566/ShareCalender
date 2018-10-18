package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/8.
 */

public interface CreateTagContract {
    interface View extends BaseView{
        void onSaveOK();
        void onGetFriendsByTagOK(List<FriendListBean> listBeen);
        void onDeleteTagOK();
        void onEditTagOK();
    }

    interface Presenter extends OtherPresenter {
        void save(String tagName, List<FriendListBean> selectFriendList);
        void getFriendsByTag(Integer tagId);
        void deleteTag(Integer tagId);
        void editTag(Integer tagId, String tagName, List<FriendListBean> selectFriendList);
    }
}
