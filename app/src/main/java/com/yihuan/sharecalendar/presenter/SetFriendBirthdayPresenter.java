package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.ApplyFriendsContract;
import com.yihuan.sharecalendar.presenter.contract.SetFriendBirthdayContract;
import com.yihuan.sharecalendar.ui.activity.friends.SetFriendBirthdayActivity;

/**
 * Created by Ronny on 2017/9/26.
 */

public class SetFriendBirthdayPresenter extends BasePresenter<SetFriendBirthdayActivity> implements SetFriendBirthdayContract.Presenter {

    public SetFriendBirthdayPresenter(SetFriendBirthdayActivity setFriendBirthdayActivity) {
        super(setFriendBirthdayActivity);
    }

    @Override
    public void setFriendBirthday(String friendId, String birthdayStr, String cnCalendarBirthdayStr, String isPermenentRemind) {
        mView.showLoaddingView(true);
        Api.setFriendBirthday(friendId, birthdayStr, cnCalendarBirthdayStr,isPermenentRemind,bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onSetFriendBirthdayOK();
            }
        }));
    }
}
