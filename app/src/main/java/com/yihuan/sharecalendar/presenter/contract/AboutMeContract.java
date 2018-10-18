package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface AboutMeContract {
    interface View extends BaseView{
        void onGetAboutMeOK(AboutMeBean bean);
    }

    interface Presenter extends OtherPresenter {
        void getAboutMe();
    }
}
