package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.contract.FriendListByTagContract;
import com.yihuan.sharecalendar.ui.activity.friends.FriendListByTagtActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/10/4.
 */

public class FriendListByTagPresenter extends BasePresenter<FriendListByTagtActivity> implements FriendListByTagContract.Presenter {

    public FriendListByTagPresenter(FriendListByTagtActivity friendListByTagtActivity) {
        super(friendListByTagtActivity);
    }

    @Override
    public void getFriendListByTag(String tagId) {
        if(tagId == null){
            return;
        }

        Api.getFriendListByTag(tagId, bind(new MyObserver<List<FriendListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<FriendListBean> listBeen) {
                mView.showLoaddingView(false);
                mView.onGetFriendListByTagOK(listBeen);
            }
        }));
    }
}
