package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.contract.EditUserInfoContract;
import com.yihuan.sharecalendar.ui.activity.setting.EditUserInfoActivity;

/**
 * Created by Ronny on 2017/9/11.
 * 修改个人信息
 */

public class EditUserInfoPresenter extends BasePresenter<EditUserInfoActivity> implements EditUserInfoContract.Presenter {
    public EditUserInfoPresenter(EditUserInfoActivity editUserInfoActivity) {
        super(editUserInfoActivity);
    }

    @Override
    public void getUserInfo() {
        Api.getUserInfo(bind(new MyObserver<UserBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(UserBean userBean) {
                mView.showLoaddingView(false);
                mView.onGetUserInfoOK(userBean);
            }
        }));
    }

    @Override
    public void editInfo(String signature, String nickname, String sex, String period, String constellation, String location) {
        mView.showLoaddingView(true);
        Api.editUserInfo(signature, nickname, sex, period, constellation, location, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("修改成功");
                mView.editInfoOK();
            }
        }));
    }
}
