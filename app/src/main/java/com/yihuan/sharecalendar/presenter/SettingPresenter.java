package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.SettingContract;
import com.yihuan.sharecalendar.ui.activity.setting.SettingActivity;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/12.
 */

public class SettingPresenter extends BasePresenter<SettingActivity> implements SettingContract.Presenter {

    public SettingPresenter(SettingActivity settingActivity) {
        super(settingActivity);
    }

    @Override
    public void logout() {
        Api.logout(bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            public void onSucceed(@NonNull Object o) {
                mView.showToast("退出成功");
                mView.logOutOK();
            }
        }));
    }
}
