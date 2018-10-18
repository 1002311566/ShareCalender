package com.yihuan.sharecalendar.presenter;

import android.content.Intent;

import com.yihuan.sharecalendar.alarm.AlarmSendBroadcastUtils;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.MainContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.login.LoginActivity;
import com.yihuan.sharecalendar.utils.LogUtils;
import com.yihuan.sharecalendar.utils.SystemUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/9/30.
 */

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.Presenter {

    public MainPresenter(MainActivity mainActivity) {
        super(mainActivity);
    }

    private int updateCount = 3;//todo 允许更新设备号的次数
    private int updateSqlCount = 3;//todo 允许失败重新更新数据库的次数

    /**
     * 用户信息
     */
    @Override
    public void getUserInfo() {

        Api.getUserInfo(bind(new MyObserver<UserBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void on401Failure() {
                super.on401Failure();
                mView.startActivity(new Intent(mView, LoginActivity.class));
            }

            @Override
            protected void onSucceed(UserBean userBean) {
                if (userBean == null) return;
                App.getInstanse().setUser(userBean);//可以防止无id情况加载数据库
                //todo 在这里发送广播，让服务进行闹铃初始化
                AlarmSendBroadcastUtils.sendInitAllActiveAlarm(mView);

                LogUtils.e("- -- -- -- -- -- ---检查设备号start --- - --- -- --- --");
                checkDeviceId(userBean.getDeviceNumber());
            }
        }));
    }

    /**
     * 检查设备号
     * @param deviceId
     */
    @Override
    public void checkDeviceId(String deviceId) {
        //todo 这里还要考虑：当用户在手机不变的情况下，卸载app后重新安装，导致不能更新数据库
        Boolean isFirst = DataUtils.isFirst();
        //todo ----------------------测试阶段，先注释该判断，进行每次重置数据库，因为完善功能时可能需在表里添加字段，这样在更新表后可能造成账号在同一手机出现数据被清除无法更新的问题
//        if (isFirst || deviceId == null || (deviceId != null && !SystemUtils.getDeviceId().equals(deviceId))) {
            //todo 如果设备是不一样的 去更新数据库, 并更新设备号
            getAllSchedule();
            return;
//        }
//        LogUtils.e("- -- -- -- -- -- ---检查设备号end 设备号相同 --- - --- -- --- --");
//        mView.refreshBroadCast();
    }

    /**
     * 获取所有日程，并更新到数据库
     */
    @Override
    public void getAllSchedule() {

        mView.showLoaddingView(true);

        Api.getAllSchedule(bind(new MyObserver<ScheduleListBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);

            }

            @Override
            protected void onSucceed(final ScheduleListBean scheduleListBean) {
                mView.showLoaddingView(false);

                //todo 获取数据成功后插入数据库
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updateSqlCount--;
                        while (updateSqlCount >0 && !DBDao.getDao().addScheduleList(scheduleListBean.getActivityList())) {
                            this.run();
                            return;
                        }
                        LogUtils.e("- -- -- -- -- -- ---全部数据已写入数据库 --- - --- -- --- --");
                        mView.refreshBroadCast();
                        DataUtils.setIsFirst(false);
                        //todo 更新设备号
                        updateDeviceId();
                    }
                }).start();
            }
        }));
    }

    /**
     * 更新设备号
     */
    @Override
    public void updateDeviceId() {
        final String deviceId = SystemUtils.getDeviceId();
        Api.updateDeveiceID(deviceId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                updateCount--;
                if (updateCount > 0) {
                    updateDeviceId();
                }
            }

            @Override
            protected void onSucceed(Object o) {
                updateCount = 3;
                mView.onUpDateDeviceIdOK();
                LogUtils.e("------------更新设备号成功------------------");
            }
        }));
    }

    /**
     * 获取所有的定制化提醒
     */
    @Override
    public void getAllRemind() {

        Api.getAutoRemindList(null, bind(new MyObserver<List<AutoRemindListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<AutoRemindListBean> beanList) {
                //todo 将提醒设置到闹钟
                for (AutoRemindListBean b : beanList){
//                    AlarmSendBroadcastUtils.sendAddAlarm(mView, new Alarm(b));
                }
            }
        }));
    }

}
