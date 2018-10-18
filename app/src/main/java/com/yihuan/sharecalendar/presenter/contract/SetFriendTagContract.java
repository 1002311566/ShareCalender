package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface SetFriendTagContract {
    interface View extends BaseView{
        void onMoveFriendToTagOK();
        void onGetTagListOK(List<TagListBean> listBeen);
    }

    interface Presenter extends OtherPresenter {
        void moveFriendToTag(String friendId, List<String> groupId);
        void getTagList(String mFriendId);
    }
}
