package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.FriendsContract;
import com.yihuan.sharecalendar.ui.fragment.FriendsFragment;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public class FriendPresenter extends BasePresenter<FriendsFragment> implements FriendsContract.Presenter {

    public FriendPresenter(FriendsFragment friendsFragment) {
        super(friendsFragment);
    }

    /**
     * 获取好友列表
     */
    @Override
    public void getFriendList() {
//        mView.showLoaddingView(true);
        Api.getFriendList(bind(new MyObserver<List<FriendListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
//                mView.showLoaddingView(false);
                mView.onCloseRefresh();
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<FriendListBean> list) {
//                mView.showLoaddingView(false);
                DBDao.getDao().saveFriendList(list);
                mView.onGetFriendListOK(DBDao.getDao().getFriendListFromLetterSort());
            }

            @Override
            protected void on401Failure() {
                super.on401Failure();
                mView.showNotLoginView();
            }
        }));
    }

    /**
     * 获取新朋友申请的提示数量
     */
    @Override
    public void getNewFriendApplyCount() {
        Api.getNewFriendApplyCount(bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Integer integer) {
                mView.onGetNewFriendApplyCountOK(integer);

            }
        }));
    }

    @Override
    public void getNewMsgCount() {
        Api.getNewMsgCount(bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Integer count) {
                mView.onGetNewMsgCount(count);
            }
        }));
    }
}
