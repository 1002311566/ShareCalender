package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface MainContract {
    interface View extends BaseView{
        void onUpDateDeviceIdOK();
        void refreshBroadCast();
    }

    interface Presenter extends OtherPresenter {
        void updateDeviceId();//上传设备id
        void getAllSchedule();//获取所有的日程
        void getUserInfo();//获取个人信息
        void checkDeviceId(String deviceId);//检查设备id
        void getAllRemind();//获取所有闹钟
    }
}
