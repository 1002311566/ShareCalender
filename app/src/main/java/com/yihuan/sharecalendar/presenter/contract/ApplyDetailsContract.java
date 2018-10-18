package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface ApplyDetailsContract {
    interface View extends BaseView{
        void onAgreeApplyOK();
    }

    interface Presenter extends OtherPresenter {
        //        处理状态, 0: 未处理, 1: 接受, 2: 拒绝
        void agreeApply(String userId, String proStatus);
    }
}
