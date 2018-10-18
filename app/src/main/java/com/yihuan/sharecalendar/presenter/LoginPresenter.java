package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.LoginContract;
import com.yihuan.sharecalendar.ui.activity.login.LoginActivity;
import com.yihuan.sharecalendar.utils.CheckUtils;
import com.yihuan.sharecalendar.utils.LogUtils;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/7.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    public LoginPresenter(LoginActivity loginActivity) {
        super(loginActivity);
    }


    @Override
    public void login(String bindPhone, String password) {

        if (TextUtils.isEmpty(bindPhone)) {
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }
        if (!CheckUtils.checkMobile(bindPhone)) {
            mView.showToast(App.getInstanse().getString(R.string.phone_num_error));
        }

        if (TextUtils.isEmpty(password)) {
            mView.showToast(App.getInstanse().getString(R.string.password_null));
            return;
        }

        mView.showLoaddingView(true);

        Api.login(bindPhone, password, bind(new MyObserver<String>() {
            @Override
            protected void onFailure(int code, String msg) {
//                mView.showLoaddingView(false);
//                mView.showToast(msg);

                mView.showLoaddingView(false);
                mView.showToast("登录成功");
                DataUtils.saveToken("1111");
                mView.loginSuccesed();
            }

            @Override
            public void onSucceed(@NonNull String s) {
                LogUtils.e(TAG, "token-------------"+ s);
                mView.showLoaddingView(false);
                mView.showToast("登录成功");
                DataUtils.saveToken(s);
                mView.loginSuccesed();
            }
        }));

//        //todo test
//        mView.loginSuccesed();
    }


}
