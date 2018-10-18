package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.contract.CreateTagContract;
import com.yihuan.sharecalendar.ui.activity.friends.CreateTagActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/28.
 */

public class CreateTagPresenter extends BasePresenter<CreateTagActivity> implements CreateTagContract.Presenter {
    public CreateTagPresenter(CreateTagActivity createTagActivity) {
        super(createTagActivity);
    }

    /**
     * 创建新标签
     * @param tagName
     * @param selectFriendList
     */
    @Override
    public void save(String tagName, List<FriendListBean> selectFriendList) {
        if(TextUtils.isEmpty(tagName)){
            mView.showToast(App.getInstanse().getString(R.string.tag_name_null));
            return;
        }

        List<FriendBean> friendIdList = new ArrayList<>();
        for (FriendListBean friend : selectFriendList){
            friendIdList.add(new FriendBean( friend.getFriendId() + ""));
        }

        mView.showLoaddingView(true);
        Api.createTag(tagName, friendIdList, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("保存成功！");
                mView.showLoaddingView(false);
                mView.onSaveOK();
            }
        }));

    }

    /**
     * 获取标签下好友
     * @param tagId
     */
    @Override
    public void getFriendsByTag(Integer tagId) {
        if(tagId == null){
            return;
        }

        Api.getFriendListByTag(tagId.toString(), bind(new MyObserver<List<FriendListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<FriendListBean> listBeen) {
                mView.showLoaddingView(false);
                mView.onGetFriendsByTagOK(listBeen);
            }
        }));
    }

    /**
     * 删除标签
     */
    @Override
    public void deleteTag(Integer tagId) {
        if(tagId == null)return;

        Api.deleteTag(tagId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("删除成功");
                mView.onDeleteTagOK();
            }
        }));
    }

    @Override
    public void editTag(Integer tagId, String tagName, List<FriendListBean> selectFriendList) {
        if(tagId == null)return;

        if(TextUtils.isEmpty(tagName)){
            mView.showToast(App.getInstanse().getString(R.string.tag_name_null));
            return;
        }

        List<FriendBean> friendIdList = new ArrayList<>();
        for (FriendListBean friend : selectFriendList){
                friendIdList.add(new FriendBean( friend.getFriendId() + ""));
        }

        Api.editTag(tagId, tagName, friendIdList, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onEditTagOK();
            }
        }));
    }
}
