package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/22.
 */

public interface MessageBoardContract {
    interface View extends BaseView{
        void commitOK();
    }

    interface Presenter extends OtherPresenter {
        void commit(String type, String content);
    }
}
