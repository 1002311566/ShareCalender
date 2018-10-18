package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;
import com.yihuan.sharecalendar.presenter.contract.AboutMeContract;
import com.yihuan.sharecalendar.ui.activity.setting.AboutMeActivity;

/**
 * Created by Ronny on 2017/9/30.
 */

public class AboutMePresenter extends BasePresenter<AboutMeActivity> implements AboutMeContract.Presenter {
    public AboutMePresenter(AboutMeActivity aboutMeActivity) {
        super(aboutMeActivity);
    }

    @Override
    public void getAboutMe() {
        mView.showLoaddingView(true);
        Api.getAboutMe(bind(new MyObserver<AboutMeBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(AboutMeBean bean) {
                mView.showLoaddingView(false);
                mView.onGetAboutMeOK(bean);
            }
        }));
    }
}
