package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.RegisterContract;
import com.yihuan.sharecalendar.ui.activity.login.RegisterActivity;
import com.yihuan.sharecalendar.utils.CheckUtils;
import com.yihuan.sharecalendar.utils.LogUtils;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/8.
 */

public class RegisterPresenter extends BasePresenter<RegisterActivity> implements RegisterContract.Presenter {

    public static final String TAG = "RegisterPresenter";

    public RegisterPresenter(RegisterActivity registerActivity) {
        super(registerActivity);
    }

    /**
     * 获取注册验证码
     *
     * @param bindPhone
     */
    @Override
    public void getCode(String bindPhone) {

        if (TextUtils.isEmpty(bindPhone)) {
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }
        mView.timeStart();
        mView.showLoaddingView(true);
        Api.getRegisterCode(bindPhone, bind(new MyObserver<Object>() {
            @Override
            public void onSucceed(@NonNull Object o) {
                LogUtils.e(TAG, "发送注册验证码成功------------------------");
                mView.showToast(App.getInstanse().getString(R.string.send_code_successful));
                mView.showLoaddingView(false);
            }

            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.timeEnd();
                mView.showLoaddingView(false);
            }
        }));
    }

    @Override
    public void register(String bindPhone, String validateCode, String password, String passwrod2, boolean isCheck) {

        if (TextUtils.isEmpty(bindPhone)) {
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }
        if (!CheckUtils.checkMobile(bindPhone)) {
            mView.showToast(App.getInstanse().getString(R.string.phone_num_error));
            return;
        }

        if (TextUtils.isEmpty(validateCode)) {
            mView.showToast(App.getInstanse().getString(R.string.code_null));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mView.showToast(App.getInstanse().getString(R.string.password_null));
            return;
        }
        if(!CheckUtils.checkPassword(password)){
            mView.showToast(App.getInstanse().getResources().getString(R.string.password_no_legal));
            return;
        }

        if (!password.equals(passwrod2)) {
            mView.showToast(App.getInstanse().getString(R.string.passwrod_no_same));
            return;
        }
        if(!isCheck){
            mView.showToast("请阅读软件注册协议");
            return;
        }
        mView.showLoaddingView(true);
        Api.register(bindPhone, validateCode, password, bind(new MyObserver<String>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(String o) {
                LogUtils.e(TAG, "注册成功-------");
                mView.showLoaddingView(false);
                mView.showToast("注册成功");
                mView.registerSuccessed(o);
            }
        }));

        //TODO------------------------------
//        mView.registerSuccessed();
        //TODO-------------------------------

    }
}
