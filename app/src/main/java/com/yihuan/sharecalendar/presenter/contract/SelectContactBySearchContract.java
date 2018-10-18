package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 */

public interface SelectContactBySearchContract {
    interface View extends BaseView{
        void onGetTagListOK(List<TagListBean> listBeen);
        void onSearchFriendByNicknameOK(List<FriendListBean> listBeen);
        void onSearchTagOK(List<TagListBean> listBeen);
    }

    interface Presenter extends OtherPresenter{
        void getTagList();
        void searchFriendByNickname(String nickname);
        void searchTag(String title);
    }
}
