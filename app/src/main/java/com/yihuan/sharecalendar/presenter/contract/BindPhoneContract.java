package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.login.PhoneBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface BindPhoneContract {
    interface View extends BaseView{
        void onGetCode();
        void onBindWXOK();
        void onCheckPhoneOK(PhoneBean bean);
    }

    interface Presenter extends OtherPresenter {
        void getCode(String bindPhone);
        void bindWX(String cellphone, String password,String confimPwd, String code, String captcha);
        void checkPhone(String cellphone);
    }
}
