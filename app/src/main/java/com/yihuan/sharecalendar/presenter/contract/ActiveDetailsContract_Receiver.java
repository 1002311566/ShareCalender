package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;


/**
 * Created by Ronny on 2017/9/8.
 * 活动详情-接受者
 */

public interface ActiveDetailsContract_Receiver {
    interface View extends BaseView{
        void onGetActiveDetailsOK(ActiveDetailsBean bean);
        void onAgreeActiveOK();
        void onRefuseActiveOK();
        void onExitActiveOK();
        void onReApplyOK();
        void get_active_share_id_OK(Integer id);
    }

    interface Presenter extends OtherPresenter {
        void getActiveDetails(int activityId);
        void agreeActive(ActiveBean activityId);
        void refuseActive(int activityId);
        void exitActive(ActiveBean activityId);
        void reApply(int activityId);
        void get_active_share_id(Integer activeId);
    }
}
