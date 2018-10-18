package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.SetNewPaswordContract;
import com.yihuan.sharecalendar.ui.activity.setting.SetNewPasswordActivity;
import com.yihuan.sharecalendar.utils.CheckUtils;
import com.yihuan.sharecalendar.utils.LogUtils;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/10.
 */

public class SetNewPasswordPresenter extends BasePresenter<SetNewPasswordActivity> implements SetNewPaswordContract.Presenter {


    public SetNewPasswordPresenter(SetNewPasswordActivity setNewPasswordActivity) {
        super(setNewPasswordActivity);
    }

    @Override
    public void setNewPassword(String bindPhone, String code, String password1, String password2) {
        if(TextUtils.isEmpty(bindPhone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }
        if(!CheckUtils.checkMobile(bindPhone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_error));
            return;
        }
        if(TextUtils.isEmpty(code)){
            mView.showToast(App.getInstanse().getString(R.string.code_null));
            return;
        }
        if(TextUtils.isEmpty(password1)){
            mView.showToast(App.getInstanse().getString(R.string.password_null));
            return;
        }

        if(!CheckUtils.checkPassword(password1)){
            mView.showToast(App.getInstanse().getResources().getString(R.string.password_no_legal));
            return;
        }


        if(password2 != null && !password1.equals(password2)){
            mView.showToast(App.getInstanse().getString(R.string.passwrod_no_same));
            return;
        }

        mView.showLoaddingView(true);
        Api.checkResetPsdCode(bindPhone, code, password1, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            public void onSucceed(@NonNull Object o) {
                mView.showLoaddingView(false);
                mView.showToast("修改成功");
                mView.onSetNewPasswrodOK();
            }
        }));

    }

    @Override
    public void getCellPhoneCode(String phone) {
        if(TextUtils.isEmpty(phone)){
            return;
        }

        mView.startTime();
        mView.showLoaddingView(true);
        Api.getResetPsdCode(phone, bind(new MyObserver<Object>() {
            @Override
            public void onSucceed(@NonNull Object o) {
                LogUtils.e(TAG, "发送重置密码验证码成功------------------------");
                mView.showLoaddingView(false);
                mView.showToast(App.getInstanse().getString(R.string.send_code_successful));
            }

            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
                mView.stopTime();
            }
        }));
    }
}
