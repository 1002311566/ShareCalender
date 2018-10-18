package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.presenter.contract.MoodLookContract;
import com.yihuan.sharecalendar.ui.activity.setting.SetMoodPermissionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/29.
 * 心情查看权限
 */

public class SetMoodPermissionPrensenter extends BasePresenter<SetMoodPermissionActivity> implements MoodLookContract.Presenter {
    public SetMoodPermissionPrensenter(SetMoodPermissionActivity setMoodPermissionActivity) {
        super(setMoodPermissionActivity);
    }

    @Override
    public void addLookFriendList(List<FriendListBean> selectFriendList) {
        if (selectFriendList == null || selectFriendList.size() <= 0) {
            return;
        }

        List<String> ids = new ArrayList<>();
        for (FriendListBean bean  : selectFriendList){
            ids.add(bean.getFriendId()+"");
        }
        mView.showLoaddingView(true);
        Api.addFriendLookMoodPermission(ids, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("保存成功！");
                mView.onAddLookFriendListOK();
            }
        }));



    }
}
