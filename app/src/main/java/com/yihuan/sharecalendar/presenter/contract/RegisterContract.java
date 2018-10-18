package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/8.
 */

public interface RegisterContract {
    interface View extends BaseView{
        void registerSuccessed(String token);
    }

    interface Presenter extends OtherPresenter {
        void getCode(String bindPhone);
        void register(String bindPhone, String validateCode, String password, String passwrod2, boolean isCheck);
    }
}
