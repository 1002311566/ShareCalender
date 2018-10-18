package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/10.
 * 忘记密码
 */

public interface ForgetPwdContract {
    interface View extends BaseView{
        void getCodeOK();
        void checkCodeOK();
    }

    interface Presenter extends OtherPresenter {
        void getResetPasswordCode(String bindPhone);
        void checkResetPwdCode(String bindPhone, String code, String newPassword, String pwdConfim);
    }
}
