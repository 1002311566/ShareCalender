package com.yihuan.sharecalendar.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.contract.ActiveDetailsContract_Receiver;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;

/**
 * Created by Ronny on 2017/11/7.
 */

public class ActiveDetailsPresenter_Receiver extends BasePresenter<ActiveDetailsActivity_Receiver> implements ActiveDetailsContract_Receiver.Presenter {


    public ActiveDetailsPresenter_Receiver(ActiveDetailsActivity_Receiver activeDetailsActivity_receiver) {
        super(activeDetailsActivity_receiver);
    }

    @Override
    public void getActiveDetails(int activityId) {
        if (activityId == -1) return;

        Api.getActiveDetails(activityId, bind(new MyObserver<ActiveDetailsBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(ActiveDetailsBean activeDetailsBean) {
                if (activeDetailsBean != null) {
                    mView.onGetActiveDetailsOK(activeDetailsBean);
                }
            }
        }));
    }

    /**
     * 参加活动
     *
     * @param activityId
     */
    @Override
    public void agreeActive(final ActiveBean activeBean) {
        if (activeBean.getActive_id() == -1) return;

        Api.agreeActive(activeBean.getActive_id(), bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Object o) {
                //todo 接受活动，写入数据库
//                ScheduleManager.getInstance().addSchdule(activeBean);
                mView.onAgreeActiveOK();
            }
        }));
    }

    /**
     * 拒绝活动
     *
     * @param activityId
     */
    @Override
    public void refuseActive(int activityId) {
        if (activityId == -1) return;

        Api.refuseActive(activityId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.onRefuseActiveOK();
            }
        }));
    }

    @Override
    public void exitActive(final ActiveBean activeBean) {
        if (activeBean.getActive_id() == -1) return;

        Api.exitActive(activeBean.getActive_id(), bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Object o) {
                //todo 退出活动，删除数据库
                ScheduleManager.getInstance().deleteSchdule(activeBean);
                mView.onExitActiveOK();
            }
        }));
    }

    @Override
    public void reApply(int activityId) {
        if (activityId == -1) return;

        Api.reApply(activityId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("申请成功");
                mView.onReApplyOK();
            }
        }));
    }

    @Override
    public void get_active_share_id(Integer activeId) {
        if(activeId == null)return;

        mView.showLoaddingView(true);
        Api.get_active_share_id(activeId, bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Integer id) {
                mView.showLoaddingView(false);
                mView.get_active_share_id_OK(id);
            }
        }));
    }
}
