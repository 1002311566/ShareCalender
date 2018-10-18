package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.presenter.contract.MyMoodContract;
import com.yihuan.sharecalendar.ui.activity.hometitle.MyMoodActivity;

/**
 * Created by Ronny on 2017/11/23.
 */

public class MyMoodPresenter extends BasePresenter<MyMoodActivity> implements MyMoodContract.Presenter {
    public MyMoodPresenter(MyMoodActivity myMoodActivity) {
        super(myMoodActivity);
    }

    @Override
    public void getMyMood() {

        Api.getMyMood(bind(new MyObserver<MyMoodBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(MyMoodBean bean) {
                mView.onGetMyMoodOK(bean);
            }

            @Override
            protected void on401Failure() {
                super.on401Failure();
                mView.toLoginActivity();
            }
        }));
    }

    @Override
    public void setMyMood(final String mood) {
        mView.showLoaddingView(true);
        Api.setMyMood(mood, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Object o) {
//                mView.showToast("今日心情设置成功！");
                mView.showLoaddingView(false);
                mView.onSetMoodOK();
            }
        }));
    }

    /**
     * 获取一周心情
     */
    @Override
    public void getMyWeekMood() {
        Api.getMyWeekMood(bind(new MyObserver<WeekMoodBean>() {
            @Override
            protected void onFailure(int code, String msg) {

            }

            @Override
            protected void onSucceed(WeekMoodBean weekMoodBean) {
                if (weekMoodBean == null) return;
                mView.onGetMyWeekMood(weekMoodBean);
            }
        }));
    }


}
