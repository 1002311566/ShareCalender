package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/10.
 */

public interface SetNewPaswordContract {
    interface View extends BaseView{
        void onSetNewPasswrodOK();
        void onGetCellPhoneCodeOK();
    }

    interface Presenter extends OtherPresenter {
        void setNewPassword(String phone, String code, String password1, String password2);
        void getCellPhoneCode(String phone);
    }
}
