package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.alarm.AlarmSendBroadcastUtils;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.contract.AddRemindContract;
import com.yihuan.sharecalendar.ui.activity.setting.AddRemindActivity;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

/**
 * Created by Ronny on 2017/9/29.
 */

public class AddRemindPresenter extends BasePresenter<AddRemindActivity> implements AddRemindContract.Presenter {

    public AddRemindPresenter(AddRemindActivity addRemindActivity) {
        super(addRemindActivity);
    }

    @Override
    public TimeBean getCurrentTimeBean() {
        return TimeUtils.getCurrentTimeBean();
    }

    @Override
    public TimeBean getNextDayTimeBean() {
        return TimeUtils.getNextDayTimeBean(getCurrentTimeBean());
    }

    /**
     * 创建提醒
     * @param remindBean
     */
    @Override
    public void addRemind(final RemindBean remindBean) {
        if(remindBean == null)return;

        if (TextUtils.isEmpty(remindBean.getName())) {
            mView.showToast("请输入提醒名称！");
            return;
        }

        mView.showLoaddingView(true);
        Api.addRemind(remindBean, bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Integer remindId) {
                mView.showLoaddingView(false);
                //todo 将提醒添加到闹钟
                remindBean.setRemind_id(remindId);
                AlarmSendBroadcastUtils.sendAddAlarm(App.getInstanse(),new Alarm(remindBean));
                mView.onAddRemindOK();
            }
        }));
    }

    /**
     * 获取提醒详情
     * @param taskReminderId
     */
    @Override
    public void getRemindDetails(Integer taskReminderId) {
        if(taskReminderId == null)return;

        mView.showLoaddingView(true);
        Api.getRemindDetails(taskReminderId, bind(new MyObserver<RemindDetails>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(RemindDetails remindDetails) {
                mView.showLoaddingView(false);
                mView.onGetRemindDetails(remindDetails);
            }
        }));
    }
}
