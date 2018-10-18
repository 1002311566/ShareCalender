package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;
import com.yihuan.sharecalendar.presenter.contract.NewFriendsContract;
import com.yihuan.sharecalendar.ui.activity.friends.NewFriendListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public class NewFriendPrensenter extends BasePresenter<NewFriendListActivity> implements NewFriendsContract.Presenter {

    private List<NewApplyListBean> mList;
    public NewFriendPrensenter(NewFriendListActivity newFriendListActivity) {
        super(newFriendListActivity);
        mList = new ArrayList<>();
    }

    @Override
    public void getNewFriendList(final int type) {
        mView.showLoaddingView(true);
        if (type == Constants.TYPE_REFRESH) {
            resetLastId();
        }
        Api.getNewApplyList(getLastId(), bind(new MyObserver<List<NewApplyListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<NewApplyListBean> beanList) {
                mView.showLoaddingView(false);
                if (beanList == null || (beanList != null && beanList.size() <= 0)) {
                    setNoMore();
                    return;
                }
                setLastId(beanList.get(beanList.size() - 1).getId());
                if (type == Constants.TYPE_REFRESH) {
                    mList.clear();
                    mList.addAll(beanList);
                } else if (type == Constants.TYPE_LOADMORE) {
                    mList.addAll(beanList);
                }
                mView.onGetNewFriendListOK(mList);
            }
        }));
    }

    @Override
    public void agreeApply(int userId, String proStatus) {
//        处理状态, 0: 未处理, 1: 接受, 2: 拒绝
        mView.showLoaddingView(true);
        Api.disposeNewApply(userId+"", proStatus, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onAgreeApplyOK();
            }
        }));
    }
}
