package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/9.
 * 完善信息接口
 */

public interface SetInfoContract {
    interface View extends BaseView{
        void setCompleted();
    }

    interface Presenter extends OtherPresenter {
        void next(String sex, String age, String constellationId, int cityId);
    }
}
