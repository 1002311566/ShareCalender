package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.contract.MoodLookListContract;
import com.yihuan.sharecalendar.ui.activity.setting.MoodLookListActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/12/9.
 */

public class MoodLookListPresenter extends BasePresenter<MoodLookListActivity> implements MoodLookListContract.Presenter {


    public MoodLookListPresenter(MoodLookListActivity moodLookListActivity) {
        super(moodLookListActivity);
    }

    @Override
    public void getPermissionFriend() {

        mView.showLoaddingView(true);
        Api.getPermissionFriend("1", bind(new MyObserver<List<FriendListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<FriendListBean> listBeen) {
                mView.showLoaddingView(false);
                mView.onGetPermissionFriendOK(listBeen);
            }
        }));
    }
}
