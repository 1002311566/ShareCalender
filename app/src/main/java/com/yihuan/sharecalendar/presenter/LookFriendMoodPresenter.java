package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.presenter.contract.LookFriendMoodContract;
import com.yihuan.sharecalendar.ui.activity.friends.LookFriendMoodActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/9/30.
 */

public class LookFriendMoodPresenter extends BasePresenter<LookFriendMoodActivity> implements LookFriendMoodContract.Presenter {
    public LookFriendMoodPresenter(LookFriendMoodActivity lookFriendMoodActivity) {
        super(lookFriendMoodActivity);
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
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<MyMoodBean> bean) {
                mView.showLoaddingView(false);
                if(bean == null)return;
                mView.onQueryFriendMood(bean);
            }
        }));
    }

    /**
     * 查看好友一周心情指数
     *
     * @param friendId
     */
    @Override
    public void queryFriendWeekMood(String friendId) {
        Api.queryFriendWeekMood(friendId, bind(new MyObserver<WeekMoodBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(WeekMoodBean bean) {
                if(bean == null)return;
                mView.onQueryFriendWeekMood(bean);
            }
        }));
    }


}
