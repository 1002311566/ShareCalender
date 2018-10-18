package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.MyFriendSettingContract;
import com.yihuan.sharecalendar.ui.activity.friends.MyFriendSettingActivity;

/**
 * Created by Ronny on 2017/9/29.
 */

public class MyFriendSettingPresenter extends BasePresenter<MyFriendSettingActivity> implements MyFriendSettingContract.Presenter {
    public MyFriendSettingPresenter(MyFriendSettingActivity myFriendSettingActivity) {
        super(myFriendSettingActivity);
    }

    @Override
    public void deleteFriend(String friendId) {
        mView.showLoaddingView(true);
        Api.deleteFriend(friendId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("删除成功！");
                mView.onDeleteOK();
            }
        }));
    }
}
