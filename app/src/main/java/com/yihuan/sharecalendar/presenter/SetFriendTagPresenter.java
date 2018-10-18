package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.presenter.contract.SetFriendTagContract;
import com.yihuan.sharecalendar.ui.activity.friends.SetFriendTagActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 */

public class SetFriendTagPresenter extends BasePresenter<SetFriendTagActivity> implements SetFriendTagContract.Presenter {
    public SetFriendTagPresenter(SetFriendTagActivity setFriendTagActivity) {
        super(setFriendTagActivity);
    }

    /**
     * 移动好友到某些标签集
     * @param friendId
     * @param groupIdList 标签集
     */
    @Override
    public void moveFriendToTag(String friendId, List<String> groupIdList) {
        if(friendId == null || groupIdList == null){
            return;
        }

        Api.setFriendTag(friendId, groupIdList, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("设置成功！");
                mView.onMoveFriendToTagOK();
            }
        }));
    }

    @Override
    public void getTagList(String mFriendId) {
        mView.showLoaddingView(true);
        Api.getTagList(mFriendId, bind(new MyObserver<List<TagListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<TagListBean> listBeen) {
                mView.showLoaddingView(false);
                mView.onGetTagListOK(listBeen);
            }
        }));
    }
}
