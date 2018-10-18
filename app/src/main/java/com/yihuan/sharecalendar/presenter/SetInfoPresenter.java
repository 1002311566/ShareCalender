package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.SetInfoContract;
import com.yihuan.sharecalendar.ui.activity.login.SetInfoActivity;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/9.
 */

public class SetInfoPresenter extends BasePresenter<SetInfoActivity> implements SetInfoContract.Presenter {


    public SetInfoPresenter(SetInfoActivity setInfoActivity) {
        super(setInfoActivity);
    }

    @Override
    public void next(String sex, String age, String constellationId, int cityId) {
        //TODO 判断是否需要填写全部信息

        if(TextUtils.isEmpty(sex)){
            mView.showToast(App.getInstanse().getString(R.string.select_sex_null));
            return;
        }
        if(TextUtils.isEmpty(age)){
            mView.showToast(App.getInstanse().getString(R.string.select_year_null));
            return;
        }
        if(TextUtils.isEmpty(constellationId)){
            mView.showToast(App.getInstanse().getString(R.string.select_constellation_null));
            return;
        }
        if(cityId == 0){
            mView.showToast(App.getInstanse().getString(R.string.select_address_null));
            return;
        }

        mView.showLoaddingView(true);

        Api.editUserInfo(null, null, sex, age, constellationId, cityId+"", bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            public void onSucceed(@NonNull Object o) {
                mView.showLoaddingView(false);
                mView.setCompleted();
            }
        }));
    }
}
