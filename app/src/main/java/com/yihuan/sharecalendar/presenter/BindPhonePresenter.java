package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.login.PhoneBean;
import com.yihuan.sharecalendar.presenter.contract.BindPhoneContract;
import com.yihuan.sharecalendar.ui.activity.login.BindPhoneActivity;
import com.yihuan.sharecalendar.utils.CheckUtils;

/**
 * Created by Ronny on 2017/9/29.
 */

public class BindPhonePresenter extends BasePresenter<BindPhoneActivity> implements BindPhoneContract.Presenter {


    public BindPhonePresenter(BindPhoneActivity bindPhoneActivity) {
        super(bindPhoneActivity);
    }

    @Override
    public void getCode(String bindPhone) {
        if(TextUtils.isEmpty(bindPhone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }

        mView.timeStart();
        mView.showLoaddingView(true);
        Api.getBindCode(bindPhone, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.timeEnd();
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onGetCode();
            }

        }));
    }

    @Override
    public void bindWX(String cellphone, String password, String confimPwd, String code, String captcha) {
        if(TextUtils.isEmpty(cellphone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_null));
            return;
        }

        if(!CheckUtils.checkMobile(cellphone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_error));
            return;
        }
        if(TextUtils.isEmpty(code)){
            mView.showToast(App.getInstanse().getString(R.string.code_null));
            return;
        }
//        if(TextUtils.isEmpty(password)){
//            mView.showToast(App.getInstanse().getString(R.string.password_null));
//            return;
//        }
//        if(!password.equals(confimPwd)){
//            mView.showToast(App.getInstanse().getString(R.string.passwrod_no_same));
//            return;
//        }

        mView.showLoaddingView(true);
        Api.bindWX(cellphone, password, code, captcha , bind(new MyObserver<String>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(String token) {
                mView.showLoaddingView(false);
                DataUtils.saveToken(token);
                mView.onBindWXOK();
            }
        }));
    }

    /**
     * 检查用户是否存在
     * @param cellphone
     */
    @Override
    public void checkPhone(String cellphone) {

        Api.checkPhone(cellphone, bind(new MyObserver<PhoneBean>() {
            @Override
            protected void onFailure(int code, String msg) {

            }

            @Override
            protected void onSucceed(PhoneBean b) {
                mView.onCheckPhoneOK(b);
            }
        }));
    }
}
