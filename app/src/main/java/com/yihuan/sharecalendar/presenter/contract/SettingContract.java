package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/12.
 */

public interface SettingContract {

    interface View extends BaseView{
        void logOutOK();
    }

    interface Presenter extends OtherPresenter {
        void logout();
    }

}
