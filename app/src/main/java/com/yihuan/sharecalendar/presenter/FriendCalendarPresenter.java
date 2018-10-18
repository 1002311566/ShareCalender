package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.FriendCalendarContract;
import com.yihuan.sharecalendar.ui.activity.friends.FriendCalendarActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/10/8.
 */

public class FriendCalendarPresenter extends BasePresenter<FriendCalendarActivity> implements FriendCalendarContract.Presenter {

    public FriendCalendarPresenter(FriendCalendarActivity friendCalendarActivity) {
        super(friendCalendarActivity);
    }

    /**
     * 获取好友空闲状态
     * @param acceptUser
     * @param ym
     */
    @Override
    public void getFriendCalendar(String acceptUser, String ym) {
        if(acceptUser == null || ym == null)return;

//        mView.showLoaddingView(true);

        Api.getFriendCalendar(acceptUser, ym, bind(new MyObserver<List<String>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
//                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(List<String> list) {
//                mView.showLoaddingView(false);
                mView.onGetFriendCalendarOK(list);
            }
        }));
    }
}
