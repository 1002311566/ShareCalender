package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;

/**
 * Created by Ronny on 2017/9/11.
 */

public interface EditUserInfoContract {

    interface View extends BaseView{
        void onGetUserInfoOK(UserBean userBean);
        void editInfoOK();
    }

    interface Presenter extends OtherPresenter {
        void getUserInfo();
        void editInfo(String signature, String nickname,String sex, String period, String constellation, String location);
    }
}
