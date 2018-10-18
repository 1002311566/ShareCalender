package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.presenter.contract.MyFriendDetiailsContract;
import com.yihuan.sharecalendar.ui.activity.friends.MyFriendDetailsActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/9/28.
 */

public class MyFriendDetailsPresenter extends BasePresenter<MyFriendDetailsActivity> implements MyFriendDetiailsContract.Presenter {
    public MyFriendDetailsPresenter(MyFriendDetailsActivity myFriendDetailsActivity) {
        super(myFriendDetailsActivity);
    }

    @Override
    public void getFriendDetails(String friendId) {

        mView.showLoaddingView(true);
        Api.getFriendDetails(friendId, bind(new MyObserver<MyFriendDetailBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(MyFriendDetailBean myFriendDetailBean) {
                mView.showLoaddingView(false);
                mView.onGetFriendDetailsOK(myFriendDetailBean);
            }
        }));
    }

    /**
     * 获取好友心情指数
     *
     * @param friendId
     */
    @Override
    public void queryFriendMood(String friendId) {
        if (TextUtils.isEmpty(friendId))
            return;

        mView.showLoaddingView(true);
        Api.queryFriendMood(friendId, bind(new MyObserver<List<MyMoodBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.onQueryFialure();//查询失败
            }

            @Override
            protected void onSucceed(List<MyMoodBean> bean) {
                mView.showLoaddingView(false);
                mView.onQueryOK();
//                mView.onQueryFialure();//查询失败
            }
        }));
    }
}
