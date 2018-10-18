package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/7.
 */

public interface LoginContract {

    interface View extends BaseView{
        void loginSuccesed();
    }

    interface Presenter extends OtherPresenter {
        void login(String mobile, String password);
    }

}
