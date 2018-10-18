package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.ApplyFriendsContract;
import com.yihuan.sharecalendar.ui.activity.friends.ApplyFriendActivity;

/**
 * Created by Ronny on 2017/9/26.
 */

public class ApplyFriendPresenter extends BasePresenter<ApplyFriendActivity> implements ApplyFriendsContract.Presenter {

    public ApplyFriendPresenter(ApplyFriendActivity applyFriendActivity) {
        super(applyFriendActivity);
    }

    @Override
    public void applyFriend(String id, String message) {
        mView.showLoaddingView(true);
        Api.applyFriend(id, message, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("请求已提交！");
                mView.onApplyFriendOK();
            }
        }));
    }
}
