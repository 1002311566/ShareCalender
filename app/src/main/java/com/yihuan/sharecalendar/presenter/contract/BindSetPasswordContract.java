package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.login.PhoneBean;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface BindSetPasswordContract {
    interface View extends BaseView{
        void onBindWXOK();
    }

    interface Presenter extends OtherPresenter {
        void bindWX(String cellphone, String password, String confimPwd, String code, String captcha);
    }
}
