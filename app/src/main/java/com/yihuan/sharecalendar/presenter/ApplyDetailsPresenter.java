package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.ApplyDetailsContract;
import com.yihuan.sharecalendar.ui.activity.friends.ApplyDetailsActivity;

/**
 * Created by Ronny on 2017/12/9.
 */

public class ApplyDetailsPresenter extends BasePresenter<ApplyDetailsActivity> implements ApplyDetailsContract.Presenter {

    public ApplyDetailsPresenter(ApplyDetailsActivity applyDetailsActivity) {
        super(applyDetailsActivity);
    }

    @Override
    public void agreeApply(String userId, String proStatus) {
       //        处理状态, 0: 未处理, 1: 接受, 2: 拒绝
        mView.showLoaddingView(true);
        Api.disposeNewApply(userId+"", proStatus, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onAgreeApplyOK();
            }
        }));
    }
}
